package com.example.testing.search.post_detail.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.testing.R
import com.example.testing.databinding.SampleItemBinding
import com.example.testing.search.model.SearchModel
import com.example.testing.utils.DoubleClickListener

class PostDetailAdapter : ListAdapter<SearchModel, PostDetailAdapter.PostDetailViewHolder>(DiffUtil()) {

    lateinit var onSaveClicked: (SearchModel) -> Unit

    inner class PostDetailViewHolder(binding: SampleItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostDetailViewHolder {
        val viewHolder =
            SampleItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostDetailViewHolder(viewHolder)
    }

    override fun onBindViewHolder(holder: PostDetailViewHolder, position: Int) {

        val postDetailItem = getItem(position)

        SampleItemBinding.bind(holder.itemView).apply {

            userName.text = postDetailItem.userName
            userLocation.text = postDetailItem.userLocation
            userImage.setImageResource(postDetailItem.userImage)
            userPost.setImageResource(postDetailItem.userPost)


            if (postDetailItem.isLike) {
                like.setImageResource(R.drawable.color_heart)
            } else {
                like.setImageResource(R.drawable.heart)
            }

            if (postDetailItem.isSave) {
                save.setImageResource(R.drawable.bookmark)
            }

            save.setOnClickListener {
                postDetailItem.isSave = true
                onSaveClicked(postDetailItem)
            }

            like.setOnClickListener {
                if (postDetailItem.isLike) {
                    like.setImageResource(R.drawable.heart)
                    postDetailItem.isLike = false
                } else {
                    like.setImageResource(R.drawable.color_heart)
                    postDetailItem.isLike = true
                }

            }

            userPost.setOnClickListener(object : DoubleClickListener() {
                override fun onDoubleClick(v: View?) {
                    like.setImageResource(R.drawable.color_heart)
                    postDetailItem.isLike = true
                }

            })
        }

    }

    class DiffUtil: androidx.recyclerview.widget.DiffUtil.ItemCallback<SearchModel>() {
        override fun areItemsTheSame(oldItem: SearchModel, newItem: SearchModel): Boolean {
            return oldItem.postId == newItem.postId
        }

        override fun areContentsTheSame(oldItem: SearchModel, newItem: SearchModel): Boolean {
            return oldItem.equals(newItem)
        }
    }
}