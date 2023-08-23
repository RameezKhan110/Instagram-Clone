package com.example.testing.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.testing.R
import com.example.testing.databinding.SampleItemBinding
import com.example.testing.home.model.PostModel
import com.example.testing.home.room.post.Home
import com.example.testing.model.Urls
import com.example.testing.model.Wallpapers
import com.example.testing.utils.DoubleClickListener

class PostAdapter : ListAdapter<Wallpapers, RecyclerView.ViewHolder>(PostDiffUtil()) {

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
            Glide.with(holder.itemView.context).load(postItems.urls.raw).into(userImage)
            Glide.with(holder.itemView.context).load(postItems.urls.full).into(userPost)
            userLocation.text = "Hyderabad"
            userName.text = "Rameez Khan"

//            userImage.setImageURI(postItems.userImage.toUri())
//            userPost.setImageURI(postItems.userPost.toUri())
//            userLocation.text = postItems.userLocation
//            userName.text = postItems.userName

//            if (postItems.isLike) {
//                like.setImageResource(R.drawable.color_heart)
//            } else {
//                like.setImageResource(R.drawable.heart)
//            }
//
//            if (postItems.isSave) {
//                save.setImageResource(R.drawable.bookmark)
//            } else {
//                save.setImageResource(R.drawable.saveinstagram)
//            }
//
//            like.setOnClickListener {
//                if (postItems.isLike) {
//                    like.setImageResource(R.drawable.heart)
//                    postItems.isLike = false
//                } else {
//                    like.setImageResource(R.drawable.color_heart)
//                    postItems.isLike = true
//                }
//
//            }
//
//            userPost.setOnClickListener(object : DoubleClickListener() {
//                override fun onDoubleClick(v: View?) {
//                    like.setImageResource(R.drawable.color_heart)
//                    postItems.isLike = true
//                }
//
//            })
//
//            save.setOnClickListener {
//                if (!postItems.isSave) {
//                    postItems.isSave = true
//                    save.setImageResource(R.drawable.bookmark)
//                    onSaveClicked?.invoke(postItems)
//                }
//
//            }
        }
    }
}

class PostDiffUtil : androidx.recyclerview.widget.DiffUtil.ItemCallback<Wallpapers>() {
    override fun areItemsTheSame(oldItem: Wallpapers, newItem: Wallpapers): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Wallpapers, newItem: Wallpapers): Boolean {
        return oldItem == newItem
    }
}