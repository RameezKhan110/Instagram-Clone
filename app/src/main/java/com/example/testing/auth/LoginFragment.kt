package com.example.testing.auth

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.testing.MainActivity
import com.example.testing.R
import com.example.testing.auth.viewmodel.AuthViewModel
import com.example.testing.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private val authViewModel: AuthViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(layoutInflater, container, false)

        if (userIsLoggedIn()) {
            startActivity(Intent(requireContext(), MainActivity::class.java))
            requireActivity().finish()
        }

        binding.loginBtn.setOnClickListener {
            val userEmailLogin = binding.userEmailLogin.text.toString().trim()
            val userPasswordLogin = binding.userPasswordLogin.text.toString().trim()

                if (userEmailLogin.isNotEmpty() && userPasswordLogin.isNotEmpty()) {
                    authViewModel.loginUser(userEmailLogin, userPasswordLogin)
                    authViewModel.loginLiveUser.observe(viewLifecycleOwner) {
                        if(it != null) {
                            Toast.makeText(requireContext(), "Logging in", Toast.LENGTH_SHORT).show()
                            startActivity(Intent(requireContext(), MainActivity::class.java))
                            requireActivity().finish()
                            saveUserCredentials(userEmailLogin, userPasswordLogin)

                        } else {
                            binding.errorTv.text = "*email or password is in incorrect"
                            binding.errorTv.visibility = View.VISIBLE
                        }
                    }
                } else {
                    binding.errorTv.text = "all fields are required"
                    binding.errorTv.visibility = View.VISIBLE
                }
        }

        binding.goToRegister.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

        return binding.root
    }

    private fun saveUserCredentials(userEmail: String, userPassword: String) {
        val sharedPreferences = requireActivity().getSharedPreferences("user_credentials", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("username", userEmail)
        editor.putString("password", userPassword)
        editor.apply()
    }

    fun userIsLoggedIn(): Boolean {
        val sharedPreferences = requireActivity().getSharedPreferences("user_credentials", Context.MODE_PRIVATE)
        return sharedPreferences.contains("username") && sharedPreferences.contains("password")
    }
}