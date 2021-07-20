package com.weljak.splittermobile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.weljak.splittermobile.databinding.ActivityMainBinding
import com.weljak.splittermobile.presentation.viewmodel.user.UserViewModel
import com.weljak.splittermobile.presentation.viewmodel.user.UserViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    @Inject
    lateinit var viewModelFactory: UserViewModelFactory
    lateinit var viewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this, viewModelFactory).get(UserViewModel::class.java)
    }
}