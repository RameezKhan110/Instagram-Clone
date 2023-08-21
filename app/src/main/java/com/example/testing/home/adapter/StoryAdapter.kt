package com.example.testing.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.testing.databinding.HomeStoriesBinding
import com.example.testing.home.room.post.story.Story

class StoryAdapter: ListAdapter<Story, RecyclerView.ViewHolder>(StoryDiffUtil()) {

    inner class StoryViewHolder(binding: HomeStoriesBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val viewHolder = HomeStoriesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StoryViewHolder(viewHolder)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val storyItems = getItem(position)

        HomeStoriesBinding.bind(holder.itemView).apply {
            userStoryImageView.setImageURI(storyItems.story.toUri())
        }
    }

    class StoryDiffUtil : androidx.recyclerview.widget.DiffUtil.ItemCallback<Story>() {
        override fun areItemsTheSame(oldItem: Story, newItem: Story): Boolean {
            return oldItem.story == newItem.story
        }

        override fun areContentsTheSame(oldItem: Story, newItem: Story): Boolean {
            return oldItem == newItem
        }
    }



}
