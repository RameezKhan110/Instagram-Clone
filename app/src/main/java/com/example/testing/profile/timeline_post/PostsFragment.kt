package com.example.testing.profile.timeline_post

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.testing.R
import com.example.testing.databinding.FragmentPostsBinding
import com.example.testing.profile.timeline_post.adapter.PostAdapter
import com.example.testing.profile.timeline_post.viewmodel.PostViewModel
import com.example.testing.search.post_detail.viewmodel.TimelineToDetailSharedViewModel
import com.example.testing.utils.GridItemDecorator


class PostsFragment : Fragment() {

    private lateinit var binding: FragmentPostsBinding
    private val postViewModel: PostViewModel by activityViewModels()
    private val timelinePostToDetailSharedViewModel: TimelineToDetailSharedViewModel by activityViewModels()
    val timeLinePostAdapter = PostAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPostsBinding.inflate(layoutInflater, container, false)

        val spacing = resources.getDimensionPixelSize(R.dimen.dp2)
        binding.postRecyclerView.addItemDecoration(GridItemDecorator(spacing, 3, false))

        binding.postRecyclerView.layoutManager = GridLayoutManager(requireContext(), 3)

        postViewModel.addTimelinePost()
        postViewModel.getTimelinePost()

        postViewModel.getLiveTimelinePost.observe(viewLifecycleOwner, Observer {
            timeLinePostAdapter.postsList = it
            binding.postRecyclerView.adapter = timeLinePostAdapter
        })

        timeLinePostAdapter.onTimeLinePostClicked = {
            timelinePostToDetailSharedViewModel.getUserPost(it)
            findNavController().navigate(R.id.action_profileFragment_to_postDetailFragment)
        }




        return binding.root
    }

}