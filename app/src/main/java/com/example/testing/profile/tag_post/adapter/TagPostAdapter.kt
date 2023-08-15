package com.example.testing.profile.tag_post.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.testing.databinding.TagpostSampleItemBinding
import com.example.testing.profile.tag_post.model.TagPostModel

class TagPostAdapter(private val taggedPost: List<TagPostModel>): RecyclerView.Adapter<TagPostAdapter.TagPostViewHolder>(){

    inner class TagPostViewHolder(binding: TagpostSampleItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TagPostViewHolder {
        val viewHolder = TagpostSampleItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TagPostViewHolder(viewHolder)
    }

    override fun getItemCount(): Int {
        return taggedPost.size
    }

    override fun onBindViewHolder(holder: TagPostViewHolder, position: Int) {
        val itemList = taggedPost[position]

        TagpostSampleItemBinding.bind(holder.itemView).apply {
            taggedImage.setImageResource(itemList.taggedPost)
        }
    }
}