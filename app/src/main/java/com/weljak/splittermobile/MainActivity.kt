package com.weljak.splittermobile

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.weljak.splittermobile.databinding.ActivityMainBinding
import com.weljak.splittermobile.presentation.adapter.*
import com.weljak.splittermobile.presentation.viewmodel.expense.ExpenseViewModel
import com.weljak.splittermobile.presentation.viewmodel.expense.ExpenseViewModelFactory
import com.weljak.splittermobile.presentation.viewmodel.friend.FriendViewModel
import com.weljak.splittermobile.presentation.viewmodel.friend.FriendViewModelFactory
import com.weljak.splittermobile.presentation.viewmodel.user.UserViewModel
import com.weljak.splittermobile.presentation.viewmodel.user.UserViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var userViewModelFactory: UserViewModelFactory

    @Inject
    lateinit var friendsAdapter: FriendsAdapter

    @Inject
    lateinit var sentFriendRequestsAdapter: SentFriendRequestsAdapter

    @Inject
    lateinit var receivedFriendRequestsAdapter: ReceivedFriendRequestsAdapter

    @Inject
    lateinit var friendsViewModelFactory: FriendViewModelFactory

    @Inject
    lateinit var expenseViewModelFactory: ExpenseViewModelFactory

    @Inject
    lateinit var unsettledExpensesAdapter: UnsettledExpensesAdapter

    @Inject
    lateinit var debtorsAdapter: DebtorsAdapter
    lateinit var userViewModel: UserViewModel
    lateinit var friendViewModel: FriendViewModel
    lateinit var expenseViewModel: ExpenseViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController = navHostFragment.navController

        binding.bnvSplitter.setupWithNavController(
            navController
        )
        userViewModel = ViewModelProvider(this, userViewModelFactory).get(UserViewModel::class.java)
        friendViewModel =
            ViewModelProvider(this, friendsViewModelFactory).get(FriendViewModel::class.java)
        expenseViewModel =
            ViewModelProvider(this, expenseViewModelFactory).get(ExpenseViewModel::class.java)
    }

    fun makeNavBarVisible() {
        binding.bnvSplitter.visibility = View.VISIBLE
    }
}