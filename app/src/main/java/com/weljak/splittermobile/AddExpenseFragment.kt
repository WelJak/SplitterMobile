package com.weljak.splittermobile

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.weljak.splittermobile.data.model.expense.Currency
import com.weljak.splittermobile.data.model.expense.Debtor
import com.weljak.splittermobile.data.model.expense.ExpenseCreationForm
import com.weljak.splittermobile.data.model.expense.ExpenseType
import com.weljak.splittermobile.data.util.Resource
import com.weljak.splittermobile.databinding.FragmentAddExpenseBinding
import com.weljak.splittermobile.presentation.adapter.DebtorsAdapter
import com.weljak.splittermobile.presentation.viewmodel.expense.ExpenseViewModel
import com.weljak.splittermobile.presentation.viewmodel.friend.FriendViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.math.BigDecimal
import java.util.*


class AddExpenseFragment : Fragment() {
    private lateinit var binding: FragmentAddExpenseBinding
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var debtorsAdapter: DebtorsAdapter
    private lateinit var expenseViewModel: ExpenseViewModel
    private lateinit var friendViewModel: FriendViewModel
    private lateinit var debtorsSpinnerAdapter: ArrayAdapter<String>
    private lateinit var token: String
    private var previousPayer: String = ""
    private val friendList = HashSet<String>()
    private var displayMessage = false
    private var selectedPayer = ""

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

        debtorsAdapter.setDeleteItemButtonCallback { debtor ->
            debtorsSpinnerAdapter.add(debtor.name)
        }

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

        expenseViewModel.createExpenseResponse.observe(viewLifecycleOwner, { response ->
            when (response) {
                is Resource.Loading -> {
                    displayMessage = true
                }
                is Resource.Success -> {
                    if (displayMessage) {
                        Toast.makeText(activity, "Expense created!", Toast.LENGTH_SHORT).show()
                        displayMessage = false
                    }

                }
                is Resource.Error -> {
                    if (displayMessage) {
                        response.message?.let {
                            Toast.makeText(activity, "An error occurred $it", Toast.LENGTH_LONG)
                                .show()
                        }
                        displayMessage = false
                    }
                }
            }
        })

        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                friendViewModel.getCurrentUserFriendList(token).join()
                val debtorSpinnerChoices = ArrayList(friendList.toList())
                debtorSpinnerChoices.add(0, "")
                val payersList = ArrayList(debtorSpinnerChoices)
                payersList.add(sharedPreferences.getString("username", ""))
                val spinnerAdapter =
                    ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, payersList)
                spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

                debtorsSpinnerAdapter = ArrayAdapter(
                    requireActivity(),
                    android.R.layout.simple_spinner_item,
                    payersList
                )
                debtorsSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                withContext(Dispatchers.Main) {
                    binding.paidBySpinner.adapter = spinnerAdapter
                    binding.debtorsSpinner.adapter = debtorsSpinnerAdapter
                }
            }
        }

        binding.paidBySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (previousPayer.isNotBlank()) {
                    debtorsSpinnerAdapter.add(previousPayer)
                }
//                val currentPayer = binding.paidBySpinner.selectedItem.toString()
                val currentPayer = binding.paidBySpinner.selectedItem.toString()
                debtorsSpinnerAdapter.remove(currentPayer)
                previousPayer = currentPayer
                selectedPayer = currentPayer
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

        }
        initRecyclerView()

        binding.addDebtorButton.setOnClickListener {
            addDebtor()
        }

        binding.createExpenseBtn.setOnClickListener {
            createExpense()
            findNavController().navigate(R.id.action_addExpenseFragment_to_expensesFragment)
        }
    }

    private fun createExpense() {
        val expenseBreakDown = debtorsAdapter.differ.currentList.map {
            it.name to it.debt
        }.toMap()
        val expenseCreationForm = ExpenseCreationForm(
            binding.expenseDescEt.text.toString(),
            selectedPayer,
            BigDecimal(binding.lenderPaidEt.text.toString()),
            BigDecimal(binding.totalSumEt.text.toString()),
            Currency.valueOf(binding.currencySpinner.selectedItem.toString()),
            ExpenseType.valueOf(
                binding.expenseTypeSpinner.selectedItem.toString().replace(" ", "_").uppercase()
            ),
            null,
            expenseBreakDown
        )
        lifecycleScope.launch(Dispatchers.IO) {
            expenseViewModel.createExpense(token, expenseCreationForm).join()
        }
    }

    private fun addDebtor() {
        val selectedItem = binding.debtorsSpinner.selectedItem.toString()
        val amount = binding.debtAmountTi.text.toString()
        if (selectedItem.isNotBlank() && amount.isNotBlank()) {
            val debtors = ArrayList(debtorsAdapter.differ.currentList)
            val debtor = Debtor(selectedItem, BigDecimal(amount))
            debtorsSpinnerAdapter.remove(selectedItem)
            debtors.add(debtor)
            debtorsAdapter.differ.submitList(debtors)
            binding.debtAmountTi.setText("")
        }
    }

    private fun initRecyclerView() {
        binding.expenseBreakdownRv.apply {
            adapter = debtorsAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

    override fun onPause() {
        super.onPause()
        debtorsAdapter.differ.submitList(ArrayList())
    }
}