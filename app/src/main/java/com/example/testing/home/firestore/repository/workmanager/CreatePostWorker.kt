package com.example.testing.home.firestore.repository.workmanager

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.core.net.toUri
import androidx.work.CoroutineWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.testing.home.firestore.repository.FireStoreHomeRepository
import com.example.testing.home.firestore.repository.viewmodel.FireStoreHomeViewModel
import com.example.testing.utils.NetworkResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class CreatePostWorker(context: Context, params: WorkerParameters) :
    CoroutineWorker(context, params) {

    private val fireStoreHomeRepository = FireStoreHomeRepository()
    private val viewModel = FireStoreHomeViewModel()
    override suspend fun doWork(): Result {

        return try {
            Log.d("TAG", "Worker Called")
            val userPostString = inputData.getString("user_post")
            val userPost = userPostString?.toUri()
            if (userPost != null) {
                fireStoreHomeRepository.createPost(userPost)
            }
            Log.d("TAG", "success in worker")
            Result.success()
        } catch (e: Throwable) {
            e.printStackTrace()
            Log.d("TAG", "failure in worker" + e.cause)
            Result.failure()
        }
    }
}