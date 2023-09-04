package com.example.testing.profile

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.testing.R
import com.example.testing.auth.viewmodel.AuthViewModel
import com.example.testing.databinding.FragmentProfileBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.material.tabs.TabLayoutMediator

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private val authViewModel: AuthViewModel by activityViewModels()
    private var userImage: String = ""
    private var userName: String = ""
    private var userEmail: String = ""
    override fun onResume() {
        super.onResume()
        activity?.actionBar?.setTitle("Profile")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(layoutInflater, container, false)

        getUserDetailsProfile()
        val account = GoogleSignIn.getLastSignedInAccount(requireContext())

        Glide.with(requireContext()).load(userImage).into(binding.userImageProfile)
        binding.userNameProfile.text = userName
        binding.userBioProfile.text = userEmail
//        authViewModel.getDataAsPerEmail(getUserEmailProfile()).observe(viewLifecycleOwner) { userData ->
//            userData.map {userData ->
//                binding.userImageProfile.setImageURI(userData.userImage.toUri())
//                binding.userNameProfile.text = userData.userName
//                binding.userBioProfile.text = userData.userEmail
//            }
//        }

        binding.apply {
            viewPager.adapter = PageAdapter(requireActivity())
            TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                when (position) {
                    0 -> {
                        tab.view.tab?.setIcon(R.drawable.baseline_home_24)
                    }
                    1 -> {
                        tab.view.tab?.setIcon(R.drawable.reels)
                    }
                    2 -> {
                        tab.view.tab?.setIcon(R.drawable.tag)
                    }
                }
            }.attach()
        }

        binding.savedPostBtn.setOnClickListener {

            findNavController().navigate(R.id.action_profileFragment_to_savedPostFragment)
        }


        return binding.root
    }

    private fun getUserEmailProfile(): String {
        val sharedPreferences =
            requireActivity().getSharedPreferences("user_credentials", Context.MODE_PRIVATE)
        val currentUserEmail = sharedPreferences.getString("username", null)
        return currentUserEmail.toString()
    }

    private fun getUserDetailsProfile() {
        val prefs = requireContext().getSharedPreferences("user_info", Context.MODE_PRIVATE)
        userImage = prefs?.getString("user_image", null).toString()
        userName = prefs?.getString("user_name", null).toString()
        userEmail = prefs?.getString("user_email", null).toString()
    }

}