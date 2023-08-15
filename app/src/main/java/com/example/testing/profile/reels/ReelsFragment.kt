package com.example.testing.profile.reels

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.example.testing.R
import com.example.testing.databinding.FragmentReelsBinding
import com.example.testing.profile.reels.adapter.ReelsAdapter
import com.example.testing.profile.reels.viewmodel.ReelsViewModel
import com.example.testing.utils.GridItemDecorator

class ReelsFragment : Fragment() {

    private lateinit var binding: FragmentReelsBinding
    private val reelsViewModel: ReelsViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentReelsBinding.inflate(layoutInflater, container, false)

        val spacing = resources.getDimensionPixelSize(R.dimen.dp2)
        binding.reelsRecyclerView.addItemDecoration(GridItemDecorator(spacing, 3, false))
        binding.reelsRecyclerView.layoutManager = GridLayoutManager(requireContext(), 3)

        reelsViewModel.addReels()
        reelsViewModel.getReels()

        reelsViewModel.getLiveReels.observe(viewLifecycleOwner, Observer {
            val reelsAdapter = ReelsAdapter(it)
            binding.reelsRecyclerView.adapter = reelsAdapter
        })

        return binding.root
    }

}