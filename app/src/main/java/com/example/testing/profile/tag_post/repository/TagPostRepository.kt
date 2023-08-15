package com.example.testing.profile.tag_post.repository

import com.example.testing.R
import com.example.testing.profile.tag_post.model.TagPostModel

class TagPostRepository {

    val tagPost = mutableListOf<TagPostModel>()

    fun addTagPost() {

        if (tagPost.isEmpty()) {

            tagPost.add(TagPostModel(R.drawable.flower))
            tagPost.add(TagPostModel(R.drawable.flower))
            tagPost.add(TagPostModel(R.drawable.flower))
            tagPost.add(TagPostModel(R.drawable.flower))
            tagPost.add(TagPostModel(R.drawable.flower))
            tagPost.add(TagPostModel(R.drawable.flower))
            tagPost.add(TagPostModel(R.drawable.flower))
            tagPost.add(TagPostModel(R.drawable.flower))
            tagPost.add(TagPostModel(R.drawable.flower))
            tagPost.add(TagPostModel(R.drawable.flower))
        }
    }

    fun getTagPosts(): List<TagPostModel> {
        return tagPost
    }
}