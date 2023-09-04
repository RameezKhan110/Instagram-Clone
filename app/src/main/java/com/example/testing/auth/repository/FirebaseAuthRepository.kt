package com.example.testing.auth.repository

import android.net.Network
import android.net.Uri
import android.util.Log
import com.example.testing.auth.model.User
import com.example.testing.utils.FirebaseResource
import com.example.testing.utils.NetworkResponse
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await

class FirebaseAuthRepository {

    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private val fireStore: FirebaseFirestore = Firebase.firestore

    suspend fun registerUser(
        userEmail: String,
        userPassword: String
    ): FirebaseResource<FirebaseUser> {

        return try {

            val result =
                firebaseAuth.createUserWithEmailAndPassword(userEmail, userPassword).await()
            FirebaseResource.Success(result.user!!)

        } catch (e: Exception) {
            e.printStackTrace()
            FirebaseResource.Error(e)
        }
    }

    suspend fun signInUser(
        userEmail: String,
        userPassword: String
    ): FirebaseResource<FirebaseUser> {

        return try {
            Log.d("TAG", "signing in")
            val result = firebaseAuth.signInWithEmailAndPassword(userEmail, userPassword).await()
            FirebaseResource.Success(result.user!!)

        } catch (e: Exception) {
            Log.d("TAG", "error in signing")
            e.printStackTrace()
            FirebaseResource.Error(e)
        }

    }

    fun signOutUser() {
        firebaseAuth.signOut()
    }

    suspend fun saveUserDetails(
        userImage: Uri,
        userName: String,
        userEmail: String,
        userPassword: String
    ): NetworkResponse<Boolean> {

        val userid = firebaseAuth.currentUser?.uid
        val storageRef = FirebaseStorage.getInstance().reference
        val userImageName = "${System.currentTimeMillis()}.jpg"
        val userImageRef = storageRef.child("user_image/${userImageName}.jpg")

        userImageRef.putFile(userImage).await()
        val downloadUserImageUrl = userImageRef.downloadUrl.await().toString()
        val user = User(downloadUserImageUrl, userName, userEmail, userPassword, userid)

        return try {
            fireStore.collection("User").document(userid!!).set(user).await()
            NetworkResponse.Success(true)
        } catch (e: Exception) {
            e.printStackTrace()
            Log.d("TAG", "Saving User Details: $e")
            NetworkResponse.Error(e.message)
        }

    }

    suspend fun getUserDetails(): NetworkResponse<User> {
        val userDetailsRef =
            fireStore.collection("User").whereEqualTo("userId", firebaseAuth.currentUser?.uid).get()

        try {
            userDetailsRef.await().let {
                for (doc in it.documents) {
                    val details = doc.toObject<User>()
                    details?.let { details ->
//                        Log.d("TAG", "details from repo" + details)
                        return NetworkResponse.Success(details)
                    }
                }
                return NetworkResponse.Error("User details not found")
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Log.d("TAG", "e" + e)
            return NetworkResponse.Error(e.message)
        }
    }
}