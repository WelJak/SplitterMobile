package com.weljak.splittermobile

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.weljak.splittermobile.data.model.friend.request.FriendshipRequestCreationForm
import com.weljak.splittermobile.data.util.Resource
import com.weljak.splittermobile.databinding.FragmentAddFriendBinding
import com.weljak.splittermobile.presentation.util.view.ViewUtils
import com.weljak.splittermobile.presentation.viewmodel.friend.FriendViewModel
import com.weljak.splittermobile.presentation.viewmodel.user.UserViewModel

class AddFriendFragment : Fragment() {
    private lateinit var binding: FragmentAddFriendBinding
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var friendViewModel: FriendViewModel
    private lateinit var userViewModel: UserViewModel
    private lateinit var token: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_friend, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAddFriendBinding.bind(view)
        sharedPreferences =
            requireActivity().getSharedPreferences("user_data", Context.MODE_PRIVATE)
        friendViewModel = (activity as MainActivity).friendViewModel
        userViewModel = (activity as MainActivity).userViewModel
        token = sharedPreferences.getString("token", "").toString()

        userViewModel.searchedUser.observe(viewLifecycleOwner, { response ->
            when (response) {
                is Resource.Loading -> {
                    ViewUtils.showProgressBar(binding.friendPb)
                }
                is Resource.Success -> {
                    ViewUtils.hideProgressBar(binding.friendPb)
                    val data = response.data
                    data?.let {
                        if (it.payload == null) {
                            Toast.makeText(activity, it.message, Toast.LENGTH_LONG).show()
                            clearUserDetails()
                            ViewUtils.makeButtonUnClickable(binding.addFriendBtn)
                        } else {
                            val payload = it.payload
                            binding.friendUsernameTv.text = payload.username
                            binding.friendEmailTv.text = payload.email
                            ViewUtils.makeButtonClickable(binding.addFriendBtn)
                        }
                    }
                }
                is Resource.Error -> {
                    ViewUtils.hideProgressBar(binding.friendPb)
                    response.message?.let {
                        Toast.makeText(activity, "An error occurred $it", Toast.LENGTH_LONG).show()
                    }
                }
            }
        })

        friendViewModel.addFriendReqResponse.observe(viewLifecycleOwner, { response ->
            when (response) {
                is Resource.Loading -> {
                    ViewUtils.showProgressBar(binding.friendPb)
                }
                is Resource.Success -> {
                    ViewUtils.hideProgressBar(binding.friendPb)
                    response.data?.let {
                        Toast.makeText(
                            activity,
                            "Friend request for user: ${it.payload?.potentialFriend?.username} has been sent",
                            Toast.LENGTH_LONG
                        ).show()
                        clearUserDetails()
                    }
                }
                is Resource.Error -> {
                    ViewUtils.hideProgressBar(binding.friendPb)
                    clearUserDetails()
                    response.message?.let {
                        Toast.makeText(activity, "An error occurred $it", Toast.LENGTH_LONG).show()
                    }
                }
            }

        })
        binding.searchFriendBtn.setOnClickListener {
            searchFriend(binding.searchFriendSv.query.toString(), "Bearer $token")
        }

        binding.addFriendBtn.setOnClickListener {
            addFriendButtonCallback()
        }

        binding.friendRequestsBtn.setOnClickListener {
            findNavController().navigate(R.id.action_addFriendFragment_to_friendRequestsFragment)
        }

        binding.searchFriendSv.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    searchFriend(it, "Bearer $token")
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })
    }

    private fun clearUserDetails() {
        binding.friendUsernameTv.text = ""
        binding.friendEmailTv.text = ""
    }

    private fun searchFriend(searchQuery: String, token: String) {
        userViewModel.getUserData(searchQuery, token)
    }

    private fun addFriendButtonCallback() {
        val friendRequestCreationForm = FriendshipRequestCreationForm(
            binding.friendUsernameTv.text.toString(),
            binding.friendEmailTv.text.toString()
        )
        friendViewModel.createFriendRequest("Bearer $token", friendRequestCreationForm)
        ViewUtils.makeButtonUnClickable(binding.addFriendBtn)
    }
}