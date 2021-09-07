package com.weljak.splittermobile

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.weljak.splittermobile.databinding.FragmentAddExpenseBinding
import com.weljak.splittermobile.databinding.FragmentExpensesBinding
import com.weljak.splittermobile.presentation.adapter.UnsettledExpensesAdapter
import com.weljak.splittermobile.presentation.viewmodel.expense.ExpenseViewModel

class AddExpenseFragment : Fragment() {
    private lateinit var binding: FragmentAddExpenseBinding
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var expenseViewModel: ExpenseViewModel
    private lateinit var token: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_expense, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAddExpenseBinding.bind(view)
        sharedPreferences =
            requireActivity().getSharedPreferences("user_data", Context.MODE_PRIVATE)
        expenseViewModel = (activity as MainActivity).expenseViewModel
        token = "Bearer ${sharedPreferences.getString("token", "")}"
    }
}