package com.example.testing.search.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.testing.R
import com.example.testing.databinding.SampleItemBinding
import com.example.testing.databinding.SearchSampleItemBinding
import com.example.testing.home.model.HomeModel
import com.example.testing.search.model.SearchModel

class SearchAdapter: RecyclerView.Adapter<SearchAdapter.SearchAdapterViewHolder>() {

    var userPost: List<SearchModel> = arrayListOf()
    lateinit var onSearchPostClicked: (SearchModel) -> Unit

    inner class SearchAdapterViewHolder(binding: SearchSampleItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchAdapterViewHolder {
        val viewHolder = SearchSampleItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SearchAdapterViewHolder(viewHolder)
    }

    override fun getItemCount(): Int {
        return userPost.size
    }

    override fun onBindViewHolder(holder: SearchAdapterViewHolder, position: Int) {

        val listItem = userPost[position]

        SearchSampleItemBinding.bind(holder.itemView).apply {
            searchPosts.setImageResource(listItem.userPost)

            searchPosts.setOnClickListener {
                onSearchPostClicked(listItem)
            }
        }
    }
}

