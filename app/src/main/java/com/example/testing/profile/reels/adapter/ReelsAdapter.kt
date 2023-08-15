package com.example.testing.profile.reels.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.testing.R
import com.example.testing.databinding.ReelsSampleItemBinding
import com.example.testing.profile.reels.model.ReelsModel

class ReelsAdapter(private val reels: List<ReelsModel>): RecyclerView.Adapter<ReelsAdapter.ReelsAdapterViewHolder>() {

    inner class ReelsAdapterViewHolder(binding: ReelsSampleItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReelsAdapterViewHolder {
        val viewHolder = ReelsSampleItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ReelsAdapterViewHolder(viewHolder)
    }

    override fun getItemCount(): Int {
        return reels.size
    }

    override fun onBindViewHolder(holder: ReelsAdapterViewHolder, position: Int) {
        val reelsItem = reels[position]

        ReelsSampleItemBinding.bind(holder.itemView).apply {
            reelsVideo.setImageResource(reelsItem.reel)
        }
    }
}