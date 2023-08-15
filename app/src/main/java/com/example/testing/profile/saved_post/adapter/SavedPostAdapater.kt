package com.example.testing.profile.saved_post.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.testing.R
import com.example.testing.databinding.SampleItemBinding
import com.example.testing.home.model.HomeModel
import com.example.testing.utils.DoubleClickListener

class MySavedPostAdapter :
    ListAdapter<HomeModel, MySavedPostAdapter.MySavedPostViewHolder>(DiffUtil()) {

    lateinit var onUnSaveClicked: (HomeModel) -> Unit

    inner class MySavedPostViewHolder(binding: SampleItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MySavedPostViewHolder {
        val viewHolder =
            SampleItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MySavedPostViewHolder(viewHolder)
    }

    override fun onBindViewHolder(holder: MySavedPostViewHolder, position: Int) {

        val savedPostItems = getItem(position)

        SampleItemBinding.bind(holder.itemView).apply {

            userName.text = savedPostItems.userName
            userLocation.text = savedPostItems.userLocation
            userImage.setImageResource(savedPostItems.userImage)
            userPost.setImageResource(savedPostItems.userPost)


            if (savedPostItems.isLike) {
                like.setImageResource(R.drawable.color_heart)
            } else {
                like.setImageResource(R.drawable.heart)
            }

            if (savedPostItems.isSave) {
                save.setImageResource(R.drawable.bookmark)
            }

            save.setOnClickListener {
                savedPostItems.isSave = false
                onUnSaveClicked(savedPostItems)
            }

            like.setOnClickListener {
                savedPostItems.isLike = false
                like.setImageResource(R.drawable.heart)
            }

            userPost.setOnClickListener(object : DoubleClickListener() {
                override fun onDoubleClick(v: View?) {
                    like.setImageResource(R.drawable.color_heart)
                    savedPostItems.isLike = true
                }

            })
        }

    }

    class DiffUtil: androidx.recyclerview.widget.DiffUtil.ItemCallback<HomeModel>() {
        override fun areItemsTheSame(oldItem: HomeModel, newItem: HomeModel): Boolean {
            return oldItem.postId == newItem.postId
        }

        override fun areContentsTheSame(oldItem: HomeModel, newItem: HomeModel): Boolean {
            return oldItem.equals(newItem)
        }
    }
}