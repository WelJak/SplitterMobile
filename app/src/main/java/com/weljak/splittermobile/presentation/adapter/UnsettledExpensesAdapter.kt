package com.weljak.splittermobile.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.weljak.splittermobile.data.model.expense.Expense
import com.weljak.splittermobile.databinding.UnsettledExpenseListElementBinding

class UnsettledExpensesAdapter:
    RecyclerView.Adapter<UnsettledExpensesAdapter.UnsettledExpensesViewHolder>() {
    private val callback = object: DiffUtil.ItemCallback<Expense>() {
        override fun areItemsTheSame(oldItem: Expense, newItem: Expense): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Expense, newItem: Expense): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, callback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UnsettledExpensesViewHolder {
        val binding = UnsettledExpenseListElementBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UnsettledExpensesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UnsettledExpensesViewHolder, position: Int) {
        val expense = differ.currentList[position]
        holder.bind(expense)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    inner class UnsettledExpensesViewHolder(private val binding: UnsettledExpenseListElementBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(expense: Expense) {
            binding.lenderTv.text = expense.paidBy
            binding.amountTv.text = String.format(expense.totalSum.toString())
            binding.descTv.text = expense.description

            binding.settleExpenseBtn.setOnClickListener {
                settleExpenseCallback?.let { it(expense) }
            }

            binding.deleteExpenseBtn.setOnClickListener {
                deleteExpenseCallback?.let { it(expense) }
            }
        }
    }

    private var settleExpenseCallback: ((Expense) -> Unit) ?= null

    fun setSettleExpenseCallback(callback: (Expense) -> Unit) {
        settleExpenseCallback = callback
    }

    private var deleteExpenseCallback: ((Expense) -> Unit) ?= null

    fun setDeleteExpenseCallback(callback: (Expense) -> Unit) {
        deleteExpenseCallback = callback
    }
}
