package com.ksayker.reddit.utils

import android.content.Context
import android.graphics.Bitmap
import android.provider.MediaStore
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target


fun loadImage(target: ImageView, url: String) {
    Glide.with(target.context)
        .load(url)
        .into(target)
}

fun loadImage(context: Context, url: String, action: (bitmap: Bitmap) -> Unit) {
    Glide.with(context)
        .asBitmap()
        .load(url)
        .listener(object : RequestListener<Bitmap> {
            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<Bitmap>?,
                isFirstResource: Boolean
            ): Boolean {
                return true
            }

            override fun onResourceReady(
                resource: Bitmap?,
                model: Any?,
                target: Target<Bitmap>?,
                dataSource: DataSource?,
                isFirstResource: Boolean
            ): Boolean {
                resource?.let { action.invoke(it) }

                return true
            }

        })
        .submit()
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