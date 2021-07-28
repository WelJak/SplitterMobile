package com.weljak.splittermobile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.weljak.splittermobile.data.util.Resource
import com.weljak.splittermobile.databinding.FragmentRegisterBinding
import com.weljak.splittermobile.presentation.viewmodel.user.UserViewModel

class RegisterFragment : Fragment() {
    private lateinit var viewModel: UserViewModel
    private lateinit var binding: FragmentRegisterBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRegisterBinding.bind(view)
        viewModel = (activity as MainActivity).userViewModel
        viewModel.registerData.observe(viewLifecycleOwner, { response ->
            when (response) {
                is Resource.Loading -> {
                    showProgressBar()
                }
                is Resource.Success -> {
                    hideProgressBar()
                    Toast.makeText(activity, "User ${response.data?.payload?.username} successfully created!", Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
                }
                is Resource.Error -> {
                    hideProgressBar()
                    response.message?.let {
                        Toast.makeText(activity,"An error occurred : $it", Toast.LENGTH_LONG).show()
                    }
                }
            }
        })
        binding.registerBtn.setOnClickListener { registerButtonClickCallback() }
    }

    private fun registerButtonClickCallback() {
        viewModel.registerUser(
            binding.usernameEditText.text.toString(),
            binding.emailEditText.text.toString(),
            binding.passwordEditText.text.toString()
        )
    }

    private fun showProgressBar(){
        binding.progressBar3.visibility = View.VISIBLE
    }

    private fun hideProgressBar(){
        binding.progressBar3.visibility = View.INVISIBLE
    }
}