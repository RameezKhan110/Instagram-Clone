package com.example.testing.auth

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.IntentSender
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.net.toFile
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.testing.MainActivity
import com.example.testing.R
import com.example.testing.auth.room.User
import com.example.testing.auth.viewmodel.AuthViewModel
import com.example.testing.auth.viewmodel.FirebaseAuthViewModel
import com.example.testing.databinding.FragmentLoginBinding
import com.example.testing.home.firestore.repository.viewmodel.FireStoreHomeViewModel
import com.example.testing.utils.FirebaseResource
import com.example.testing.utils.NetworkResponse
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import java.lang.Exception

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private val authViewModel: AuthViewModel by activityViewModels()
    val REQ_ONE_TAP = 2

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient
    private val firebaseAuthViewModel: FirebaseAuthViewModel by activityViewModels()
    private val fireStoreHomeViewModel: FireStoreHomeViewModel by activityViewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        firebaseAuth = FirebaseAuth.getInstance()
    }
    override fun onStart() {
        super.onStart()
        val currentUser = firebaseAuth.currentUser
        if (currentUser != null) {
            startActivity(Intent(requireContext(), MainActivity::class.java))
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(layoutInflater, container, false)

        fireStoreHomeViewModel.getRemoteConfigStrings()
        fireStoreHomeViewModel.getLiveRemoteConfigStrings.observe(viewLifecycleOwner) {
            when (it) {
                is NetworkResponse.Error -> {
                    Log.d("TAG", "Error in fetching remote configs")
                }
                is NetworkResponse.Loading -> {
                    Log.d("TAG", "Fetching remote configs")
                }
                is NetworkResponse.Success -> {
                    binding.loginBtn.text = it.data
                }
            }
        }
        activity?.actionBar?.hide()
//        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//            .requestIdToken(getString(R.string.default_web_client_id))
//            .requestEmail()
//            .build()
//
//        googleSignInClient = GoogleSignIn.getClient(requireContext(), gso)
//
//        if (userIsLoggedIn()) {
//            startActivity(Intent(requireContext(), MainActivity::class.java))
//            requireActivity().finish()
//        }

        binding.loginBtn.setOnClickListener {
            val userEmailLogin = binding.userEmailLogin.text.toString().trim()
            val userPasswordLogin = binding.userPasswordLogin.text.toString().trim()

            if (userEmailLogin.isNotEmpty() && userPasswordLogin.isNotEmpty()) {

                firebaseAuthViewModel.signInUser(userEmailLogin, userPasswordLogin)

                firebaseAuthViewModel.loginLiveData.observe(viewLifecycleOwner) {
                    when (it) {
                        is FirebaseResource.Error -> {
                            Toast.makeText(
                                requireContext(),
                                it.exception.message,
                                Toast.LENGTH_SHORT
                            ).show()
                            binding.loginProgressBar.visibility = View.GONE
                        }

                        FirebaseResource.Loading -> {
                            binding.loginProgressBar.visibility = View.VISIBLE

                        }

                        is FirebaseResource.Success -> {

                            binding.loginProgressBar.visibility = View.GONE
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
                                            saveUserInfoLogin(userDetails)

                                        }
                                        Toast.makeText(requireContext(), "Logging In", Toast.LENGTH_SHORT)
                                            .show()
                                        startActivity(Intent(requireContext(), MainActivity::class.java))
                                        requireActivity().finish()
                                    }
                                }
                            }
                        }
                    }
                }

//                authViewModel.loginUser(userEmailLogin, userPasswordLogin)
//                authViewModel.loginLiveUser.observe(viewLifecycleOwner) {
//                    if (it != null) {
//                        Toast.makeText(requireContext(), "Logging in", Toast.LENGTH_SHORT).show()
//                        startActivity(Intent(requireContext(), MainActivity::class.java))
//                        requireActivity().finish()
//                        saveUserCredentials(userEmailLogin, userPasswordLogin)
//
//                    } else {
//                        binding.errorTv.text = "*email or password is in incorrect"
//                        binding.errorTv.visibility = View.VISIBLE
//                    }
//                }
            } else {
                binding.errorTv.text = "all fields are required"
                binding.errorTv.visibility = View.VISIBLE
            }
        }


        binding.goToRegister.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

        binding.signInWithGoogleBtn.setOnClickListener {
//            signIn()
        }

        return binding.root
    }

    fun saveUserInfoLogin(
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

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//
//        if (requestCode == REQ_ONE_TAP) {
//            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
//            try {
//                val account = task.getResult(ApiException::class.java)!!
//                firebaseAuthWithGoogle(account.idToken!!)
//                val userGoogleImage = account.photoUrl?.toString()
//                authViewModel.addUser(User(account.id!!,
//                    userGoogleImage!!, account.displayName, account.email))
//            } catch (e: ApiException) {
//                Log.w("TAG", "Google sign in failed", e)
//            }
//        }
//    }
//
//    private fun firebaseAuthWithGoogle(idToken: String) {
//        val credential = GoogleAuthProvider.getCredential(idToken, null)
//        firebaseAuth.signInWithCredential(credential)
//            .addOnCompleteListener { task ->
//                if (task.isSuccessful) {
//                    Toast.makeText(requireContext(), "Successfully Logged in", Toast.LENGTH_SHORT).show()
//                    startActivity(Intent(requireContext(), MainActivity::class.java))
//                } else {
//                    Toast.makeText(requireContext(), "Login failed", Toast.LENGTH_SHORT).show()
//                }
//            }
//    }
//
//    private fun signIn() {
//        val signInIntent = googleSignInClient.signInIntent
//        startActivityForResult(signInIntent, REQ_ONE_TAP)
//    }

//    private fun saveUserCredentials(userEmail: String, userPassword: String) {
//        val sharedPreferences =
//            requireActivity().getSharedPreferences("user_credentials", Context.MODE_PRIVATE)
//        val editor = sharedPreferences.edit()
//        editor.putString("username", userEmail)
//        editor.putString("password", userPassword)
//        editor.apply()
//    }

//    private fun userIsLoggedIn(): Boolean {
//        val sharedPreferences =
//            requireActivity().getSharedPreferences("user_credentials", Context.MODE_PRIVATE)
//        return sharedPreferences.contains("username") && sharedPreferences.contains("password")
//    }
}