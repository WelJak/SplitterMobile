package com.weljak.splittermobile.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.weljak.splittermobile.data.model.expense.Debtor
import com.weljak.splittermobile.databinding.DebtorsListElementBinding
import java.math.BigDecimal

class DebtorsAdapter : RecyclerView.Adapter<DebtorsAdapter.DebtorsViewHolder>() {
    private val callback = object : DiffUtil.ItemCallback<Debtor>() {
        override fun areItemsTheSame(oldItem: Debtor, newItem: Debtor): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: Debtor, newItem: Debtor): Boolean {
            return oldItem == newItem
        }
    }

    private var deleteItemButtonCallback: ((Debtor) -> Unit)? = null
    fun setDeleteItemButtonCallback(callback: (Debtor) -> Unit) {
        deleteItemButtonCallback = callback
    }

    val differ = AsyncListDiffer(this, callback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DebtorsViewHolder {
        val binding =
            DebtorsListElementBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DebtorsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DebtorsViewHolder, position: Int) {
        val currentItem = differ.currentList[position]
        holder.bind(currentItem)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    inner class DebtorsViewHolder(val binding: DebtorsListElementBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(debtor: Debtor) {
            binding.debtorNameTv.text = debtor.name
            binding.debtAmountEt.setText(String.format(debtor.debt.toString()))
            binding.deleteDebtorBtn.setOnClickListener {
                deleteItemButtonCallback?.let {
                    it(debtor)
                }
                val updated = ArrayList(differ.currentList)
                updated.remove(debtor)
                differ.submitList(updated)
            }

            binding.debtAmountEt.setOnFocusChangeListener { _, hasFocus ->
                if (!hasFocus) {
                    val updated = ArrayList(differ.currentList)
                    val toUpdate = updated.first { toFind -> toFind.name == debtor.name }
                    val index = updated.indexOf(toUpdate)
                    updated[index] =
                        Debtor(toUpdate.name, BigDecimal(binding.debtAmountEt.text.toString()))
                    differ.submitList(updated)
                }
            }
        }
    }
}