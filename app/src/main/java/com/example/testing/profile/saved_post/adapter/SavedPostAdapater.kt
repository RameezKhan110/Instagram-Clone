package com.example.testing.profile.saved_post.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.testing.R
import com.example.testing.databinding.SampleItemBinding
import com.example.testing.home.model.PostModel
import com.example.testing.home.room.post.Home
import com.example.testing.utils.DoubleClickListener

class MySavedPostAdapter : ListAdapter<Home, MySavedPostAdapter.MySavedPostViewHolder>(DiffUtil()){

    lateinit var onUnSaveClicked: (PostModel) -> Unit

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
            userImage.setImageURI(savedPostItems.userImage.toUri())
            userPost.setImageURI(savedPostItems.userPost.toUri())


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
//                onUnSaveClicked(savedPostItems)
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

    class DiffUtil: androidx.recyclerview.widget.DiffUtil.ItemCallback<Home>() {
        override fun areItemsTheSame(oldItem: Home, newItem: Home): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Home, newItem: Home): Boolean {
            return oldItem == (newItem)
        }
    }
}