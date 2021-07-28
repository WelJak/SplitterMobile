package com.weljak.splittermobile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.weljak.splittermobile.databinding.ActivityMainBinding
import com.weljak.splittermobile.presentation.adapter.FriendsAdapter
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
    lateinit var friendsViewModelFactory: FriendViewModelFactory
    lateinit var userViewModel: UserViewModel
    lateinit var friendViewModel: FriendViewModel

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
        friendViewModel = ViewModelProvider(this, friendsViewModelFactory).get(FriendViewModel::class.java)
    }

    fun makeNavBarVisible() {
        binding.bnvSplitter.visibility = View.VISIBLE
    }
}