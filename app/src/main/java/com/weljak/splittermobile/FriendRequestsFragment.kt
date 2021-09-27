package com.weljak.splittermobile

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.weljak.splittermobile.data.util.Resource
import com.weljak.splittermobile.databinding.FragmentFriendRequestsBinding
import com.weljak.splittermobile.presentation.adapter.ReceivedFriendRequestsAdapter
import com.weljak.splittermobile.presentation.adapter.SentFriendRequestsAdapter
import com.weljak.splittermobile.presentation.util.view.ViewUtils
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
        sharedPreferences =
            requireActivity().getSharedPreferences("user_data", Context.MODE_PRIVATE)
        token = "Bearer ${sharedPreferences.getString("token", "").toString()}"
        sentFriendRequestsAdapter = (activity as MainActivity).sentFriendRequestsAdapter
        friendViewModel = (activity as MainActivity).friendViewModel

        receivedFriendRequestsAdapter = (activity as MainActivity).receivedFriendRequestsAdapter

        receivedFriendRequestsAdapter.setDeclineFriendRequestButtonCallback {
            friendViewModel.declineFriendRequest(token, it)
            Toast.makeText(activity, "Friend request declined", Toast.LENGTH_SHORT).show()
        }
        receivedFriendRequestsAdapter.setAcceptFriendRequestButtonCallback {
            friendViewModel.acceptFriendRequest(token, it)
            Toast.makeText(activity, "Friend request accepted", Toast.LENGTH_SHORT).show()
        }

        initSentFriendRequestsRecyclerView()
        initReceivedFriendRequestRecyclerView()

        friendViewModel.sentFriendRequests.observe(viewLifecycleOwner, { response ->
            when (response) {
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
            when (response) {
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

        friendViewModel.confirmFriendReqResponse.observe(viewLifecycleOwner, { response ->
            when (response) {
                is Resource.Loading -> {
                    ViewUtils.showProgressBar(binding.friendRequestsPb)
                }
                is Resource.Success -> {
                    ViewUtils.hideProgressBar(binding.friendRequestsPb)
                    getCurrentUserReceivedFriendRequests()
                }
                is Resource.Error -> {
                    ViewUtils.hideProgressBar(binding.friendRequestsPb)
                    response.message?.let {
                        Toast.makeText(activity, "An error occurred $it", Toast.LENGTH_LONG).show()
                    }
                    getCurrentUserReceivedFriendRequests()
                }
            }
        })

        friendViewModel.declineFriendReqResponse.observe(viewLifecycleOwner, { response ->
            when (response) {
                is Resource.Loading -> {
                    ViewUtils.showProgressBar(binding.friendRequestsPb)
                }
                is Resource.Success -> {
                    ViewUtils.hideProgressBar(binding.friendRequestsPb)
                    getCurrentUserReceivedFriendRequests()
                }
                is Resource.Error -> {
                    ViewUtils.hideProgressBar(binding.friendRequestsPb)
                    response.message?.let {
                        Toast.makeText(activity, "An error occurred $it", Toast.LENGTH_LONG).show()
                    }
                    getCurrentUserReceivedFriendRequests()
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