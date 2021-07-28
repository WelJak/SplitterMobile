package com.weljak.splittermobile.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.weljak.splittermobile.data.model.friend.Friendship
import com.weljak.splittermobile.databinding.FriendListElementBinding

class FriendsAdapter: RecyclerView.Adapter<FriendsAdapter.FriendsViewHolder>() {
    private val callback = object: DiffUtil.ItemCallback<Friendship>() {
        override fun areItemsTheSame(oldItem: Friendship, newItem: Friendship): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Friendship, newItem: Friendship): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, callback)

    inner class FriendsViewHolder(private val binding:FriendListElementBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(friendship: Friendship) {
            binding.elementTitleTv.text = friendship.username
            //TODO add tv completion when expenses are implemented
            binding.totalLentTv.text = "TODO"
            binding.totalOwedTv.text = "TODO"
            binding.finalBalanceTv.text = "TODO"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendsViewHolder {
       val binding = FriendListElementBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FriendsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FriendsViewHolder, position: Int) {
        val friendship = differ.currentList[position]
        holder.bind(friendship)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}