package com.weljak.splittermobile

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.weljak.splittermobile.data.model.group.Group
import com.weljak.splittermobile.data.model.group.ManageGroupMembershipRequest
import com.weljak.splittermobile.data.util.Resource
import com.weljak.splittermobile.databinding.FragmentGroupDetailsBinding
import com.weljak.splittermobile.presentation.adapter.GroupMembersAdapter
import com.weljak.splittermobile.presentation.viewmodel.friend.FriendViewModel
import com.weljak.splittermobile.presentation.viewmodel.group.GroupViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class GroupDetailsFragment : Fragment() {
    private lateinit var binding: FragmentGroupDetailsBinding
    private lateinit var groupViewModel: GroupViewModel
    private lateinit var friendViewModel: FriendViewModel
    private lateinit var groupMembersAdapter: GroupMembersAdapter
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var token: String
    private lateinit var group: Group
    private val potentialMembers = HashSet<String>()

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
            //TODO add dialog to confirm user removal
            lifecycleScope.launch {
                withContext(Dispatchers.IO) {
                    groupViewModel.removeUsersFromGroup(token, group.id, toDelete).join()
                    groupViewModel.getGroupById(token, group.id)
                }
            }
        }

        initGroupDetails()
        initRecyclerView()
        //TODO implement Loading & Error cases
        groupViewModel.getGroupByIdResponse.observe(viewLifecycleOwner) { response ->
            when(response) {
                is Resource.Loading -> {

                }
                is Resource.Success -> {
                    val payload = response.data!!.payload
                    groupMembersAdapter.differ.submitList(payload!!.members)
                    group = payload
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
                val spinnerAdapter = ArrayAdapter(requireContext(),android.R.layout.simple_spinner_item, potentialMembers.toList())
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
}