package com.weljak.splittermobile.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.weljak.splittermobile.data.model.friend.request.FriendshipRequest
import com.weljak.splittermobile.databinding.ReceivedFriendRequestListElementBinding

class ReceivedFriendRequestsAdapter: RecyclerView.Adapter<ReceivedFriendRequestsAdapter.ReceivedFriendRequestsViewHolder>() {
    private val callback = object : DiffUtil.ItemCallback<FriendshipRequest>() {
        override fun areItemsTheSame(
            oldItem: FriendshipRequest,
            newItem: FriendshipRequest
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: FriendshipRequest,
            newItem: FriendshipRequest
        ): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, callback)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ReceivedFriendRequestsViewHolder {
        val binding = ReceivedFriendRequestListElementBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ReceivedFriendRequestsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ReceivedFriendRequestsViewHolder, position: Int) {
        val friendRequest = differ.currentList[position]
        holder.bind(friendRequest)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    inner class ReceivedFriendRequestsViewHolder(private val binding: ReceivedFriendRequestListElementBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(friendRequest: FriendshipRequest) {
            binding.receivedFrUsernameTv.text = friendRequest.username
            binding.confirmRequestBtn.setOnClickListener{ acceptFriendRequestButtonCallback?.let {
                it(friendRequest)
            } }
            binding.rejectFriendRequestBtn.setOnClickListener { declineFriendRequestButtonCallback?.let {
                it(friendRequest)
            } }
        }
    }

    private var acceptFriendRequestButtonCallback: ((FriendshipRequest) -> Unit) ?= null

    fun setAcceptFriendRequestButtonCallback(callback :(FriendshipRequest) -> Unit) {
        acceptFriendRequestButtonCallback = callback
    }

    private var declineFriendRequestButtonCallback: ((FriendshipRequest) -> Unit) ?= null

    fun setDeclineFriendRequestButtonCallback(callback :(FriendshipRequest) -> Unit) {
        declineFriendRequestButtonCallback = callback
    }
}