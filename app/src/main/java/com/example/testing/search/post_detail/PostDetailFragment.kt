package com.example.testing.search.post_detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.testing.databinding.FragmentDetailBinding
import com.example.testing.search.post_detail.viewmodel.PostDetailSharedViewModel
import com.example.testing.profile.saved_post.viewmodel.DetailToSavedSharedViewModel
import com.example.testing.search.post_detail.viewmodel.TimelineToDetailSharedViewModel

class PostDetailFragment : Fragment() {

    private lateinit var binding: FragmentDetailBinding
    private val postDetailSharedViewModel: PostDetailSharedViewModel by activityViewModels()
    private val timelineToDetailSharedViewModel: TimelineToDetailSharedViewModel by activityViewModels()
    private val detailToSavedSharedViewModel: DetailToSavedSharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailBinding.inflate(layoutInflater, container, false)

        val searchitemList = postDetailSharedViewModel.userPostItem
        val searchitem = searchitemList.get(0)

        val timeLinePostList = timelineToDetailSharedViewModel.userPostItem
        val timeLineItem = timeLinePostList.get(0)


        binding.detailUserImage.setImageResource(searchitem.userImage)
        binding.detailUserName.text = searchitem.userName
        binding.detailUserLocation.text = searchitem.userLocation
        binding.detailUserPost.setImageResource(searchitem.userPost)

        return binding.root
    }

}