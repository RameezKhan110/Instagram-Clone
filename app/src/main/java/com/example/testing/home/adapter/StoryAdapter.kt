package com.example.testing.home.adapter

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.example.testing.R
import com.example.testing.databinding.HomeStoriesBinding
import com.example.testing.home.firestore.repository.model.FireStoreStory
import com.example.testing.home.model.StoryModel
import com.example.testing.home.room.post.story.Story

class StoryAdapter: ListAdapter<StoryModel, RecyclerView.ViewHolder>(StoryDiffUtil()) {

    inner class StoryViewHolder(binding: HomeStoriesBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val viewHolder = HomeStoriesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StoryViewHolder(viewHolder)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val storyItems = getItem(position)

        HomeStoriesBinding.bind(holder.itemView).apply {
            Glide.with(holder.itemView.context)
                .load(storyItems.story)
                .error(R.drawable.error_image)
                .listener(object : RequestListener<Drawable?> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: com.bumptech.glide.request.target.Target<Drawable?>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        storyLoadingProgressbar.setVisibility(View.GONE)
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: com.bumptech.glide.request.target.Target<Drawable?>?,
                        dataSource: com.bumptech.glide.load.DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        storyLoadingProgressbar.setVisibility(View.GONE)
                        return false
                    }
                })
                .into(userStoryImageView)
        }
    }

    class StoryDiffUtil : androidx.recyclerview.widget.DiffUtil.ItemCallback<StoryModel>() {
        override fun areItemsTheSame(oldItem: StoryModel, newItem: StoryModel): Boolean {
            return oldItem.story == newItem.story
        }

        override fun areContentsTheSame(oldItem: StoryModel, newItem: StoryModel): Boolean {
            return oldItem == newItem
        }
    }



}
