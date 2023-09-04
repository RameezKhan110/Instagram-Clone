package com.example.testing.home

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.testing.databinding.CustomDialogOpenImageviewBinding

class CustomDialogBox(context: Context): Dialog(context) {

    private lateinit var binding: CustomDialogOpenImageviewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = CustomDialogOpenImageviewBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

//    fun setImageResource(image: String) {
//        Glide.with(context).load(image).into(binding.customZoomImageView)
//    }
}