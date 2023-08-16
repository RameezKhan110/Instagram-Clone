package com.example.testing.home.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.testing.R
import com.example.testing.databinding.SampleItemBinding
import com.example.testing.home.model.PostModel
import com.example.testing.utils.DoubleClickListener

class PostAdapter: ListAdapter<PostModel, RecyclerView.ViewHolder>(DiffUtil()){

    lateinit var onSaveClicked: (PostModel) -> Unit

    inner class PostViewHolder(postBinding: SampleItemBinding): RecyclerView.ViewHolder(postBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val viewHolder = SampleItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder(viewHolder)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val postItems = getItem(position)
        SampleItemBinding.bind(holder.itemView).apply {
            userImage.setImageResource(postItems.userImage)
            userLocation.text = postItems.userLocation
            userPost.setImageResource(postItems.userPost)
            userName.text = postItems.userName

            if (postItems.isLike) {
                like.setImageResource(R.drawable.color_heart)
            } else {
                like.setImageResource(R.drawable.heart)
            }

            if (postItems.isSave) {
                save.setImageResource(R.drawable.bookmark)
            } else {
                save.setImageResource(R.drawable.saveinstagram)
            }

            like.setOnClickListener {
                if (postItems.isLike) {
                    like.setImageResource(R.drawable.heart)
                    postItems.isLike = false
                } else {
                    like.setImageResource(R.drawable.color_heart)
                    postItems.isLike = true
                }

            }

            userPost.setOnClickListener(object : DoubleClickListener() {
                override fun onDoubleClick(v: View?) {
                    like.setImageResource(R.drawable.color_heart)
                    postItems.isLike = true
                }

            })

            save.setOnClickListener {
                if (!postItems.isSave) {
                    postItems.isSave = true
                    save.setImageResource(R.drawable.bookmark)
                    onSaveClicked(postItems)
                }

            }

        }
        }
    }



    class DiffUtil : androidx.recyclerview.widget.DiffUtil.ItemCallback<PostModel>() {
        override fun areItemsTheSame(oldItem: PostModel, newItem: PostModel): Boolean {
            return oldItem.postId == newItem.postId
        }

        override fun areContentsTheSame(oldItem: PostModel, newItem: PostModel): Boolean {
            return oldItem == newItem
        }
    }