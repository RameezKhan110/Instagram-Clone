package com.example.testing.home

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testing.MainActivity
import com.example.testing.R
import com.example.testing.databinding.FragmentHomeBinding
import com.example.testing.home.adapter.HomeAdapter
import com.example.testing.home.room.post.HomeCommonModel
import com.example.testing.home.room.post.story.Story
import com.example.testing.home.viewmodel.HomeViewModel
import com.example.testing.profile.saved_post.viewmodel.SavedSharedViewModel
import com.example.testing.search.post_detail.PostDetailFragment
import com.example.testing.utils.Resource
import com.google.android.gms.auth.api.signin.GoogleSignInOptions

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val homeViewModel: HomeViewModel by activityViewModels()
    private val sharedViewModel: SavedSharedViewModel by activityViewModels()
    private val homeAdapter = HomeAdapter()
    private val commonModelList = mutableListOf<HomeCommonModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        handleBackPress()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)

        binding.parentRecyclerViewHome.layoutManager = LinearLayoutManager(requireContext())
        binding.parentRecyclerViewHome.adapter = homeAdapter

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
                        commonModelList.add(HomeCommonModel.PostModelItem(homePostList))

                        binding.progressBar.visibility = View.GONE
                        binding.parentRecyclerViewHome.visibility = View.VISIBLE
                        homeAdapter.submitList(commonModelList)

                    }
                }


            }
        }

        homeAdapter.onSavedClickedHomeAdapter = { postItem ->
            Toast.makeText(requireContext(), "Saved", Toast.LENGTH_SHORT).show()
            sharedViewModel.getUserPost(postItem)
        }

        homeAdapter.onPostClickedHomeAdapter = { postItem ->
            Log.d("TAG", "HomeFragment PostItem: " + postItem)
            val postBundle = Bundle()
            postBundle.putString("User_Image", postItem.urls.raw)
            postBundle.putString("User_Post", postItem.urls.full)
            postBundle.putBoolean("Check_Click", true)
            findNavController().navigate(R.id.action_homeFragment_to_postDetailFragment, postBundle)
        }
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        binding == null
    }

    private fun handleBackPress(){
        requireActivity().onBackPressedDispatcher.addCallback(
            this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    requireActivity().finishAffinity()
                }
            }
        )
    }
}