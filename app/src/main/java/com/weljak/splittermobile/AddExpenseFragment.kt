package com.weljak.splittermobile

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.weljak.splittermobile.data.model.expense.Debtor
import com.weljak.splittermobile.data.util.Resource
import com.weljak.splittermobile.databinding.FragmentAddExpenseBinding
import com.weljak.splittermobile.databinding.FragmentExpensesBinding
import com.weljak.splittermobile.presentation.adapter.DebtorsAdapter
import com.weljak.splittermobile.presentation.adapter.UnsettledExpensesAdapter
import com.weljak.splittermobile.presentation.util.ViewUtils
import com.weljak.splittermobile.presentation.viewmodel.expense.ExpenseViewModel
import com.weljak.splittermobile.presentation.viewmodel.friend.FriendViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.math.BigDecimal
import java.util.ArrayList
import java.util.HashSet

class AddExpenseFragment : Fragment() {
    private lateinit var binding: FragmentAddExpenseBinding
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var debtorsAdapter: DebtorsAdapter
    private lateinit var expenseViewModel: ExpenseViewModel
    private lateinit var friendViewModel: FriendViewModel
    private lateinit var token: String
    private val friendList = HashSet<String>()

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
        debtorsAdapter = (activity as MainActivity).debtorsAdapter
        expenseViewModel = (activity as MainActivity).expenseViewModel
        friendViewModel = (activity as MainActivity).friendViewModel
        token = "Bearer ${sharedPreferences.getString("token", "")}"

        friendViewModel.currentUserFriendship.observe(viewLifecycleOwner, { response ->
            when (response) {
                is Resource.Loading -> {
                    friendList.clear()
                }
                is Resource.Success -> {
                    response.data?.payload?.friendList?.forEach { friend -> friendList.add(friend.username) }
                }
                is Resource.Error -> {
                    response.message?.let {
                        Toast.makeText(activity, "An error occurred $it", Toast.LENGTH_LONG).show()
                    }
                }
            }
        })
        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                friendViewModel.getCurrentUserFriendList(token).join()
                val list = friendList.toList()
                debtorsAdapter.friendList = list
                val payersList = ArrayList(list)
                    payersList.add(sharedPreferences.getString("username", ""))
                val spinnerAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, payersList)
                spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                withContext(Dispatchers.Main) {
                    binding.paidBySpinner.adapter = spinnerAdapter
                }
            }
        }
        initRecyclerView()

        binding.addDebtorBtn.setOnClickListener {
            addDebtor()
        }
    }

    private fun getFriendList() {
        friendViewModel.getCurrentUserFriendList(token)
    }

    private fun addDebtor() {
        val debtors = ArrayList(debtorsAdapter.differ.currentList)
        debtors.add(Debtor("", BigDecimal.ZERO))
        debtorsAdapter.differ.submitList(debtors)
    }

    private fun initRecyclerView() {
        binding.expenseBreakdownRv.apply {
            adapter = debtorsAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }
}