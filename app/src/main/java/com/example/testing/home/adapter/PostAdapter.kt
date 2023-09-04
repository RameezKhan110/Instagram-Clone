package com.example.testing.home.adapter

import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.example.testing.R
import com.example.testing.databinding.SampleItemBinding
import com.example.testing.home.model.PostModel
import com.example.testing.home.room.post.Home
import com.example.testing.home.model.Wallpapers


class PostAdapter : ListAdapter<PostModel, RecyclerView.ViewHolder>(PostDiffUtil()) {

    var onSaveClicked: ((Home) -> Unit)? = null
    var onPostClicked: ((PostModel) -> Unit)? = null

    inner class PostViewHolder(postBinding: SampleItemBinding) :
        RecyclerView.ViewHolder(postBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val viewHolder =
            SampleItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder(viewHolder)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val postItems = getItem(position)

//        Log.d("TAG", "calling from post adapter: " + postItems.userImage)
        SampleItemBinding.bind(holder.itemView).apply {
//                Log.d("TAG", "User Post: " +postItems.userPost)
                Glide.with(holder.itemView.context)
                    .load(postItems.userPost)
                    .error(R.drawable.error_image)
                    .listener(object : RequestListener<Drawable?> {
                        override fun onLoadFailed(
                            e: GlideException?,
                            model: Any?,
                            target: com.bumptech.glide.request.target.Target<Drawable?>?,
                            isFirstResource: Boolean
                        ): Boolean {
                            progressBar2.setVisibility(View.GONE)
                            return false
                        }

                        override fun onResourceReady(
                            resource: Drawable?,
                            model: Any?,
                            target: com.bumptech.glide.request.target.Target<Drawable?>?,
                            dataSource: com.bumptech.glide.load.DataSource?,
                            isFirstResource: Boolean
                        ): Boolean {
                            progressBar2.setVisibility(View.GONE)
                            userPost.setOnClickListener {
                                onPostClicked?.invoke(postItems)
                            }
                            return false
                        }
                    })
                    .into(userPost)

            Glide.with(holder.itemView.context).load(postItems.userImage).placeholder(R.drawable.placeholder).into(userImage)
            userLocation.text = postItems.userLocation
            userName.text = postItems.userName



//            userImage.setImageURI(postItems.userImage.toUri())
//            userPost.setImageURI(postItems.userPost.toUri())
//            userLocation.text = postItems.userLocation
//            userName.text = postItems.userName

//            if (postItems.isLike) {
//                like.setImageResource(R.drawable.color_heart)
//            } else {
//                like.setImageResource(R.drawable.heart)
//            }
//
//            if (postItems.isSave) {
//                save.setImageResource(R.drawable.bookmark)
//            } else {
//                save.setImageResource(R.drawable.saveinstagram)
//            }
//
//            like.setOnClickListener {
//                if (postItems.isLike) {
//                    like.setImageResource(R.drawable.heart)
//                    postItems.isLike = false
//                } else {
//                    like.setImageResource(R.drawable.color_heart)
//                    postItems.isLike = true
//                }
//
//            }
//
//            userPost.setOnClickListener(object : DoubleClickListener() {
//                override fun onDoubleClick(v: View?) {
//                    like.setImageResource(R.drawable.color_heart)
//                    postItems.isLike = true
//                }
//
//            })
//
//            save.setOnClickListener {
//                if (!postItems.isSave) {
//                    postItems.isSave = true
//                    save.setImageResource(R.drawable.bookmark)
//                    onSaveClicked?.invoke(postItems)
//                }
//
//            }
        }
    }
}

class PostDiffUtil : androidx.recyclerview.widget.DiffUtil.ItemCallback<PostModel>() {
    override fun areItemsTheSame(oldItem: PostModel, newItem: PostModel): Boolean {
        return oldItem.userImage == newItem.userImage
    }

    override fun areContentsTheSame(oldItem: PostModel, newItem: PostModel): Boolean {
        return oldItem == newItem
    }
}