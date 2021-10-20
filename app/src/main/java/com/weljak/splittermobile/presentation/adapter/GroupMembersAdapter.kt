package com.weljak.splittermobile.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.weljak.splittermobile.databinding.GroupMemberListElementBinding

class GroupMembersAdapter: RecyclerView.Adapter<GroupMembersAdapter.GroupMembersViewHolder>() {
    private val callback = object: DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }
    }

    private var removeMemberFromGroupButtonCallback: ((String) -> Unit) ?= null

    val differ = AsyncListDiffer(this, callback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupMembersViewHolder {
        val binding = GroupMemberListElementBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GroupMembersViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GroupMembersViewHolder, position: Int) {
        val currentItem = differ.currentList[position]
        holder.bind(currentItem)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun setRemoveMemberFromGroupButtonCallback(buttonCallback: (String) -> Unit) {
        removeMemberFromGroupButtonCallback = buttonCallback
    }

    inner class GroupMembersViewHolder(private val binding: GroupMemberListElementBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(groupName: String) {
            binding.memberNameTv.text = groupName
            binding.removeMemberBtn.setOnClickListener {
                removeMemberFromGroupButtonCallback?.let { buttonCallback -> buttonCallback(groupName) }
            }
        }
    }
}