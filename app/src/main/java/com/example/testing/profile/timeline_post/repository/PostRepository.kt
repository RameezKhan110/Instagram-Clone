package com.example.testing.profile.timeline_post.repository

import com.example.testing.R
import com.example.testing.profile.timeline_post.model.PostModel

class PostRepository {

    val timelinePost = mutableListOf<PostModel>()

    fun addTimelinePost() {

        if(timelinePost.isEmpty()) {

            timelinePost.add(PostModel(1, R.drawable.image, "Rameez Khan", "Hyderabad", R.drawable.campfire, false, false))
            timelinePost.add(PostModel(2, R.drawable.image, "Rameez Khan", "Hyderabad", R.drawable.campfire, false, false))
            timelinePost.add(PostModel(3, R.drawable.image, "Rameez Khan", "Hyderabad", R.drawable.campfire, false, false))
            timelinePost.add(PostModel(4, R.drawable.image, "Rameez Khan", "Hyderabad", R.drawable.campfire, false, false))
            timelinePost.add(PostModel(5, R.drawable.image, "Rameez Khan", "Hyderabad", R.drawable.campfire, false, false))
            timelinePost.add(PostModel(6, R.drawable.image, "Rameez Khan", "Hyderabad", R.drawable.campfire, false, false))
            timelinePost.add(PostModel(7, R.drawable.image, "Rameez Khan", "Hyderabad", R.drawable.campfire, false, false))
            timelinePost.add(PostModel(8, R.drawable.image, "Rameez Khan", "Hyderabad", R.drawable.campfire, false, false))
            timelinePost.add(PostModel(9, R.drawable.image, "Rameez Khan", "Hyderabad", R.drawable.campfire, false, false))
            timelinePost.add(PostModel(10, R.drawable.image, "Rameez Khan", "Hyderabad", R.drawable.campfire, false, false))
        }

    }

    fun getTimeLinePost(): List<PostModel> {
        return timelinePost
    }
}