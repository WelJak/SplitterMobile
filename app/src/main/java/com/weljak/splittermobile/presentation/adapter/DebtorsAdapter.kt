package com.weljak.splittermobile.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ArrayAdapter
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

    var friendList: List<String> = emptyList()

    val differ = AsyncListDiffer(this, callback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DebtorsViewHolder {
        val binding = DebtorsListElementBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DebtorsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DebtorsViewHolder, position: Int) {
        val adapter = ArrayAdapter(holder.binding.root.context, android.R.layout.simple_spinner_item, friendList)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        holder.binding.debtorsSpinner.adapter = adapter
        holder.bind(differ.currentList[position])
        holder.binding.deleteDebtorBtn.setOnClickListener {
            val debtors = differ.currentList
            debtors.removeAt(position)
            differ.submitList(debtors)
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    inner class DebtorsViewHolder(val binding: DebtorsListElementBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(debtor: Debtor) {
            binding.debtAmountEt.setText(String.format(debtor.debt.toString()))
        }
    }
}