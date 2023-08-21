package com.example.testing.profile.timeline_post.repository

import com.example.testing.R
import com.example.testing.profile.timeline_post.model.PostModels

class PostRepository {

    val timelinePost = mutableListOf<PostModels>()

    fun addTimelinePost() {

        if(timelinePost.isEmpty()) {

            timelinePost.add(PostModels(1, R.drawable.image, "Rameez Khan", "Hyderabad", R.drawable.campfire, false, false))
            timelinePost.add(PostModels(2, R.drawable.image, "Rameez Khan", "Hyderabad", R.drawable.campfire, false, false))
            timelinePost.add(PostModels(3, R.drawable.image, "Rameez Khan", "Hyderabad", R.drawable.campfire, false, false))
            timelinePost.add(PostModels(4, R.drawable.image, "Rameez Khan", "Hyderabad", R.drawable.campfire, false, false))
            timelinePost.add(PostModels(5, R.drawable.image, "Rameez Khan", "Hyderabad", R.drawable.campfire, false, false))
            timelinePost.add(PostModels(6, R.drawable.image, "Rameez Khan", "Hyderabad", R.drawable.campfire, false, false))
            timelinePost.add(PostModels(7, R.drawable.image, "Rameez Khan", "Hyderabad", R.drawable.campfire, false, false))
            timelinePost.add(PostModels(8, R.drawable.image, "Rameez Khan", "Hyderabad", R.drawable.campfire, false, false))
            timelinePost.add(PostModels(9, R.drawable.image, "Rameez Khan", "Hyderabad", R.drawable.campfire, false, false))
            timelinePost.add(PostModels(10, R.drawable.image, "Rameez Khan", "Hyderabad", R.drawable.campfire, false, false))
        }

    }

    fun getTimeLinePost(): List<PostModels> {
        return timelinePost
    }
}