package com.weljak.splittermobile

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.get
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.weljak.splittermobile.data.util.Resource
import com.weljak.splittermobile.databinding.DebtorsListElementBinding
import com.weljak.splittermobile.databinding.FragmentExpensesBinding
import com.weljak.splittermobile.presentation.adapter.UnsettledExpensesAdapter
import com.weljak.splittermobile.presentation.viewmodel.expense.ExpenseViewModel
import kotlinx.coroutines.*

class ExpensesFragment : Fragment() {
    private lateinit var binding: FragmentExpensesBinding
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var unsettledExpensesAdapter: UnsettledExpensesAdapter
    private lateinit var expenseViewModel: ExpenseViewModel
    private lateinit var token: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_expenses, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentExpensesBinding.bind(view)
        sharedPreferences =
            requireActivity().getSharedPreferences("user_data", Context.MODE_PRIVATE)
        unsettledExpensesAdapter = (activity as MainActivity).unsettledExpensesAdapter
        expenseViewModel = (activity as MainActivity).expenseViewModel
        token = "Bearer ${sharedPreferences.getString("token", "")}"

        unsettledExpensesAdapter.setSettleExpenseCallback {
            val dialogBuilder = MaterialAlertDialogBuilder(activity as MainActivity)
            dialogBuilder.setTitle(SETTLE_UP_EXPENSE_DIALOG_TITLE)
            dialogBuilder.setMessage(SETTLE_UP_EXPENSE_MESSAGE)
            dialogBuilder.setPositiveButton(SETTLE_UP_BUTTON_TEXT) { _, _ ->
                lifecycleScope.launch {
                    withContext(Dispatchers.IO) {
                        coroutineScope {
                            expenseViewModel.settleExpense(token, it.id).join()
                            expenseViewModel.getCurrentUserUnsettledExpenses(token)
                        }
                    }
                }
                Toast.makeText(activity, "Expense settled up!", Toast.LENGTH_SHORT).show()
            }
            dialogBuilder.setNegativeButton(CANCEL_BUTTON_TEXT) { dialogInterface, _ ->
                dialogInterface.cancel()
            }
            dialogBuilder.show()
        }

        unsettledExpensesAdapter.setDeleteExpenseCallback {
            val dialogBuilder = MaterialAlertDialogBuilder(activity as MainActivity)
            dialogBuilder.setTitle(DELETE_EXPENSE_DIALOG_TITLE)
            dialogBuilder.setMessage(DELETE_EXPENSE_MESSAGE)
            dialogBuilder.setPositiveButton(DELETE_BUTTON_TEXT) { _, _ ->
                lifecycleScope.launch {
                    withContext(Dispatchers.IO) {
                        expenseViewModel.deleteExpense(token, it.id).join()
                        expenseViewModel.getCurrentUserUnsettledExpenses(token)
                    }
                }
                Toast.makeText(activity, "Expense deleted!", Toast.LENGTH_SHORT).show()
            }
            dialogBuilder.setNegativeButton(CANCEL_BUTTON_TEXT) { dialogInterface, _ ->
                dialogInterface.cancel()
            }
            dialogBuilder.show()
        }

        initRecyclerView()

        expenseViewModel.unsettledExpenses.observe(viewLifecycleOwner, { response ->
            when (response) {
                is Resource.Loading -> {

                }
                is Resource.Success -> {
                    val expenses = response.data?.payload
                    unsettledExpensesAdapter.differ.submitList(expenses)
                }
                is Resource.Error -> {
                    response.message?.let {
                        Toast.makeText(activity, "An error occurred $it", Toast.LENGTH_LONG).show()
                    }
                }
            }
        })

        binding.addExpenseBtn.setOnClickListener {
            findNavController().navigate(R.id.action_expensesFragment_to_addExpenseFragment)
        }

        getUnsettledExpenses()
    }

    private fun initRecyclerView() {
        binding.unsettledExpensesRv.apply {
            adapter = unsettledExpensesAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

    private fun getUnsettledExpenses() {
        expenseViewModel.getCurrentUserUnsettledExpenses(token)
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