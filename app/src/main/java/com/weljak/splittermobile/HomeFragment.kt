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
import com.weljak.splittermobile.data.util.Resource
import com.weljak.splittermobile.databinding.FragmentHomeBinding
import com.weljak.splittermobile.presentation.viewmodel.user.UserViewModel

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: UserViewModel
    private var isLoading = false
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPreferences = requireActivity().getSharedPreferences("user_data", Context.MODE_PRIVATE)
        binding = FragmentHomeBinding.bind(view)
        viewModel = (activity as MainActivity).viewModel
        viewModel.userData.observe(viewLifecycleOwner, { response ->
            when(response) {
                is Resource.Loading -> {
                    showProgressBar()
                }
                is Resource.Success -> {
                    hideProgressBar()
                    val welcomeText = "Hello ${response.data?.payload?.username} your email is ${response.data?.payload?.email}"
                    binding.helloTextView.text = welcomeText
                }
                is Resource.Error -> {
                    hideProgressBar()
                    response.message?.let {
                        Toast.makeText(activity,"An error occurred : $it", Toast.LENGTH_LONG).show()
                    }
                }
            }
        })
        getCurrentUserData()
    }

    private fun getCurrentUserData() {
        val username = sharedPreferences.getString("username", "")
        val token = sharedPreferences.getString("token", "")
        Log.i("app", username + token)
        if (username != null && token != null) {
            viewModel.getUserData(
                username,
                "Bearer $token"
            )
        } else {
            Toast.makeText(activity, "Not enough data to get user details", Toast.LENGTH_LONG).show()
        }
    }

    private fun showProgressBar(){
        isLoading = true
        binding.progressBar2.visibility = View.VISIBLE
    }

    private fun hideProgressBar(){
        isLoading = false
        binding.progressBar2.visibility = View.INVISIBLE
    }
}