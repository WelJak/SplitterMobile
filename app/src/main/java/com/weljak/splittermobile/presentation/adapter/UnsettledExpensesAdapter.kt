package com.weljak.splittermobile.presentation.adapter

import android.app.Application
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.weljak.splittermobile.data.model.expense.Expense
import com.weljak.splittermobile.databinding.UnsettledExpenseListElementBinding

class UnsettledExpensesAdapter(private val app: Application):
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
                val dialogBuilder = AlertDialog.Builder(app.applicationContext)
                dialogBuilder.setTitle(SETTLE_UP_EXPENSE_DIALOG_TITLE)
                dialogBuilder.setMessage(SETTLE_UP_EXPENSE_MESSAGE)
                dialogBuilder.setPositiveButton(SETTLE_UP_BUTTON_TEXT) { _, _ ->
                    settleExpenseCallback?.let { it(expense) }
                    Toast.makeText(app, "Expense settled up!", Toast.LENGTH_SHORT).show()
                }
                dialogBuilder.setNegativeButton(CANCEL_BUTTON_TEXT) {dialogInterface, _ ->
                    dialogInterface.cancel()
                }
                dialogBuilder.create().show()
            }

            binding.deleteExpenseBtn.setOnClickListener {
                val dialogBuilder = AlertDialog.Builder(app.applicationContext)
                dialogBuilder.setTitle(DELETE_EXPENSE_DIALOG_TITLE)
                dialogBuilder.setMessage(DELETE_EXPENSE_MESSAGE)
                dialogBuilder.setPositiveButton(DELETE_BUTTON_TEXT) {_, _ ->
                    deleteExpenseCallback?.let { it(expense) }
                    Toast.makeText(app, "Expense deleted!", Toast.LENGTH_SHORT).show()
                }
                dialogBuilder.setNegativeButton(CANCEL_BUTTON_TEXT) {dialogInterface, _ ->
                    dialogInterface.cancel()
                }
                dialogBuilder.create().show()
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

    companion object {
        private const val SETTLE_UP_EXPENSE_DIALOG_TITLE = "Settle up expense"
        private const val DELETE_EXPENSE_DIALOG_TITLE = "Delete expense"
        private const val SETTLE_UP_EXPENSE_MESSAGE = "Do you want to settle up expense?"
        private const val DELETE_EXPENSE_MESSAGE = "Do you want to delete expense?"
        private const val CANCEL_BUTTON_TEXT = "Cancel"
        private const val DELETE_BUTTON_TEXT = "Delete"
        private const val SETTLE_UP_BUTTON_TEXT = "Settle up"
    }
}
