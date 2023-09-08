package com.example.testing.home.firestore.repository.workmanager

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.testing.home.firestore.repository.FireStoreHomeRepository
import java.lang.Exception

class DeleteStoryWorker(context: Context, params: WorkerParameters): CoroutineWorker(context, params) {

    private val fireStoreHomeRepository = FireStoreHomeRepository()
    override suspend fun doWork(): Result {
        return try {
            Log.d("TAG", "delete story worker calling")
            fireStoreHomeRepository.deleteStory()
            Result.success()
        } catch (e: Exception) {
            e.printStackTrace()
            Log.d("TAG", "worker in failure story" + e)
            Result.failure()
        }
    }
}