package com.example.testing.profile.saved_post

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testing.databinding.FragmentSavedPostBinding
import com.example.testing.profile.saved_post.adapter.MySavedPostAdapter
import com.example.testing.profile.saved_post.adapter.SearchToSavedPostAdapter
import com.example.testing.profile.saved_post.viewmodel.DetailToSavedSharedViewModel
import com.example.testing.profile.saved_post.viewmodel.SavedSharedViewModel

class SavedPostFragment : Fragment() {

    private lateinit var binding: FragmentSavedPostBinding
    private val sharedViewModel: SavedSharedViewModel by activityViewModels()
    private val detailToSavedSharedViewModel: DetailToSavedSharedViewModel by activityViewModels()
    val savedPostAdapter = MySavedPostAdapter()
    val searchToSavedPostAdapter = SearchToSavedPostAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSavedPostBinding.inflate(layoutInflater, container, false)

        binding.savedPostRecyclerView.layoutManager = LinearLayoutManager(requireContext())


        val homePostItemList = sharedViewModel.itemList
        savedPostAdapter.submitList(homePostItemList)
        binding.savedPostRecyclerView.adapter = savedPostAdapter

//        val searchPostItemList = detailToSavedSharedViewModel.itemList
//        searchToSavedPostAdapter.submitList(searchPostItemList)
//        binding.savedPostRecyclerView.adapter = searchToSavedPostAdapter

        savedPostAdapter.onUnSaveClicked = { postItem ->
            Toast.makeText(requireContext(), "Unsaved", Toast.LENGTH_SHORT).show()
            val deletePos = homePostItemList.indexOfFirst {
                it.id == postItem.postId
            }
            homePostItemList.removeAt(deletePos)
            savedPostAdapter.notifyItemRemoved(deletePos)

        }
        return binding.root
    }
}