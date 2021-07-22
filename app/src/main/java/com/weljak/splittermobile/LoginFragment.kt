package com.weljak.splittermobile

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.weljak.splittermobile.data.util.Resource
import com.weljak.splittermobile.databinding.FragmentLoginBinding
import com.weljak.splittermobile.presentation.viewmodel.user.UserViewModel

class LoginFragment() : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private lateinit var viewModel: UserViewModel
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var currentUser: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPreferences = requireActivity().getSharedPreferences("user_data", Context.MODE_PRIVATE)
        binding = FragmentLoginBinding.bind(view)
        if (sharedPreferences.getString("token", "")?.isNotBlank() == true) {
            findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
        }
        viewModel= (activity as MainActivity).viewModel
        viewModel.userToken.observe(viewLifecycleOwner, { response ->
            when(response) {
                is Resource.Loading -> {
                    showProgressBar()
                }
                is Resource.Success -> {
                    hideProgressBar()
                    response.data?.let {
                        val editor = sharedPreferences.edit()
                        Log.i("login", "${response.data.payload?.token}  $currentUser")
                        editor?.putString("token", response.data.payload?.token)
                        editor?.putString("username", currentUser)
                        editor?.apply()
                        findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
                    }
                }
                is Resource.Error -> {
                    hideProgressBar()
                    response.message?.let {
                        Toast.makeText(activity,"An error occurred : $it", Toast.LENGTH_LONG).show()
                    }
                }
            }
        })

        binding.loginButton.setOnClickListener {
            loginButtonPressedCallback()
        }

        binding.registerButton.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
    }

    private fun loginButtonPressedCallback() {
        viewModel.authenticateUser(
            binding.usernameEditText.text.toString(),
            binding.passwordEditText.text.toString()
        )
        currentUser = binding.usernameEditText.text.toString()
    }

    private fun showProgressBar(){
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun hideProgressBar(){
        binding.progressBar.visibility = View.INVISIBLE
    }
}