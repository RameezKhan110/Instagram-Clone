package com.example.testing.home.adapter

import android.util.Log
import android.util.SparseIntArray
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.testing.R
import com.example.testing.databinding.HomeStoriesChildBinding
import com.example.testing.databinding.SampleItemChildBinding
import com.example.testing.home.firestore.repository.model.FireStoreStory
import com.example.testing.home.model.PostModel
import com.example.testing.home.model.StoryModel
import com.example.testing.home.room.post.Home
import com.example.testing.home.room.post.HomeCommonModel
import com.example.testing.home.room.post.story.Story
import com.example.testing.home.model.Wallpapers

class HomeAdapter :
    ListAdapter<HomeCommonModel, RecyclerView.ViewHolder>(HomeDiffUtil()) {

    var onSavedClickedHomeAdapter: ((Home) -> Unit)? = null
    private val positionList = SparseIntArray()
    var onPostClickedHomeAdapter: ((PostModel) -> Unit)? = null


    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is HomeCommonModel.StoryModelItem -> R.layout.home_stories
            is HomeCommonModel.PostModelItem -> R.layout.sample_item
        }
    }

    inner class HomeAdapterStoriesViewHolder(private val storiesBinding: HomeStoriesChildBinding) :
        RecyclerView.ViewHolder(storiesBinding.root) {

        lateinit var layoutManager: LinearLayoutManager
        fun bind(storiesItem: List<StoryModel>) {
            layoutManager = LinearLayoutManager(
                storiesBinding.root.context,
                LinearLayoutManager.HORIZONTAL,
                false
            )
            storiesBinding.childRV.layoutManager = layoutManager
            val storyAdapter = StoryAdapter()
            storiesBinding.childRV.adapter = storyAdapter
            storyAdapter.submitList(storiesItem)

        }
    }
    inner class HomeAdapterPostViewHolder(private val postBinding: SampleItemChildBinding) :
        RecyclerView.ViewHolder(postBinding.root) {
        fun bind(postItem: List<PostModel>) {

            val postAdapter = PostAdapter()
            postBinding.postRV.layoutManager =
                LinearLayoutManager(postBinding.root.context, LinearLayoutManager.VERTICAL, false)
            postBinding.postRV.adapter = postAdapter

            postAdapter.submitList(postItem)
            postAdapter.onSaveClicked = {
                onSavedClickedHomeAdapter?.invoke(it)
            }

            postAdapter.onPostClicked = {
                onPostClickedHomeAdapter?.invoke(it)
            }


        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {

            R.layout.home_stories -> HomeAdapterStoriesViewHolder(
                HomeStoriesChildBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )

            R.layout.sample_item -> HomeAdapterPostViewHolder(
                SampleItemChildBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
            else -> {
                throw IllegalArgumentException("Unknown view type")
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = getItem(position)) {
            is HomeCommonModel.StoryModelItem -> {
                (holder as HomeAdapterStoriesViewHolder).bind(item.storyItem)


            }
            is HomeCommonModel.PostModelItem -> (holder as HomeAdapterPostViewHolder).bind(item.postItem)
        }
    }

    override fun onViewRecycled(holder: RecyclerView.ViewHolder) {

        if (holder is HomeAdapterStoriesViewHolder) {
            val position = holder.adapterPosition
            val firstVisiblePosition = holder.layoutManager.findFirstVisibleItemPosition()
            positionList.put(position, firstVisiblePosition)
        }
        super.onViewRecycled(holder)
    }
}

class HomeDiffUtil : androidx.recyclerview.widget.DiffUtil.ItemCallback<HomeCommonModel>() {
    override fun areItemsTheSame(oldItem: HomeCommonModel, newItem: HomeCommonModel): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: HomeCommonModel, newItem: HomeCommonModel): Boolean {
        return oldItem == newItem
    }
}