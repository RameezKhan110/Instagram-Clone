package com.example.testing.home

import android.app.Activity
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.net.toFile
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.testing.MainActivity
import com.example.testing.R
import com.example.testing.auth.viewmodel.AuthViewModel
import com.example.testing.databinding.FragmentCreateStoryBinding
import com.example.testing.home.firestore.repository.viewmodel.FireStoreHomeViewModel
import com.example.testing.home.room.post.story.Story
import com.example.testing.home.viewmodel.HomeViewModel
import com.example.testing.utils.NetworkResponse
import com.github.drjacky.imagepicker.ImagePicker

class CreateStoryFragment : Fragment() {

    private lateinit var binding: FragmentCreateStoryBinding
    private var userStory: Uri? = null
    private val homeViewModel: HomeViewModel by activityViewModels()
    private val authViewModel: AuthViewModel by activityViewModels()
    private val fireStoreHomeViewModel: FireStoreHomeViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCreateStoryBinding.inflate(layoutInflater, container, false)

        binding.uploadStoryBtn.setOnClickListener {

            userStory?.let { it1 -> fireStoreHomeViewModel.createStory(it1) }
            fireStoreHomeViewModel.createLiveStory.observe(viewLifecycleOwner) {
                when(it) {
                    is NetworkResponse.Error -> {
                        binding.userStoryImageView.visibility = View.VISIBLE
                        binding.createStoryProgressBar.visibility = View.GONE
                        Toast.makeText(requireContext(), "Error in creating story", Toast.LENGTH_SHORT).show()
                    }
                    is NetworkResponse.Loading -> {
                        binding.userStoryImageView.visibility = View.GONE
                        binding.createStoryProgressBar.visibility = View.VISIBLE
                        Toast.makeText(requireContext(), "Please wait", Toast.LENGTH_SHORT).show()
                    }
                    is NetworkResponse.Success -> {
                        binding.userStoryImageView.visibility = View.VISIBLE
                        binding.createStoryProgressBar.visibility = View.GONE
                        Toast.makeText(requireContext(), "Story created", Toast.LENGTH_SHORT).show()
                        (requireActivity() as MainActivity).binding.bottomNavigationView.selectedItemId = R.id.home
                    }
                }
            }

//            authViewModel.getUserId(getUserEmail()).observe(viewLifecycleOwner) { userId ->
//                Log.d("TAG", "Current userId" + userId)
//                homeViewModel.addStory(Story(0, userId, userStory))
//
//            }
        }

        binding.guideTextStory.setOnClickListener {
            startUserStoryResult.launch(
                ImagePicker.with(requireActivity())
                    .cropSquare()
                    .galleryOnly()
                    .createIntent()
            )
        }

        return binding.root
    }

    private val startUserStoryResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            val resultCode = result.resultCode
            val data = result.data

            if (resultCode == Activity.RESULT_OK) {
                //Image Uri will not be null for RESULT_OK

                data?.data?.let {
                    userStory = it
                    binding.userStoryImageView.visibility = View.VISIBLE
                    binding.guideTextStory.visibility = View.GONE
                    binding.userStoryImageView.setImageURI(it)
                }
            }
        }

    private fun getUserEmail(): String {
        val sharedPreferences =
            requireActivity().getSharedPreferences("user_credentials", Context.MODE_PRIVATE)
        val currentUserEmail = sharedPreferences.getString("username", null)
        return currentUserEmail.toString()
    }

}