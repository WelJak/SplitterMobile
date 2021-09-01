package com.weljak.splittermobile

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.weljak.splittermobile.data.util.Resource
import com.weljak.splittermobile.databinding.FragmentExpensesBinding
import com.weljak.splittermobile.presentation.adapter.UnsettledExpensesAdapter
import com.weljak.splittermobile.presentation.util.ConnectionUtils
import com.weljak.splittermobile.presentation.viewmodel.expense.ExpenseViewModel

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
        sharedPreferences = requireActivity().getSharedPreferences("user_data", Context.MODE_PRIVATE)
        unsettledExpensesAdapter = (activity as MainActivity).unsettledExpensesAdapter
        expenseViewModel = (activity as MainActivity).expenseViewModel
        token = "Bearer ${sharedPreferences.getString("token", "")}"

        unsettledExpensesAdapter.setSettleExpenseCallback {
            expenseViewModel.settleExpense(token, it.id)
        }

        unsettledExpensesAdapter.setDeleteExpenseCallback {
            expenseViewModel.deleteExpense(token, it.id)
        }

        initRecyclerView()

        expenseViewModel.unsettledExpenses.observe(viewLifecycleOwner, { response ->
            when(response) {
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
}