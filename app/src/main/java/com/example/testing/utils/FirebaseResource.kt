package com.example.testing.utils

sealed class FirebaseResource<out R>{

    data class Success<out R>(val result: R): FirebaseResource<R>()
    data class Error(val exception: Exception): FirebaseResource<Nothing>()
    object Loading: FirebaseResource<Nothing>()
}