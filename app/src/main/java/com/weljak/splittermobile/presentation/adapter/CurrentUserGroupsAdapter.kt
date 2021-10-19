package com.weljak.splittermobile.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.weljak.splittermobile.data.model.group.Group
import com.weljak.splittermobile.databinding.UserGroupListElementBinding

class CurrentUserGroupsAdapter: RecyclerView.Adapter<CurrentUserGroupsAdapter.CurrentUserGroupsViewHolder>() {
    private val callback = object : DiffUtil.ItemCallback<Group>() {
        override fun areItemsTheSame(oldItem: Group, newItem: Group): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Group, newItem: Group): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, callback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrentUserGroupsViewHolder {
        val binding = UserGroupListElementBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CurrentUserGroupsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CurrentUserGroupsViewHolder, position: Int) {
        val currentItem = differ.currentList[position]
        holder.bind(currentItem)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    inner class CurrentUserGroupsViewHolder(val binding: UserGroupListElementBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(group: Group) {
            binding.groupNameTv.text = group.groupName
            binding.createdAtTv.text = group.createdAt.toString()
            binding.createdByTv.text = group.createdBy
            binding.membersCountTv.text = group.members?.size.toString()
        }
    }

}