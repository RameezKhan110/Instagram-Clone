package com.example.testing.auth

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.net.toFile
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.testing.MainActivity
import com.example.testing.R
import com.example.testing.auth.room.Auth
import com.example.testing.auth.viewmodel.AuthViewModel
import com.example.testing.auth.viewmodel.FirebaseAuthViewModel
import com.example.testing.databinding.FragmentRegisterBinding
import com.example.testing.utils.FirebaseResource
import com.example.testing.utils.NetworkResponse
import com.github.drjacky.imagepicker.ImagePicker


class RegisterFragment : Fragment() {

    private lateinit var binding: FragmentRegisterBinding
    private val authViewModel: AuthViewModel by activityViewModels()
    private var userImageRegister: Uri? = null
    private var userNameRegister: String = ""
    private var userEmailRegister: String = ""
    private var userPasswordRegister: String = ""
    private val firebaseAuthViewModel: FirebaseAuthViewModel by activityViewModels()

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterBinding.inflate(layoutInflater)

        activity?.actionBar?.hide()

        binding.uploadPhotoBtn.setOnClickListener {
            startUserImageResult_register.launch(
                ImagePicker.with(requireActivity())
                    .cropSquare()         //Final image size will be less than 1 MB(Optional)
                    .galleryOnly()
                    .createIntent()
            )
        }


        binding.registerBtn.setOnClickListener {
            userNameRegister = binding.userNameRegister.text.toString()
            userEmailRegister = binding.userEmailRegister.text.toString()
            userPasswordRegister = binding.userPasswordRegister.text.toString()

            if (userNameRegister.isNotEmpty() && userEmailRegister.isNotEmpty() && userPasswordRegister.isNotEmpty()) {

                firebaseAuthViewModel.registerUser(
                    userEmailRegister,
                    userPasswordRegister
                )

//                authViewModel.isEmailRegistered(userEmailRegister).observe(viewLifecycleOwner) {
//                    Log.d("TAG", "it: " +it)
//                    if (it == null) {
//                        authViewModel.registerUser(
//                            Auth(
//                                0,
//                                userImageRegister,
//                                userNameRegister,
//                                userEmailRegister,
//                                userPasswordRegister
//                            )
//                        )
//                        Toast.makeText(requireContext(), "Registered", Toast.LENGTH_SHORT).show()
//                        findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
//                    } else {
//                        binding.errorTvRegister.text = "*email already registered"
//                        binding.errorTvRegister.visibility = View.VISIBLE
//                    }
//                }

            } else {
                binding.errorTvRegister.text = "all fields are required"
                binding.errorTvRegister.visibility = View.VISIBLE


            }
        }

        firebaseAuthViewModel.registerLiveData.observe(viewLifecycleOwner) {
            when (it) {
                is FirebaseResource.Error -> {
                    Toast.makeText(requireContext(), it.exception.message, Toast.LENGTH_SHORT)
                        .show()
                    binding.registerProgressBar.visibility = View.GONE
                }

                FirebaseResource.Loading -> {
                    binding.registerProgressBar.visibility = View.VISIBLE
                }

                is FirebaseResource.Success -> {
                    userImageRegister?.let { it1 ->
                        firebaseAuthViewModel.saveUserDetails(
                            it1,
                            userNameRegister,
                            userEmailRegister,
                            userPasswordRegister
                        )
                    }
                    firebaseAuthViewModel.saveLiveUserDetails.observe(viewLifecycleOwner) {
                        when (it) {
                            is NetworkResponse.Error -> {
                                Toast.makeText(
                                    requireContext(),
                                    "Unable to save credentials",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }

                            is NetworkResponse.Loading -> {
                                Toast.makeText(
                                    requireContext(),
                                    "Please wait, saving credentials",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }

                            is NetworkResponse.Success -> {
                                Toast.makeText(requireContext(), "Registered", Toast.LENGTH_SHORT)
                                    .show()
                                binding.registerProgressBar.visibility = View.GONE
                                firebaseAuthViewModel.getUserDetails()
                                firebaseAuthViewModel.getLiveUserDetails.observe(viewLifecycleOwner) {
                                    when (it) {
                                        is NetworkResponse.Error -> {
                                            Toast.makeText(
                                                requireContext(),
                                                it.toString(),
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }

                                        is NetworkResponse.Loading -> {

                                        }

                                        is NetworkResponse.Success -> {
                                            it.data?.let { userDetails ->
                                                Log.d("TAG", "calling from login" + userDetails)
                                                saveUserInfo(userDetails)

                                            }
                                            startActivity(
                                                Intent(
                                                    requireContext(),
                                                    MainActivity::class.java
                                                )
                                            )
                                            requireActivity().finish()
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
//
//        }

        binding.goToLogin.setOnClickListener {
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }

        return binding.root
    }

    private val startUserImageResult_register =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            val resultCode = result.resultCode
            val data = result.data

            if (resultCode == Activity.RESULT_OK) {
                data?.data?.let {
                    userImageRegister = it
                    binding.userImageRegister.setImageURI(it)
                }
            }

        }

    private fun saveUserInfo(
        user: com.example.testing.auth.model.User
    ) {
        val prefs = context?.getSharedPreferences("user_info", Context.MODE_PRIVATE)
        val editor = prefs?.edit()

        editor?.putString("user_image", user.userImage)
        editor?.putString("user_name", user.userName)
        editor?.putString("user_email", user.userEmail)
        editor?.putString("user_password", user.userPassword)
        editor?.putString("user_id", user.userId)

        editor?.apply()
    }
}