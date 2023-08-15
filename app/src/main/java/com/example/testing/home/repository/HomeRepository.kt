package com.example.testing.home.repository

import com.example.testing.R
import com.example.testing.home.ISSTORY
import com.example.testing.home.model.HomeModel

class HomeRepository {

    val homePost = mutableListOf<HomeModel>()

    fun addPost() {

        if(homePost.isEmpty()) {

            homePost.add(HomeModel(7, R.drawable.image, R.drawable.flower, "Rameez Khan", "Hyderabad", R.drawable.monkey, false, false, ISSTORY.TRUE))
//            homePost.add(HomeModel(8, R.drawable.image, R.drawable.flower, "Rameez Khan", "Hyderabad", R.drawable.monkey, false, false, ISSTORY.TRUE))
//            homePost.add(HomeModel(9, R.drawable.image, R.drawable.flower, "Rameez Khan", "Hyderabad", R.drawable.monkey, false, false, ISSTORY.TRUE))
//            homePost.add(HomeModel(10, R.drawable.image, R.drawable.flower, "Rameez Khan", "Hyderabad", R.drawable.monkey, false, false, ISSTORY.TRUE))
//            homePost.add(HomeModel(11, R.drawable.image, R.drawable.flower, "Rameez Khan", "Hyderabad", R.drawable.monkey, false, false, ISSTORY.TRUE))
//            homePost.add(HomeModel(12, R.drawable.image, R.drawable.flower, "Rameez Khan", "Hyderabad", R.drawable.monkey, false, false, ISSTORY.TRUE))

            homePost.add(HomeModel(1, R.drawable.image, null, "Rameez Khan", "Hyderabad", R.drawable.monkey, false, false, ISSTORY.FALSE))
            homePost.add(HomeModel(2, R.drawable.image, null, "Rameez Khan", "Hyderabad", R.drawable.monkey, false, false, ISSTORY.FALSE))
            homePost.add(HomeModel(3, R.drawable.image, null, "Rameez Khan", "Hyderabad", R.drawable.monkey, false, false, ISSTORY.FALSE))
            homePost.add(HomeModel(4, R.drawable.image, null, "Rameez Khan", "Hyderabad", R.drawable.monkey, false, false, ISSTORY.FALSE))
            homePost.add(HomeModel(5, R.drawable.image, null, "Rameez Khan", "Hyderabad", R.drawable.monkey, false, false, ISSTORY.FALSE))
            homePost.add(HomeModel(6, R.drawable.image, null, "Rameez Khan", "Hyderabad", R.drawable.monkey, false, false, ISSTORY.FALSE))


        }
    }

    fun getPost(): List<HomeModel> {
        return homePost
    }
}