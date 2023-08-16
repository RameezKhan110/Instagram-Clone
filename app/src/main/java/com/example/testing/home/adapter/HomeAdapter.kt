package com.example.testing.home.adapter

import android.util.Log
import android.util.SparseIntArray
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.testing.databinding.HomeStoriesChildBinding
import com.example.testing.databinding.SampleItemChildBinding
import com.example.testing.home.Const.POST
import com.example.testing.home.Const.STORY
import com.example.testing.home.ISSTORY
import com.example.testing.home.model.HomeModel
import com.example.testing.home.model.PostModel
import com.example.testing.home.model.StoryModel

class HomeAdapter :
    ListAdapter<HomeModel, RecyclerView.ViewHolder>(DiffUtil()) {

    lateinit var onSavedClicked_HomeAdapter: (PostModel) -> Unit
    val storyList: ArrayList<StoryModel> = arrayListOf()
    val postAdapter = PostAdapter()
    val postList: ArrayList<PostModel> = arrayListOf()
    private val positionList = SparseIntArray()
    override fun getItemViewType(position: Int): Int {
        return if (getItem(position).isStory == ISSTORY.TRUE) STORY else POST
    }

    inner class HomeAdapterPostViewHolder(private val postBinding: SampleItemChildBinding) :
        RecyclerView.ViewHolder(postBinding.root) {
        fun bind(postItem: HomeModel) {
            val postAdapter = PostAdapter()

            postBinding.postRV.layoutManager =
                LinearLayoutManager(postBinding.root.context, LinearLayoutManager.VERTICAL, false)
            postBinding.postRV.adapter = postAdapter
            postAdapter.submitList(postItem.postLists)

            postAdapter.onSaveClicked = {
                onSavedClicked_HomeAdapter(it)
            }
        }
    }

    inner class HomeAdapterStoriesViewHolder(private val storiesBinding: HomeStoriesChildBinding) :
        RecyclerView.ViewHolder(storiesBinding.root) {

        lateinit var layoutManager: LinearLayoutManager
        fun bind(storiesItem: HomeModel) {
            layoutManager = LinearLayoutManager(
                storiesBinding.root.context,
                LinearLayoutManager.HORIZONTAL,
                false
            )
            storiesBinding.childRV.layoutManager = layoutManager
            val storyAdapter = StoryAdapter()
            storiesBinding.childRV.adapter = storyAdapter
            storyAdapter.submitList(storiesItem.storyList)

        }
    }

    override fun onViewRecycled(holder: ViewHolder) {

        if (holder is HomeAdapterStoriesViewHolder) {
            val position = holder.adapterPosition
            val firstVisiblePosition = holder.layoutManager.findFirstVisibleItemPosition()
            positionList.put(position, firstVisiblePosition)
        }
        super.onViewRecycled(holder)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return if (viewType == STORY) {
            val viewHolder =
                HomeStoriesChildBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            HomeAdapterStoriesViewHolder(viewHolder)
        } else {
            val viewHolder =
                SampleItemChildBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            HomeAdapterPostViewHolder(viewHolder)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == STORY) {
            (holder as HomeAdapterStoriesViewHolder).bind(getItem(position))
            val lastSeenFirstPosition = positionList.get(position, 0)
            if (lastSeenFirstPosition >= 0) {
                holder.layoutManager.scrollToPositionWithOffset(lastSeenFirstPosition, 0)
            }
        } else {
            (holder as HomeAdapterPostViewHolder).bind(getItem(position))
        }
    }

    class DiffUtil : androidx.recyclerview.widget.DiffUtil.ItemCallback<HomeModel>() {
        override fun areItemsTheSame(oldItem: HomeModel, newItem: HomeModel): Boolean {
            return oldItem.postId == newItem.postId
        }

        override fun areContentsTheSame(oldItem: HomeModel, newItem: HomeModel): Boolean {
            return oldItem == newItem
        }
    }

}