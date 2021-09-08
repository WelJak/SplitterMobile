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
import com.weljak.splittermobile.databinding.FragmentAddExpenseBinding
import com.weljak.splittermobile.databinding.FragmentExpensesBinding
import com.weljak.splittermobile.presentation.adapter.DebtorsAdapter
import com.weljak.splittermobile.presentation.adapter.UnsettledExpensesAdapter
import com.weljak.splittermobile.presentation.util.ViewUtils
import com.weljak.splittermobile.presentation.viewmodel.expense.ExpenseViewModel
import com.weljak.splittermobile.presentation.viewmodel.friend.FriendViewModel
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
        getFriendList()
        debtorsAdapter.friendList = friendList.toList()
        initRecyclerView()
    }

    private fun getFriendList() {
        friendViewModel.getCurrentUserFriendList(token)
    }

    private fun initRecyclerView() {
        binding.expenseBreakdownRv.apply {
            adapter = debtorsAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }
}