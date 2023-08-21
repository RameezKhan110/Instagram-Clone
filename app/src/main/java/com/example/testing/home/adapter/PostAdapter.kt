package com.example.testing.home.adapter

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

class PostAdapter : ListAdapter<Home, RecyclerView.ViewHolder>(PostDiffUtil()) {

    var onSaveClicked: ((Home) -> Unit)? = null

    inner class PostViewHolder(postBinding: SampleItemBinding) :
        RecyclerView.ViewHolder(postBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val viewHolder =
            SampleItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder(viewHolder)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val postItems = getItem(position)
        SampleItemBinding.bind(holder.itemView).apply {
            userImage.setImageURI(postItems.userImage.toUri())
            userPost.setImageURI(postItems.userPost.toUri())
            userLocation.text = postItems.userLocation
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
                    onSaveClicked?.invoke(postItems)
                }

            }
        }
    }
}

class PostDiffUtil : androidx.recyclerview.widget.DiffUtil.ItemCallback<Home>() {
    override fun areItemsTheSame(oldItem: Home, newItem: Home): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Home, newItem: Home): Boolean {
        return oldItem == newItem
    }
}