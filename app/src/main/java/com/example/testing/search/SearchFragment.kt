package com.example.testing.search

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.testing.search.post_detail.viewmodel.PostDetailSharedViewModel
import com.example.testing.utils.GridItemDecorator
import com.example.testing.R
import com.example.testing.databinding.FragmentSearchBinding
import com.example.testing.search.adapter.SearchAdapter
import com.example.testing.search.viewmodel.SearchViewModel

class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding
    private val searchViewModel: SearchViewModel by activityViewModels()
    private val postDetailSharedViewModel: PostDetailSharedViewModel by activityViewModels()
    private val searchAdapter = SearchAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentSearchBinding.inflate(layoutInflater, container, false)

        val spacing = resources.getDimensionPixelSize(R.dimen.dp2)
        binding.searchRecyclerView.addItemDecoration(GridItemDecorator(spacing, 3, false))

        binding.searchRecyclerView.layoutManager = GridLayoutManager(requireContext(), 3)

        searchViewModel.addRandomPosts()
        searchViewModel.getRandomPosts()

        searchViewModel.getLiveRandomPosts.observe(viewLifecycleOwner, Observer {
            searchAdapter.userPost = it
            binding.searchRecyclerView.adapter = searchAdapter
        })

        searchAdapter.onSearchPostClicked = {
//            postDetailSharedViewModel.getUserPost(it)
            Log.d("TAG", "Search Fragment: " + it)
            val searchPostBundle = Bundle()
            searchPostBundle.putInt("Search_User_Image", it.userImage)
            searchPostBundle.putInt("Search_User_Post", it.userPost)
            searchPostBundle.putString("Search_User_Name", it.userName)
            searchPostBundle.putString("Search_User_Location", it.userLocation)
            searchPostBundle.putBoolean("Search_Check_Click", true)

            findNavController().navigate(R.id.action_searchFragment_to_postDetailFragment, searchPostBundle)
        }

        return binding.root
    }

}