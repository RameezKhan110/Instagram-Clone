package com.example.testing.auth

import android.content.Context
import android.content.Intent
import android.content.IntentSender
import android.os.Bundle
import android.util.Log
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
    private var oneTapClient: SignInClient? = null
    private lateinit var firebaseAuth: FirebaseAuth
    val options: Bundle? = null
    private lateinit var signInRequest: BeginSignInRequest
    private lateinit var googleSignInClient: GoogleSignInClient

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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(layoutInflater, container, false)


        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(requireContext(), gso)

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
                    if (it != null) {
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

        binding.signInWithGoogleBtn.setOnClickListener {
            signIn()
        }

        return binding.root
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQ_ONE_TAP) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)!!
                Log.d("TAG", "firebaseAuthWithGoogle:" + account.id)
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Log.w("TAG", "Google sign in failed", e)
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("TAG", "signInWithCredential:success")
                    Toast.makeText(requireContext(), "Successfully Logged in", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(requireContext(), MainActivity::class.java))
                } else {
                    Toast.makeText(requireContext(), "Login failed", Toast.LENGTH_SHORT).show()
                    Log.w("TAG", "signInWithCredential:failure", task.exception)
                }
            }
    }

    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, REQ_ONE_TAP)
    }

    private fun saveUserCredentials(userEmail: String, userPassword: String) {
        val sharedPreferences =
            requireActivity().getSharedPreferences("user_credentials", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("username", userEmail)
        editor.putString("password", userPassword)
        editor.apply()
    }

    fun userIsLoggedIn(): Boolean {
        val sharedPreferences =
            requireActivity().getSharedPreferences("user_credentials", Context.MODE_PRIVATE)
        return sharedPreferences.contains("username") && sharedPreferences.contains("password")
    }
}