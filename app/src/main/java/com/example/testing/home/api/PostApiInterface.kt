package com.example.testing.home.api

import com.example.testing.home.model.Wallpapers
import com.example.testing.utils.Const
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

//https://api.unsplash.com/photos/?client_id=DfKVnID6Gkt0Z-QPoxH42CJsS9u-fAZH2TfML9DrgWg

interface PostApiInterface {


    @GET("photos/?client_id="+Const.Client_Id)
    suspend fun getPosts(): List<Wallpapers>
}

object PostApiService {

    val postApiInterface: PostApiInterface

    init {
        val retrofit = Retrofit.Builder().baseUrl(Const.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        postApiInterface = retrofit.create(PostApiInterface::class.java)
    }
}