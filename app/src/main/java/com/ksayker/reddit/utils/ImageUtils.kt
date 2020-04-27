package com.ksayker.reddit.utils

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import java.io.File
import java.io.IOException


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

fun addImageToGallery(context: Context, bitmap: Bitmap) {
    val relativeLocation = Environment.DIRECTORY_PICTURES + File.pathSeparator + "Reddit"
    val contentValues = ContentValues().apply {
        put(MediaStore.MediaColumns.DISPLAY_NAME, System.currentTimeMillis().toString())
        put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            put(MediaStore.MediaColumns.RELATIVE_PATH, relativeLocation)
            put(MediaStore.MediaColumns.IS_PENDING, 1)
        }
    }

    val resolver = context.contentResolver

    val uri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)

    try {
        uri?.let { uri ->
            val stream = resolver.openOutputStream(uri)

            stream?.let { stream ->
                if (!bitmap.compress(Bitmap.CompressFormat.JPEG, 80, stream)) {
                    throw IOException("Failed to save bitmap.")
                }
            } ?: throw IOException("Failed to get output stream.")

        } ?: throw IOException("Failed to create new MediaStore record")

    } catch (e: IOException) {
        if (uri != null) {
            resolver.delete(uri, null, null)
        }
        throw IOException(e)
    } finally {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q)
            contentValues.put(MediaStore.MediaColumns.IS_PENDING, 0)
    }
}