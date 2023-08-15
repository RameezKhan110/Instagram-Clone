package com.example.testing.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.testing.databinding.HomeStoriesBinding
import com.example.testing.home.model.StoryModel

class StoryAdapter: ListAdapter<StoryModel, RecyclerView.ViewHolder>(DiffUtil()) {

    inner class StoryViewHolder(binding: HomeStoriesBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val viewHolder = HomeStoriesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StoryViewHolder(viewHolder)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val storyItems = getItem(position)

        HomeStoriesBinding.bind(holder.itemView).apply {
            userStory.setImageResource(storyItems.story!!)
        }
    }













    class DiffUtil : androidx.recyclerview.widget.DiffUtil.ItemCallback<StoryModel>() {
        override fun areItemsTheSame(oldItem: StoryModel, newItem: StoryModel): Boolean {
            return oldItem.story == newItem.story
        }

        override fun areContentsTheSame(oldItem: StoryModel, newItem: StoryModel): Boolean {
            return oldItem == newItem
        }
    }



}
