package com.weljak.splittermobile.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.weljak.splittermobile.data.model.friend.request.FriendshipRequest
import com.weljak.splittermobile.databinding.UserFriendRequestListElementBinding

class SentFriendRequestsAdapter: RecyclerView.Adapter<SentFriendRequestsAdapter.SentFriendRequestsViewHolder>() {
    private val callback = object: DiffUtil.ItemCallback<FriendshipRequest>() {
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
    ): SentFriendRequestsViewHolder {
        val binding = UserFriendRequestListElementBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SentFriendRequestsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SentFriendRequestsViewHolder, position: Int) {
        val friendRequest = differ.currentList[position]
        holder.bind(friendRequest)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    inner class SentFriendRequestsViewHolder(private val binding: UserFriendRequestListElementBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(friendshipRequest: FriendshipRequest) {
            binding.potFriendEmailTv.text = friendshipRequest.potentialFriend.email
            binding.potFriendUsernameTv.text = friendshipRequest.potentialFriend.username
        }
    }
}