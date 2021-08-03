package com.weljak.splittermobile

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.weljak.splittermobile.data.util.Resource
import com.weljak.splittermobile.databinding.FragmentFriendRequestsBinding
import com.weljak.splittermobile.presentation.adapter.ReceivedFriendRequestsAdapter
import com.weljak.splittermobile.presentation.adapter.SentFriendRequestsAdapter
import com.weljak.splittermobile.presentation.util.ViewUtils
import com.weljak.splittermobile.presentation.viewmodel.friend.FriendViewModel

class FriendRequestsFragment : Fragment() {
    private lateinit var binding: FragmentFriendRequestsBinding
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var sentFriendRequestsAdapter: SentFriendRequestsAdapter
    private lateinit var receivedFriendRequestsAdapter: ReceivedFriendRequestsAdapter
    private lateinit var friendViewModel: FriendViewModel
    private lateinit var token: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_friend_requests, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentFriendRequestsBinding.bind(view)
        sharedPreferences = requireActivity().getSharedPreferences("user_data", Context.MODE_PRIVATE)
        sentFriendRequestsAdapter = (activity as MainActivity).sentFriendRequestsAdapter
        receivedFriendRequestsAdapter = (activity as MainActivity).receivedFriendRequestsAdapter
        friendViewModel = (activity as MainActivity).friendViewModel
        token = "Bearer ${sharedPreferences.getString("token", "").toString()}"

        initSentFriendRequestsRecyclerView()
        initReceivedFriendRequestRecyclerView()

        friendViewModel.sentFriendRequests.observe(viewLifecycleOwner, { response ->
            when(response) {
                is Resource.Loading -> {
                    ViewUtils.showProgressBar(binding.friendRequestsPb)
                }
                is Resource.Success -> {
                    ViewUtils.hideProgressBar(binding.friendRequestsPb)
                    val requests = response.data?.payload
                    sentFriendRequestsAdapter.differ.submitList(requests)
                }
                is Resource.Error -> {
                    ViewUtils.hideProgressBar(binding.friendRequestsPb)
                    response.message?.let {
                        Toast.makeText(activity, "An error occurred $it", Toast.LENGTH_LONG).show()
                    }
                }
            }
        })

        friendViewModel.receivedFriendRequests.observe(viewLifecycleOwner, { response ->
            when(response) {
                is Resource.Loading -> {
                    ViewUtils.showProgressBar(binding.friendRequestsPb)
                }
                is Resource.Success -> {
                    ViewUtils.hideProgressBar(binding.friendRequestsPb)
                    val requests = response.data?.payload
                    receivedFriendRequestsAdapter.differ.submitList(requests)
                }
                is Resource.Error -> {
                    ViewUtils.hideProgressBar(binding.friendRequestsPb)
                    response.message?.let {
                        Toast.makeText(activity, "An error occurred $it", Toast.LENGTH_LONG).show()
                    }
                }
            }
        })

        getCurrentUserSentFriendRequests()
        getCurrentUserReceivedFriendRequests()
    }

    private fun initSentFriendRequestsRecyclerView() {
        binding.pendingRequestsRv.apply {
            adapter = sentFriendRequestsAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

    private fun initReceivedFriendRequestRecyclerView() {
        binding.receivedRequestsRv.apply {
            adapter = receivedFriendRequestsAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

    private fun getCurrentUserSentFriendRequests() {
        friendViewModel.getCurrentUserSentFriendRequests(token)
    }

    private fun getCurrentUserReceivedFriendRequests() {
        friendViewModel.getCurrentUserReceivedFriendRequests(token)
    }
}