package com.weljak.splittermobile

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.weljak.splittermobile.data.model.group.Group
import com.weljak.splittermobile.data.model.group.ManageGroupMembershipRequest
import com.weljak.splittermobile.data.util.Resource
import com.weljak.splittermobile.databinding.FragmentGroupDetailsBinding
import com.weljak.splittermobile.presentation.adapter.GroupMembersAdapter
import com.weljak.splittermobile.presentation.viewmodel.friend.FriendViewModel
import com.weljak.splittermobile.presentation.viewmodel.group.GroupViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*
import kotlin.collections.ArrayList

class GroupDetailsFragment : Fragment() {
    private lateinit var binding: FragmentGroupDetailsBinding
    private lateinit var groupViewModel: GroupViewModel
    private lateinit var friendViewModel: FriendViewModel
    private lateinit var groupMembersAdapter: GroupMembersAdapter
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var spinnerAdapter: ArrayAdapter<String>
    private lateinit var token: String
    private lateinit var group: Group
    private val potentialMembers = HashSet<String>()
    private var errorNotDisplayed = false
    private var removalConfirmationNotDisplayed = false
    private var groupNotChanged = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_group_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentGroupDetailsBinding.bind(view)
        group = arguments?.get("group") as Group
        groupViewModel = (activity as MainActivity).groupViewModel
        friendViewModel = (activity as MainActivity).friendViewModel
        groupMembersAdapter = (activity as MainActivity).groupMembersAdapter
        sharedPreferences = requireActivity().getSharedPreferences("user_data", Context.MODE_PRIVATE)
        token = "Bearer " + sharedPreferences.getString("token", "")

        groupMembersAdapter.setRemoveMemberFromGroupButtonCallback {
            val toDelete = ManageGroupMembershipRequest(Collections.singletonList(it))
            val dialogBuilder = MaterialAlertDialogBuilder(activity as MainActivity)
            dialogBuilder.setTitle(REMOVE_USER_FROM_GROUP_DIALOG_TITLE)
            dialogBuilder.setMessage(REMOVE_USER_FROM_GROUP_DIALOG_MESSAGE.replace("{}", it))
            dialogBuilder.setPositiveButton(REMOVE_USER_FROM_GROUP_CONFIRM_BUTTON_TEXT) { _, _ ->
                lifecycleScope.launch {
                    withContext(Dispatchers.IO) {
                        coroutineScope {
                            groupViewModel.removeUsersFromGroup(token, group.id, toDelete).join()
                            groupViewModel.getGroupById(token, group.id)
                        }.join()
                        withContext(Dispatchers.Main.immediate) {
                            spinnerAdapter.add(it)
                        }
                    }
                }
                errorNotDisplayed = true
                removalConfirmationNotDisplayed = true
                groupNotChanged = true
            }
            dialogBuilder.setNegativeButton(CANCEL_BUTTON_TEXT) {dialogInterface, _ ->
                dialogInterface.cancel()
            }
            dialogBuilder.show()
        }

        initGroupDetails()
        initRecyclerView()

        groupViewModel.removeUsersFromGroupResponse.observe(viewLifecycleOwner) { response ->
            when(response) {
                is Resource.Loading -> {}
                is Resource.Success -> {
                    if (removalConfirmationNotDisplayed) {
                        Toast.makeText(context, "User removed!", Toast.LENGTH_SHORT).show()
                    }
                    removalConfirmationNotDisplayed = false
                }
                is Resource.Error -> {
                    if (errorNotDisplayed) {
                        Toast.makeText(context, "${response.message}", Toast.LENGTH_LONG).show()
                    }
                    errorNotDisplayed = false
                }
            }
        }
        groupViewModel.getGroupByIdResponse.observe(viewLifecycleOwner) { response ->
            when(response) {
                is Resource.Loading -> {

                }
                is Resource.Success -> {
                   if (groupNotChanged) {
                       val payload = response.data!!.payload
                       groupMembersAdapter.differ.submitList(payload!!.members)
                       group = payload
                   }
                    groupNotChanged = false
                }
                is Resource.Error -> {

                }
            }
        }

        friendViewModel.currentUserFriendship.observe(viewLifecycleOwner) { response ->
            when(response) {
                is Resource.Loading -> {}
                is Resource.Success -> {
                    response.data?.payload?.friendList?.forEach { friend ->
                        potentialMembers.add(friend.username)
                    }
                    group.members?.let { potentialMembers.removeAll(it) }
                }
                is Resource.Error -> {}
            }
        }

        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                friendViewModel.getCurrentUserFriendList(token).join()
                spinnerAdapter = ArrayAdapter(requireContext(),android.R.layout.simple_spinner_item, ArrayList(potentialMembers))
                spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                withContext(Dispatchers.Main) {
                    binding.potentialMembersSpinner.adapter = spinnerAdapter
                }
            }
        }
    }

    private fun initGroupDetails() {
        binding.groupDetailsNameTv.text = group.groupName
        binding.groupDetailsCreatedAtTv.text = group.createdAt.toString()
        binding.groupDetailsCreatedByTv.text = group.createdBy
        groupMembersAdapter.differ.submitList(group.members)
    }

    private fun initRecyclerView() {
        binding.groupMembersRv.apply {
            adapter = groupMembersAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

    companion object {
        private const val REMOVE_USER_FROM_GROUP_DIALOG_TITLE = "Remove user from group"
        private const val REMOVE_USER_FROM_GROUP_DIALOG_MESSAGE = "Do you really want to remove {} from group?"
        private const val REMOVE_USER_FROM_GROUP_CONFIRM_BUTTON_TEXT = "Remove"
        private const val CANCEL_BUTTON_TEXT = "Cancel"
    }
}