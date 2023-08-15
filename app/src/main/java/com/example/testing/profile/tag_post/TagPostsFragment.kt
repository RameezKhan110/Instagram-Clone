package com.example.testing.profile.tag_post

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.example.testing.R
import com.example.testing.databinding.FragmentTaggedPostsBinding
import com.example.testing.profile.tag_post.adapter.TagPostAdapter
import com.example.testing.profile.tag_post.viewmodel.TagPostViewModel
import com.example.testing.utils.GridItemDecorator


class TagPostsFragment : Fragment() {

    private lateinit var binding: FragmentTaggedPostsBinding
    private val tagPostViewModel: TagPostViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentTaggedPostsBinding.inflate(layoutInflater, container, false)


        val spacing = resources.getDimensionPixelSize(R.dimen.dp2)
        binding.tagPostRecyclerView.addItemDecoration(GridItemDecorator(spacing, 3, false))

        binding.tagPostRecyclerView.layoutManager = GridLayoutManager(requireContext(), 3)

        tagPostViewModel.addTagPost()
        tagPostViewModel.getTagPost()

        tagPostViewModel.getLiveTagPost.observe(viewLifecycleOwner, Observer {
            val adapter = TagPostAdapter(it)
            binding.tagPostRecyclerView.adapter = adapter
        })

        return binding.root
    }
}