package com.example.testing.search.post_detail

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.example.testing.databinding.FragmentDetailBinding
import com.example.testing.search.post_detail.viewmodel.PostDetailSharedViewModel
import com.example.testing.profile.saved_post.viewmodel.DetailToSavedSharedViewModel
import com.example.testing.search.post_detail.viewmodel.TimelineToDetailSharedViewModel

class PostDetailFragment : Fragment() {

    private lateinit var binding: FragmentDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailBinding.inflate(layoutInflater, container, false)

        val homePostBundle = arguments
        val searchPostBundle = arguments

        if(homePostBundle?.getBoolean("Check_Click") == true) {
            val homeUserImage = homePostBundle.getString("User_Image")
            val homeUserPost = homePostBundle.getString("User_Post")
            Glide.with(requireActivity()).load(homeUserImage).into(binding.detailUserImage)
            Glide.with(requireActivity()).load(homeUserPost).into(binding.detailUserPost)
            binding.detailUserName.text = "Rameez Khan"
            binding.detailUserLocation.text = "Hyderabad"

        } else if(searchPostBundle?.getBoolean("Search_Check_Click" ) == true) {
            val searchUserImage = searchPostBundle.getInt("Search_User_Image")
            val searchUserPost = searchPostBundle.getInt("Search_User_Post")
            val searchUserName = searchPostBundle.getString("Search_User_Name")
            val searchUserLocation = searchPostBundle.getString("Search_User_Location")

            binding.detailUserImage.setImageResource(searchUserImage)
            binding.detailUserPost.setImageResource(searchUserPost)
            binding.detailUserName.text = searchUserName
            binding.detailUserLocation.text = searchUserLocation
        }
        return binding.root
    }

}