package com.example.testing.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testing.databinding.FragmentHomeBinding
import com.example.testing.home.adapter.HomeAdapter
import com.example.testing.home.room.post.HomeCommonModel
import com.example.testing.home.room.post.story.Story
import com.example.testing.home.viewmodel.HomeViewModel
import com.example.testing.profile.saved_post.viewmodel.SavedSharedViewModel
import com.example.testing.utils.Resource

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val homeViewModel: HomeViewModel by activityViewModels()
    private val sharedViewModel: SavedSharedViewModel by activityViewModels()
    private val homeAdapter = HomeAdapter()
    val commonModelList = mutableListOf<HomeCommonModel>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)

        binding.parentRecyclerViewHome.layoutManager = LinearLayoutManager(requireContext())
        binding.parentRecyclerViewHome.adapter = homeAdapter


        Log.d("TAG", "onCreateView: ")
//        homeViewModel.getApiPosts()


        homeViewModel.livePostLists.observe(viewLifecycleOwner) { homePostList ->
            homeViewModel.getStory().observe(viewLifecycleOwner) { homeStoryList ->

                when (homePostList) {
                    is Resource.Error -> {
                        binding.progressBar.visibility = View.GONE
                        binding.parentRecyclerViewHome.visibility = View.VISIBLE
                    }

                    is Resource.Loading -> {
                        Log.d("TAG", "Loading State")
                        binding.progressBar.visibility = View.VISIBLE
                        binding.parentRecyclerViewHome.visibility = View.GONE
                    }

                    is Resource.Success -> {
                        if (homeStoryList.any {
                                it is Story
                            }) {
                            val list: ArrayList<Story> = arrayListOf()
                            homeStoryList.map {
                                list.add(it)
                            }

                            commonModelList.add(HomeCommonModel.StoryModelItem(homeStoryList))
                        }
                        Log.d("TAG", "Home Posts: $homePostList")
                        commonModelList.add(HomeCommonModel.PostModelItem(homePostList))

                        binding.progressBar.visibility = View.GONE
                        binding.parentRecyclerViewHome.visibility = View.VISIBLE
                        Log.d("TAG", "postItem: " + homePostList.data)

                        homeAdapter.submitList(commonModelList)

                    }
                }


            }
        }


        homeAdapter.onSavedClickedHomeAdapter = { postItem ->
            Toast.makeText(requireContext(), "Saved", Toast.LENGTH_SHORT).show()
            sharedViewModel.getUserPost(postItem)
        }

        return binding.root
    }


    override fun onDestroy() {
        super.onDestroy()
        binding == null
    }
}