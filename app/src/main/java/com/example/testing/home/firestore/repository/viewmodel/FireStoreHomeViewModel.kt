package com.example.testing.home.firestore.repository.viewmodel

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.Constraints
import androidx.work.Data
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequest
import androidx.work.PeriodicWorkRequest
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.example.testing.home.firestore.repository.FireStoreHomeRepository
import com.example.testing.home.firestore.repository.model.FireStoreStory
import com.example.testing.home.firestore.repository.model.Post
import com.example.testing.home.firestore.repository.workmanager.CreatePostWorker
import com.example.testing.home.firestore.repository.workmanager.DeleteStoryWorker
import com.example.testing.utils.ApplicationClass
import com.example.testing.utils.NetworkResponse
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class FireStoreHomeViewModel : ViewModel() {

    private val workManager = WorkManager.getInstance(ApplicationClass.application.baseContext)
    private var workManagerForDelete: WorkManager? = null
    private val fireStoreHomeRepository = FireStoreHomeRepository()
    private var createPostWorkReq: OneTimeWorkRequest? = null
    private var deleteStoryWorkReq: PeriodicWorkRequest? = null

//    private val _createLivePost: MutableLiveData<NetworkResponse<Boolean>> = MutableLiveData()
//    val createLivePost: LiveData<NetworkResponse<Boolean>> = _createLivePost

    private val _createLiveStory: MutableLiveData<NetworkResponse<Boolean>> = MutableLiveData()
    val createLiveStory: LiveData<NetworkResponse<Boolean>> = _createLiveStory

    private val _getLivePosts: MutableLiveData<NetworkResponse<List<Post>>> = MutableLiveData()
    val getLivePost: LiveData<NetworkResponse<List<Post>>> = _getLivePosts

    private val _getLiveStory: MutableLiveData<List<FireStoreStory>> = MutableLiveData()
    val getLiveStory: LiveData<List<FireStoreStory>> = _getLiveStory

    private val _getLiveRemoteConfigStrings: MutableLiveData<NetworkResponse<String>> = MutableLiveData()
    val getLiveRemoteConfigStrings: LiveData<NetworkResponse<String>> = _getLiveRemoteConfigStrings

//    fun createPost(userPostImageView: Uri) = viewModelScope.launch {
//        _createLivePost.value = NetworkResponse.Loading()
//        val result = fireStoreHomeRepository.createPost(userPostImageView)
//        _createLivePost.value = result
//    }

    fun createPost(context: Context, userPostImageView: Uri) = viewModelScope.launch {
        val inputData = Data.Builder().putString("user_post", userPostImageView.toString()).build()
        createPostWorkReq =
            OneTimeWorkRequest.Builder(CreatePostWorker::class.java).setInputData(inputData).build()
        WorkManager.getInstance(context).enqueue(createPostWorkReq!!)
    }


    fun createStory(userStoryImageView: Uri) = viewModelScope.launch {
        _createLiveStory.value = NetworkResponse.Loading()
        val result = fireStoreHomeRepository.createStory(userStoryImageView)
        _createLiveStory.value = result
    }

    fun getPosts() = viewModelScope.launch {
        _getLivePosts.value = NetworkResponse.Loading()
        val result = fireStoreHomeRepository.getPosts()
        _getLivePosts.value = result
    }

    fun getStory() = viewModelScope.launch {
        val result = fireStoreHomeRepository.getStories()
        _getLiveStory.value = result
    }

    fun getRemoteConfigStrings() = viewModelScope.launch {
        _getLiveRemoteConfigStrings.value = NetworkResponse.Loading()
        val result = fireStoreHomeRepository.getRemoteConfigStrings()
        _getLiveRemoteConfigStrings.value = result
    }

    fun deleteStory(context: Context) = viewModelScope.launch {
        val constraint = Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()

        if(workManagerForDelete == null) {
            workManagerForDelete = WorkManager.getInstance(ApplicationClass.application.baseContext)
            deleteStoryWorkReq = PeriodicWorkRequest.Builder(DeleteStoryWorker::class.java, 15, TimeUnit.MINUTES)
                .setConstraints(constraint)
                .build()
            Log.d("TAG", "calling work req from view model")
            WorkManager.getInstance(ApplicationClass.application.baseContext)
                .enqueueUniquePeriodicWork("MyUniqueWork", ExistingPeriodicWorkPolicy.KEEP, deleteStoryWorkReq!!)
        }
    }
    fun observeCreatePostWorkReq(workReq: OneTimeWorkRequest): LiveData<WorkInfo> {
        createPostWorkReq = null
        return workManager.getWorkInfoByIdLiveData(workReq.id)
    }
    fun observeDeletePostWorkRequest(workReq: PeriodicWorkRequest): LiveData<WorkInfo>? {
        deleteStoryWorkReq = null
        return workManagerForDelete?.getWorkInfoByIdLiveData(workReq.id)
    }
    fun returnCreatePostWorkRequest(): OneTimeWorkRequest? {
        return createPostWorkReq
    }
    fun returnDeletePostWorkRequest(): PeriodicWorkRequest? {
        return deleteStoryWorkReq
    }
}
