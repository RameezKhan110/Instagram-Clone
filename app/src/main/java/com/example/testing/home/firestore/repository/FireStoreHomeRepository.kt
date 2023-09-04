package com.example.testing.home.firestore.repository

import android.net.Uri
import android.util.Log
import com.example.testing.home.firestore.repository.model.FireStoreStory
import com.example.testing.home.firestore.repository.model.Post
import com.example.testing.utils.NetworkResponse
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await

class FireStoreHomeRepository {

    private val fireStore: FirebaseFirestore = Firebase.firestore

    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()

    suspend fun createPost(userPostImageview: Uri): NetworkResponse<Boolean>{

        val userId = firebaseAuth.currentUser?.uid
        val storageRef = FirebaseStorage.getInstance().reference
        val imageName = "${System.currentTimeMillis()}.jpg"
        val imageRef = storageRef.child("post_images/${imageName}.jpg")

        imageRef.putFile(userPostImageview).await()
        val downloadUrlInString = imageRef.downloadUrl.await().toString()
        val userPost = Post(userId!!, downloadUrlInString)

        return try {
            fireStore.collection("User").document(userId).collection("Posts").add(userPost).await()
            NetworkResponse.Success(true)
        } catch (e: Exception) {
            e.printStackTrace()
            NetworkResponse.Error(e.message)
        }
    }

    suspend fun createStory(userStoryImageView: Uri): NetworkResponse<Boolean> {

        val storyId = fireStore.collection("Stories").document().id
        val userId = firebaseAuth.currentUser?.uid
        val storageRef = FirebaseStorage.getInstance().reference
        val storyImageName = "${System.currentTimeMillis()}.jpg"
        val imageRef = storageRef.child("story_images/${storyImageName}.jpg")

        imageRef.putFile(userStoryImageView).await()
        val downloadStoryImagePath = imageRef.downloadUrl.await().toString()
        val userStory = FireStoreStory(userId!!, downloadStoryImagePath)

        return try {
            fireStore.collection("Stories").document(storyId).set(userStory).await()
            NetworkResponse.Success(true)
        } catch (e: Exception) {
            e.printStackTrace()
            NetworkResponse.Error(e.message)
        }
    }

    suspend fun getPosts(): NetworkResponse<List<Post>> {
        val postList = mutableListOf<Post>()
        val userId = firebaseAuth.currentUser?.uid
        val postDetailRef = fireStore.collection("User").document(userId!!).collection("Posts").get()

        try {
            postDetailRef.await().let {
                for(doc in it.documents) {
                    val posts = doc.toObject<Post>()
                    posts?.let {
                        postList.add(posts)
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return NetworkResponse.Success(postList)
    }

    suspend fun getStories(): List<FireStoreStory> {
        val storyList = mutableListOf<FireStoreStory>()
        val userId = firebaseAuth.currentUser?.uid
        val storyDetailRef = fireStore.collection("Stories").whereEqualTo("storyId", userId).get()

        try {
            storyDetailRef.await().let {
                for(doc in it.documents) {
                    val story = doc.toObject<FireStoreStory>()
                    if (story != null) {
                        storyList.add(story)
                        Log.d("TAG", "calling from repo Stories" + storyList)
                    }
                }
            }
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
            Log.d("TAG", "stories exception from firestore")
        }
        return storyList
    }
}