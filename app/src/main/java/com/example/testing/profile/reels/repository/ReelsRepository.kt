package com.example.testing.profile.reels.repository

import com.example.testing.R
import com.example.testing.profile.reels.model.ReelsModel

class ReelsRepository {

    val reelsItem = mutableListOf<ReelsModel>()

    fun addReels() {

        if (reelsItem.isEmpty()) {

            reelsItem.add(ReelsModel(R.drawable.vulture))
            reelsItem.add(ReelsModel(R.drawable.vulture))
            reelsItem.add(ReelsModel(R.drawable.vulture))
            reelsItem.add(ReelsModel(R.drawable.vulture))
            reelsItem.add(ReelsModel(R.drawable.vulture))
            reelsItem.add(ReelsModel(R.drawable.vulture))
            reelsItem.add(ReelsModel(R.drawable.vulture))
            reelsItem.add(ReelsModel(R.drawable.vulture))
            reelsItem.add(ReelsModel(R.drawable.vulture))
            reelsItem.add(ReelsModel(R.drawable.vulture))
            reelsItem.add(ReelsModel(R.drawable.vulture))
            reelsItem.add(ReelsModel(R.drawable.vulture))
            reelsItem.add(ReelsModel(R.drawable.vulture))
        }
    }

    fun getReels(): List<ReelsModel> {
        return reelsItem
    }
}