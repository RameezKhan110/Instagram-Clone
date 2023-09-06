package com.example.testing.home

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.map
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testing.R
import com.example.testing.auth.viewmodel.AuthViewModel
import com.example.testing.databinding.FragmentHomeBinding
import com.example.testing.home.adapter.HomeAdapter
import com.example.testing.home.firestore.repository.FireStoreHomeRepository
import com.example.testing.home.firestore.repository.viewmodel.FireStoreHomeViewModel
import com.example.testing.home.model.PostModel
import com.example.testing.home.model.StoryModel
import com.example.testing.home.room.post.HomeCommonModel
import com.example.testing.home.room.post.story.Story
import com.example.testing.home.viewmodel.HomeViewModel
import com.example.testing.profile.saved_post.viewmodel.SavedSharedViewModel
import com.example.testing.utils.NetworkResponse
import com.example.testing.utils.Resource
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.firebase.firestore.auth.User

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val homeViewModel: HomeViewModel by activityViewModels()
    private val sharedViewModel: SavedSharedViewModel by activityViewModels()
    private val homeAdapter = HomeAdapter()
    private val commonModelList = mutableListOf<HomeCommonModel>()
    private val postList = mutableListOf<PostModel>()
    private val authViewModel: AuthViewModel by activityViewModels()
    private val fireStoreHomeView: FireStoreHomeViewModel by activityViewModels()
    var userImage: String = ""
    var userName: String = ""
    var userEmail: String = ""
    var userPassword: String = ""
    var userId: String = ""
    var userPost: String = ""
    var userData: com.example.testing.auth.model.User? = null
    var storiesList = mutableListOf<StoryModel>()
    @SuppressLint("NotifyDataSetChanged")
    override fun onResume() {
        super.onResume()
        Log.d("TAG", "calling on resume")
        activity?.actionBar?.title = "Home"

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("TAG", "calling from onCreate")
        handleBackPress()
    }

    override fun onStart() {
        super.onStart()
        Log.d("TAG", "calling from onStart")
    }

    override fun onPause() {
        super.onPause()
        commonModelList.clear()
        Log.d("TAG", "calling from onPause $commonModelList")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        Log.d("TAG", "onCreateView: ")
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        val account = GoogleSignIn.getLastSignedInAccount(requireContext())

        binding.parentRecyclerViewHome.layoutManager = LinearLayoutManager(requireContext())
        binding.parentRecyclerViewHome.adapter = homeAdapter


        getUserDetailsHome()
        fireStoreHomeView.returnWorkRequest().let { workReq ->
            if (workReq != null) {
                fireStoreHomeView.observerWorkReq(workReq).observe(viewLifecycleOwner) {
                    if(it != null) {
                        Log.d("TAG", "worker from home calling")
                        fireStoreHomeView.getPosts()
                    }
                }
            }
        }
        fireStoreHomeView.getPosts()
        fireStoreHomeView.getStory()

        fireStoreHomeView.getLivePost.observe(viewLifecycleOwner) {userPost ->
            fireStoreHomeView.getLiveStory.observe(viewLifecycleOwner) {userStory ->

                commonModelList.clear()
                when(userPost) {
                    is NetworkResponse.Error -> {
                        binding.shimmerEffectHome.visibility = View.GONE
                        binding.parentRecyclerViewHome.visibility = View.VISIBLE
                        Toast.makeText(requireContext(), "Error in fetching post", Toast.LENGTH_SHORT).show()
                    }
                    is NetworkResponse.Loading -> {
                        binding.shimmerEffectHome.startShimmerAnimation()
                        binding.shimmerEffectHome.visibility = View.VISIBLE
                        binding.parentRecyclerViewHome.visibility = View.GONE
                    }
                    is NetworkResponse.Success -> {
                        binding.shimmerEffectHome.visibility = View.GONE
                        binding.parentRecyclerViewHome.visibility = View.VISIBLE
                        postList.clear()
                        userPost.data?.forEach {
                            postList.addAll(listOf(PostModel(it.userPost, userImage, userName, userEmail)))

                        }
                        storiesList.clear()
                        userStory.map {
                            Log.d("TAG", "calling from home stories: " + it.story)
                            storiesList.add(StoryModel(it.story))
                        }
                        Log.d("TAG", "calling from onCreateview " + storiesList)
                        commonModelList.add(HomeCommonModel.StoryModelItem(storiesList))
                        commonModelList.add(HomeCommonModel.PostModelItem(postList))
                        homeAdapter.submitList(commonModelList)
                        homeAdapter.notifyDataSetChanged()

                    }
                }
            }

        }


//        homeViewModel.livePostLists.observe(viewLifecycleOwner) { homePostList ->
//            authViewModel.getDataAsPerEmail(getUserEmailHome()).observe(viewLifecycleOwner) { userData ->
//                    authViewModel.getUserId(getUserEmailHome()).observe(viewLifecycleOwner) {
//                        authViewModel.getStoryAsPerId(it).observe(viewLifecycleOwner) { userStory ->
//
//                            userData.forEach {
//                                userImage = it.userImage
//                                userEmail = it.userEmail
//                                userName = it.userName
//                                userId = it.userId
//                            }
//
//                            val transformed = homePostList.data?.map {
//                                PostModel(it.urls.full, userImage, userName, userEmail)
//                            }
//
//                            when (homePostList) {
//                                is Resource.Error -> {
//                                    binding.progressBar.visibility = View.GONE
//                                    binding.parentRecyclerViewHome.visibility = View.VISIBLE
//                                }
//
//                                is Resource.Loading -> {
//                                    Log.d("TAG", "Loading State")
//                                    binding.progressBar.visibility = View.VISIBLE
//                                    binding.parentRecyclerViewHome.visibility = View.GONE
//                                }
//
//                                is Resource.Success -> {
//                                    if (userStory.any {
//                                            it is Story
//                                        }) {
//                                        val list: ArrayList<Story> = arrayListOf()
//                                        userStory.map {
//                                            list.add(it)
//                                        }
//
//                                        commonModelList.add(HomeCommonModel.StoryModelItem(list))
//                                    }
//                                    commonModelList.add(
//                                        HomeCommonModel.PostModelItem(
//                                            transformed ?: arrayListOf()
//                                        )
//                                    )
//
//                                    binding.progressBar.visibility = View.GONE
//                                    binding.parentRecyclerViewHome.visibility = View.VISIBLE
//                                    homeAdapter.submitList(commonModelList)
//
//                                }
//                            }
//                        }
//                    }
//
//
//                }
//        }

        homeAdapter.onSavedClickedHomeAdapter = { postItem ->
            Toast.makeText(requireContext(), "Saved", Toast.LENGTH_SHORT).show()
            sharedViewModel.getUserPost(postItem)
        }

        homeAdapter.onPostClickedHomeAdapter = { postItem ->

////            customDialog.setImageResource(postItem.userPost)
//            customDialog.show()
            val postBundle = Bundle()
            postBundle.putString("User_Image", postItem.userPost)
//            postBundle.putString("User_Post", postItem.urls.full)
//            postBundle.putBoolean("Check_Click", true)
            findNavController().navigate(R.id.action_homeFragment_to_postDetailFragment, postBundle)
        }
        return binding.root
    }

    private fun getUserDetailsHome() {
        val prefs = context?.getSharedPreferences("user_info", Context.MODE_PRIVATE)
        userImage = prefs?.getString("user_image", null).toString()
        userName = prefs?.getString("user_name", null).toString()
        userEmail = prefs?.getString("user_email", null).toString()
        userPassword = prefs?.getString("user_password", null).toString()
        userId = prefs?.getString("user_id", null).toString()
        userData = com.example.testing.auth.model.User(userImage, userName, userEmail, userPassword, userId)
    }

    override fun onDestroy() {
        super.onDestroy()
        binding == null
    }

    private fun handleBackPress() {
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