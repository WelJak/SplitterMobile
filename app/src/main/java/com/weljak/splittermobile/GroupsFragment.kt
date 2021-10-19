package com.weljak.splittermobile

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.weljak.splittermobile.data.util.Resource
import com.weljak.splittermobile.databinding.FragmentGroupsBinding
import com.weljak.splittermobile.presentation.adapter.CurrentUserGroupsAdapter
import com.weljak.splittermobile.presentation.viewmodel.group.GroupViewModel

class GroupsFragment : Fragment() {
    private lateinit var binding: FragmentGroupsBinding
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var groupViewModel: GroupViewModel
    private lateinit var groupsAdapter: CurrentUserGroupsAdapter
    private lateinit var token: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_groups, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentGroupsBinding.bind(view)
        sharedPreferences = requireActivity().getSharedPreferences("user_data", Context.MODE_PRIVATE)
        groupViewModel = (activity as MainActivity).groupViewModel
        groupsAdapter = (activity as MainActivity).currentUserGroupsAdapter
        token = "Bearer " + sharedPreferences.getString("token", "")
        initRecyclerView()

        groupViewModel.getCurrentUserGroupsResponse.observe(viewLifecycleOwner) { response ->
            when(response) {
                is Resource.Loading -> {
                    Log.i("app", "Loading groups data (todo -> change it)") // change it later
                }
                is  Resource.Success -> {
                    val groups = response.data?.payload
                    groupsAdapter.differ.submitList(groups)
                }
                is Resource.Error -> {
                    Log.i("app", "error occured (todo -> change it)") //change it later
                }
            }
        }

        getCurrentUserGroups(token)
    }

    private fun getCurrentUserGroups(token: String) = groupViewModel.getCurrentUserGroups(token)

    private fun initRecyclerView() {
        binding.groupsRv.apply {
            adapter = groupsAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }
}