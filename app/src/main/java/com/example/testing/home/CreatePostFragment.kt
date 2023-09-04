package com.example.testing.home

import android.Manifest
import android.app.Activity
import android.content.ContentResolver
import android.content.pm.PackageManager
import android.content.res.ColorStateList
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getColor
import androidx.core.net.toFile
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.testing.MainActivity
import com.example.testing.R
import com.example.testing.databinding.FragmentCreatePostBinding
import com.example.testing.home.firestore.repository.viewmodel.FireStoreHomeViewModel
import com.example.testing.home.viewmodel.HomeViewModel
import com.example.testing.utils.NetworkResponse
import com.github.drjacky.imagepicker.ImagePicker
import com.google.android.material.textfield.TextInputLayout


class CreatePostFragment : Fragment() {

    private lateinit var binding: FragmentCreatePostBinding
    private val homeViewModel: HomeViewModel by activityViewModels()
    private var userPost: Uri? = null
    private lateinit var contentResolver: ContentResolver
    private val fireStoreHomeViewModel: FireStoreHomeViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCreatePostBinding.inflate(layoutInflater, container, false)

//        binding.userPhoto.setOnClickListener {
////                selectImageFromGallery_UserImage.launch("image/*")
//                startUserImageResult.launch(
//                    ImagePicker.with(requireActivity())
//                        .cropSquare()         //Final image size will be less than 1 MB(Optional)
//                        .galleryOnly()
//                        .createIntent()
//                )
//        }

        binding.guideTextPost.setOnClickListener {
            startUserPostResult.launch(
                ImagePicker.with(requireActivity())
                    .cropSquare()
                    .galleryOnly()
                    .createIntent()
            )
        }

        binding.uploadPostBtn.setOnClickListener {

            userPost?.let { it1 -> fireStoreHomeViewModel.createPost(it1) }
            fireStoreHomeViewModel.createLivePost.observe(viewLifecycleOwner) {
                when(it) {
                    is NetworkResponse.Error -> {
                        binding.createPostProgressBar.visibility = View.GONE
                        binding.userPostImageView.visibility = View.VISIBLE
                        Toast.makeText(requireContext(), "Error in creating post", Toast.LENGTH_SHORT).show()
                    }
                    is NetworkResponse.Loading -> {
                        Toast.makeText(requireContext(), "Please wait", Toast.LENGTH_SHORT).show()
                        binding.createPostProgressBar.visibility = View.VISIBLE
                        binding.userPostImageView.visibility = View.GONE
                    }
                    is NetworkResponse.Success -> {
                        binding.createPostProgressBar.visibility = View.GONE
                        binding.userPostImageView.visibility = View.VISIBLE
                        Toast.makeText(requireContext(), "Post created", Toast.LENGTH_SHORT).show()
                        (requireActivity() as MainActivity).binding.bottomNavigationView.selectedItemId = R.id.home
                    }
                }
            }

        }

//        binding.uploadPostBtn.setOnClickListener {
//            val userName = binding.userName.text.toString()
//            val userLocation = binding.userLocation.text.toString()
//
//            if (userName.isEmpty()) {
//                binding.textInputUsername.setCustomColor("Username can't empty")
//
//            } else if (userLocation.isEmpty()) {
//                binding.textInputLocation.setCustomColor("Location can't be empty")
//
//            } else {
//                homeViewModel.addPost(Home(0, userImage, userLocation, userName, userPost, false, false))
//                Toast.makeText(requireContext(), "Posted", Toast.LENGTH_SHORT).show()
//                findNavController().navigate(R.id.action_createPostFragment_to_homeFragment)
//            }
//
//        }
        return binding.root
    }

//    private val startUserImageResult =
//        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
//            val resultCode = result.resultCode
//            val data = result.data
//
//            if (resultCode == Activity.RESULT_OK) {
//                //Image Uri will not be null for RESULT_OK
//
//                data?.data?.let {
////                    contentResolver.takePersistableUriPermission(it, Intent.FLAG_GRANT_READ_URI_PERMISSION)
//                    userImage = it.toFile().absolutePath.toString()
//                    binding.userPhoto.setImageURI(it)
//                }
//            }
//        }

    private val startUserPostResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            val resultCode = result.resultCode
            val data = result.data

            if (resultCode == Activity.RESULT_OK) {

                data?.data?.let {
                    Log.d("TAG", "uri of post")
                    userPost = it
                    binding.userPostImageView.visibility = View.VISIBLE
                    binding.guideTextPost.visibility = View.GONE
                    binding.userPostImageView.setImageURI(it)
                }
            }
        }


//    private val selectImageFromGallery_UserImage =
//        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
//            uri?.let { imageUri ->
////                contentResolver.takePersistableUriPermission(
////                    imageUri,
////                    Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION
////                )
//                userImage = imageUri.toString()
//                binding.userPhoto.setImageURI(imageUri)
//
////                userImage = imageUri.toFile().path
//            }
//        }

//    private val selectImageFromGallery_UserPost =
//        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
//            uri?.let { imageUri ->
////                contentResolver.takePersistableUriPermission(
////                    imageUri,
////                    Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION
////                )
//                binding.userUploadedPost.setImageURI(imageUri)
//                userPost = imageUri.toString()
//                binding.userUploadedPost.visibility = View.VISIBLE
//
////                userPost = imageUri.toFile().path
//            }
//        }

    fun TextInputLayout.setCustomColor(errorMessage: String?) {
        helperText = errorMessage
        editText?.requestFocus()
        setHelperTextColor(ColorStateList.valueOf(getColor(requireContext(), R.color.red)))
        boxStrokeColor = ContextCompat.getColor(context, R.color.red)
//        boxStrokeErrorColor = ColorStateList.valueOf(ContextCompat.getColor(context, R.color.red))
    }


//    fun TextInputLayout.resetToDefaultColor() {
//        val defaultColor = ContextCompat.getColor(context, R.color.blue)
//        setHelperTextColor(ColorStateList.valueOf(defaultColor))
//        boxStrokeColor = defaultColor
//        boxStrokeErrorColor = ColorStateList.valueOf(defaultColor)
//    }
}