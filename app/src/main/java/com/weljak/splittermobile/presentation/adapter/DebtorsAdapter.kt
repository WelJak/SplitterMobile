package com.weljak.splittermobile.presentation.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.weljak.splittermobile.data.model.expense.Debtor
import com.weljak.splittermobile.databinding.DebtorsListElementBinding

class DebtorsAdapter: RecyclerView.Adapter<DebtorsAdapter.DebtorsViewHolder>() {
    private val callback = object: DiffUtil.ItemCallback<Debtor>() {
        override fun areItemsTheSame(oldItem: Debtor, newItem: Debtor): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: Debtor, newItem: Debtor): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, callback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DebtorsViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: DebtorsViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    inner class DebtorsViewHolder(private val binding: DebtorsListElementBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(debtor: Debtor) {
            binding.editTextTextPersonName.setText(debtor.name)
            binding.editTextNumberDecimal.setText(String.format(debtor.debt.toString()))
        }
    }
}