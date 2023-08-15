package com.example.testing.profile

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.testing.profile.reels.ReelsFragment
import com.example.testing.profile.tag_post.TagPostsFragment
import com.example.testing.profile.timeline_post.PostsFragment

class PageAdapter(fragmentActivity: FragmentActivity): FragmentStateAdapter(fragmentActivity) {
    private val fragments = arrayListOf(
        PostsFragment(),
        ReelsFragment(),
        TagPostsFragment()
    )

    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }


}