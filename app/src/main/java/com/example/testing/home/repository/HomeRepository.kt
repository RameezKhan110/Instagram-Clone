package com.example.testing.home.repository

import androidx.lifecycle.LiveData
import com.example.testing.api.PostApiService
import com.example.testing.home.model.HomeModel
import com.example.testing.home.model.PostModel
import com.example.testing.home.model.StoryModel
import com.example.testing.home.room.post.Home
import com.example.testing.home.room.post.HomeDao
import com.example.testing.home.room.post.story.Story
import com.example.testing.home.room.post.story.StoryDao
import com.example.testing.model.Urls
import com.example.testing.model.Wallpapers

class HomeRepository(private val postApiService: PostApiService, private val storyDao: StoryDao) {


    suspend fun getApiPosts(): List<Wallpapers> {
        return postApiService.postApiInterface.getPosts()
    }
//    suspend fun addPost(post: Home) {
//        homeDao.addPost(post)
//    }
//
//    fun getPost(): LiveData<List<Home>> {
//        return homeDao.getPost()
//    }
//
//    suspend fun deleteTable() {
//        homeDao.dropTable()
//    }

    suspend fun addStory(story: Story) {
        storyDao.addStory(story)
    }

    fun getStory(): LiveData<List<Story>> {
        return storyDao.getStory()
    }
}