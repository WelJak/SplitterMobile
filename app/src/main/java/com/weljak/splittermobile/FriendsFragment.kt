package com.weljak.splittermobile

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.weljak.splittermobile.data.util.Resource
import com.weljak.splittermobile.databinding.FragmentFriendsBinding
import com.weljak.splittermobile.presentation.adapter.FriendsAdapter
import com.weljak.splittermobile.presentation.util.ViewUtils
import com.weljak.splittermobile.presentation.viewmodel.friend.FriendViewModel
import com.weljak.splittermobile.presentation.viewmodel.user.UserViewModel

class FriendsFragment : Fragment() {
    private lateinit var binding: FragmentFriendsBinding
    private lateinit var friendsAdapter: FriendsAdapter
    private lateinit var friendViewModel: FriendViewModel
    private lateinit var userViewModel: UserViewModel
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_friends, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentFriendsBinding.bind(view)
        sharedPreferences = requireActivity().getSharedPreferences("user_data", Context.MODE_PRIVATE)
        friendViewModel = (activity as MainActivity).friendViewModel
        userViewModel = (activity as MainActivity).userViewModel
        friendsAdapter = (activity as MainActivity).friendsAdapter
        initRecyclerView()

        friendViewModel.currentUserFriendship.observe(viewLifecycleOwner, { response ->
            when (response) {
                is Resource.Loading -> {
                    ViewUtils.showProgressBar(binding.friendsProgressBar)
                }
                is Resource.Success -> {
                    ViewUtils.hideProgressBar(binding.friendsProgressBar)
                    val friendList = response.data?.payload?.friendList
                    friendsAdapter.differ.submitList(friendList)
                }
                is Resource.Error -> {
                    ViewUtils.hideProgressBar(binding.friendsProgressBar)
                    response.message?.let {
                        Toast.makeText(activity, "An error occurred $it", Toast.LENGTH_LONG).show()
                    }
                }
            }
        })
        getCurrentUserFriendList()

        binding.addFriendBtn.setOnClickListener {
            findNavController().navigate(R.id.action_friendsFragment_to_addFriendFragment)
        }
    }

    private fun getCurrentUserFriendList() {
        val token = sharedPreferences.getString("token", "")
        token?.let { friendViewModel.getCurrentUserFriendList("Bearer $it") }
    }

    private fun initRecyclerView() {
        binding.friendListRv.apply {
            adapter = friendsAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }
}