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
import com.example.testing.home.viewmodel.HomeViewModel
import com.example.testing.profile.saved_post.viewmodel.SavedSharedViewModel

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val homeViewModel: HomeViewModel by activityViewModels()
    private val sharedViewModel: SavedSharedViewModel by activityViewModels()
    private val homeAdapter = HomeAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)

        binding.parentRecyclerViewHome.layoutManager = LinearLayoutManager(requireContext())
        binding.parentRecyclerViewHome.adapter = homeAdapter

        homeViewModel.addPost()
        homeViewModel.getPost()

        homeAdapter.onSavedClicked_HomeAdapter = { postItem ->
            Toast.makeText(requireContext(), "Saved", Toast.LENGTH_SHORT).show()
            sharedViewModel.getUserWholePost(postItem)
        }
        homeViewModel.getLiveData.observe(viewLifecycleOwner) {
            Log.d("TAG", "HomeFragmentList: $it")
            homeAdapter.submitList(it)
        }

        return binding.root
    }


}