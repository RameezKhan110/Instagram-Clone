package com.example.testing.profile.timeline_post.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.testing.databinding.PostSampleItemBinding
import com.example.testing.profile.timeline_post.model.PostModel

class PostAdapter: RecyclerView.Adapter<PostAdapter.PostAdapterViewHolder>() {

    var postsList: List<PostModel> = arrayListOf()
    lateinit var onTimeLinePostClicked: (PostModel) -> Unit

    inner class PostAdapterViewHolder(binding: PostSampleItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostAdapterViewHolder {
        val viewHolder = PostSampleItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostAdapterViewHolder(viewHolder)
    }

    override fun getItemCount(): Int {
        return postsList.size
    }

    override fun onBindViewHolder(holder: PostAdapterViewHolder, position: Int) {
        val itemList = postsList[position]

        PostSampleItemBinding.bind(holder.itemView).apply {
            timelinePost.setImageResource(itemList.userPost)

            timelinePost.setOnClickListener {
                onTimeLinePostClicked(itemList)
            }
        }
    }


}