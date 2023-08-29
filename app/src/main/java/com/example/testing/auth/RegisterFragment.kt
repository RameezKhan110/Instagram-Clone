package com.example.testing.auth

import android.app.Activity
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
import com.example.testing.R
import com.example.testing.auth.room.Auth
import com.example.testing.auth.viewmodel.AuthViewModel
import com.example.testing.databinding.FragmentRegisterBinding
import com.github.drjacky.imagepicker.ImagePicker


class RegisterFragment : Fragment() {

    private lateinit var binding: FragmentRegisterBinding
    private val authViewModel: AuthViewModel by activityViewModels()
    private var userImageRegister: String = ""

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
            val userNameRegister = binding.userNameRegister.text.toString()
            val userEmailRegister = binding.userEmailRegister.text.toString()
            val userPasswordRegister = binding.userPasswordRegister.text.toString()

            if (userImageRegister.isNotEmpty() && userNameRegister.isNotEmpty() && userEmailRegister.isNotEmpty() && userPasswordRegister.isNotEmpty()) {
                authViewModel.isEmailRegistered(userEmailRegister).observe(viewLifecycleOwner) {
                    Log.d("TAG", "it: " +it)
                    if (it == null) {
                        authViewModel.registerUser(
                            Auth(
                                0,
                                userImageRegister,
                                userNameRegister,
                                userEmailRegister,
                                userPasswordRegister
                            )
                        )
                        Toast.makeText(requireContext(), "Registered", Toast.LENGTH_SHORT).show()
                        findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
                    } else {
                        binding.errorTvRegister.text = "*email already registered"
                        binding.errorTvRegister.visibility = View.VISIBLE
                    }
                }

            } else {
                binding.errorTvRegister.text = "all fields are required"
                binding.errorTvRegister.visibility = View.VISIBLE


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
                //Image Uri will not be null for RESULT_OK

                data?.data?.let {
                    userImageRegister = it.toFile().absolutePath.toString()
                    binding.userImageRegister.setImageURI(it)
                }
            }
        }
}