package com.example.testing.profile.reels.repository

import com.example.testing.R
import com.example.testing.profile.reels.model.ReelsModel

class ReelsRepository {

    val reelsItem = mutableListOf<ReelsModel>()

    fun addReels() {

        if (reelsItem.isEmpty()) {

            reelsItem.add(ReelsModel(R.drawable.monkey))
            reelsItem.add(ReelsModel(R.drawable.monkey))
            reelsItem.add(ReelsModel(R.drawable.monkey))
            reelsItem.add(ReelsModel(R.drawable.monkey))
            reelsItem.add(ReelsModel(R.drawable.monkey))
            reelsItem.add(ReelsModel(R.drawable.monkey))
            reelsItem.add(ReelsModel(R.drawable.monkey))
            reelsItem.add(ReelsModel(R.drawable.monkey))
            reelsItem.add(ReelsModel(R.drawable.monkey))
            reelsItem.add(ReelsModel(R.drawable.monkey))
            reelsItem.add(ReelsModel(R.drawable.monkey))

        }
    }

    fun getReels(): List<ReelsModel> {
        return reelsItem
    }
}