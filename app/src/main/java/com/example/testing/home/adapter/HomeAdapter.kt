package com.example.testing.home.adapter

import android.content.Context
import android.os.Parcelable
import android.util.SparseIntArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.testing.R
import com.example.testing.databinding.HomeStoriesChildBinding
import com.example.testing.databinding.SampleItemBinding
import com.example.testing.home.Const.POST
import com.example.testing.home.Const.STORY
import com.example.testing.home.ISSTORY
import com.example.testing.home.model.HomeModel
import com.example.testing.home.model.StoryModel
import com.example.testing.utils.DoubleClickListener

class HomeAdapter :
    ListAdapter<HomeModel, RecyclerView.ViewHolder>(DiffUtil()) {

    lateinit var onSaveClicked: (HomeModel) -> Unit
    private lateinit var context: Context
    val storyList: ArrayList<StoryModel> = arrayListOf()
    private val positionList = SparseIntArray()
    private val layoutManagerStates = hashMapOf<Int, Parcelable?>()

    override fun getItemViewType(position: Int): Int {
        return if(getItem(position).isStory == ISSTORY.TRUE) STORY else POST
        }

    inner class HomeAdapterPostViewHolder(private val postBinding: SampleItemBinding) :
        RecyclerView.ViewHolder(postBinding.root) {
            fun bind(postItem: HomeModel) {
                    postBinding.userImage.setImageResource(postItem.userImage)
                    postBinding.userLocation.text = postItem.userLocation
                    postBinding.userPost.setImageResource(postItem.userPost)
                    postBinding.userName.text = postItem.userName

                    if (postItem.isLike) {
                        postBinding.like.setImageResource(R.drawable.color_heart)
                    } else {
                        postBinding.like.setImageResource(R.drawable.heart)
                    }

                    if (postItem.isSave) {
                        postBinding.save.setImageResource(R.drawable.bookmark)
                    } else {
                        postBinding.save.setImageResource(R.drawable.saveinstagram)
                    }

                   postBinding. like.setOnClickListener {
                        if (postItem.isLike) {
                            postBinding.like.setImageResource(R.drawable.heart)
                            postItem.isLike = false
                        } else {
                            postBinding.like.setImageResource(R.drawable.color_heart)
                            postItem.isLike = true
                        }

                    }

                    postBinding.userPost.setOnClickListener(object : DoubleClickListener() {
                        override fun onDoubleClick(v: View?) {
                            postBinding.like.setImageResource(R.drawable.color_heart)
                            postItem.isLike = true
                        }

                    })

                    postBinding.save.setOnClickListener {
                        if (!postItem.isSave) {
                            postItem.isSave = true
                            postBinding.save.setImageResource(R.drawable.bookmark)
                            onSaveClicked(postItem)
                        }

                    }

            }
    }

    inner class HomeAdapterStoriesViewHolder(private val storiesBinding: HomeStoriesChildBinding) :
        RecyclerView.ViewHolder(storiesBinding.root) {

        lateinit var layoutManager: LinearLayoutManager
            fun bind(storiesItem: HomeModel) {
                layoutManager = LinearLayoutManager(storiesBinding.root.context, LinearLayoutManager.HORIZONTAL, false)
                storiesBinding.childRV.layoutManager = layoutManager
                val storyAdapter = StoryAdapter()
                storiesBinding.childRV.adapter = storyAdapter
                if(storyList.isEmpty()) {
                    storyList.add(StoryModel(storiesItem.userStory))
                    storyList.add(StoryModel(storiesItem.userStory))
                    storyList.add(StoryModel(storiesItem.userStory))
                    storyList.add(StoryModel(storiesItem.userStory))
                    storyList.add(StoryModel(storiesItem.userStory))
                    storyList.add(StoryModel(storiesItem.userStory))
                    storyList.add(StoryModel(storiesItem.userStory))
                    storyList.add(StoryModel(storiesItem.userStory))
                    storyList.add(StoryModel(storiesItem.userStory))
                    storyList.add(StoryModel(storiesItem.userStory))
                    storyList.add(StoryModel(storiesItem.userStory))
                    storyList.add(StoryModel(storiesItem.userStory))
                    storyList.add(StoryModel(storiesItem.userStory))
                }
                storyAdapter.submitList(storyList)

            }
    }

    override fun onViewRecycled(holder: ViewHolder) {

        if(holder is HomeAdapterStoriesViewHolder){
            val position = holder.adapterPosition
            val firstVisiblePosition = holder.layoutManager.findFirstVisibleItemPosition()
            positionList.put(position, firstVisiblePosition)
        }
        super.onViewRecycled(holder)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return if (viewType == STORY) {
            val viewHolder = HomeStoriesChildBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            HomeAdapterStoriesViewHolder(viewHolder)
        } else {
            val viewHolder = SampleItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            HomeAdapterPostViewHolder(viewHolder)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(getItemViewType(position) == STORY) {
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