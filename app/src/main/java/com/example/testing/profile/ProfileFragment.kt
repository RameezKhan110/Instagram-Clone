package com.example.testing.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.navigation.fragment.findNavController
import com.example.testing.R
import com.example.testing.databinding.FragmentProfileBinding
import com.google.android.material.tabs.TabLayoutMediator

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(layoutInflater, container, false)

        binding.apply {
            viewPager.adapter = PageAdapter(requireActivity())
            TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                when (position) {
                    0 -> {
                        tab.view.tab?.setIcon(R.drawable.baseline_home_24)
                    }
                    1 -> {
                        tab.view.tab?.setIcon(R.drawable.reels)
                    }
                    2 -> {
                        tab.view.tab?.setIcon(R.drawable.tag)
                    }
                }
            }.attach()
        }

        binding.savedPostBtn.setOnClickListener {

            findNavController().navigate(R.id.action_profileFragment_to_savedPostFragment)
        }


        return binding.root
    }

}