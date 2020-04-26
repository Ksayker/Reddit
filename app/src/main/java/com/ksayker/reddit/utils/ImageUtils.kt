package com.ksayker.reddit.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.provider.MediaStore
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition


fun loadImage(target: ImageView, url: String) {
    Glide.with(target.context)
        .load(url)
        .into(target)
}

fun loadImage(context: Context, url: String, action: (bitmap: Bitmap) -> Unit) {
    Glide.with(context)
        .asBitmap()
        .load(url)
        .into(object : CustomTarget<Bitmap>() {
            override fun onLoadCleared(placeholder: Drawable?) {
            }

            override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                resource.let { action.invoke(it) }
            }

        })
}

//todo Yura: fix
fun addImageToGallery(context: Context, bitmap: Bitmap) {
    Thread(Runnable {
        MediaStore.Images.Media.insertImage(
            context.contentResolver,
            bitmap,
            "Reddit image",
            "Reddit image"
        );
    }).start()
}