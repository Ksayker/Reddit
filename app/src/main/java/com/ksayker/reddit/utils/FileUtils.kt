package com.ksayker.reddit.utils

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import java.io.File
import java.io.IOException
import java.io.OutputStream


fun saveBitmapToFile(context: Context, bitmap: Bitmap, name: String): File {
    val file = createImageFile(context, name)
    saveBitmapToFile(context, bitmap, Uri.fromFile(file))

    return file
}

fun createImageFile(context: Context, name: String): File {
    val storageDir: File? = context.getExternalFilesDir("images")

    return File.createTempFile(
        name, /* prefix */
        ".jpg", /* suffix */
        storageDir /* directory */
    )
}

fun saveBitmapToFile(context: Context, bitmap: Bitmap, imageUri: Uri) {
    var outputStream: OutputStream? = null
    try {
        outputStream = context.contentResolver.openOutputStream(imageUri)
        if (outputStream != null) {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, outputStream)
        }
    } catch (e: IOException) {
    } finally {
        if (outputStream == null) {
            return
        }
        try {
            outputStream.close()
        } catch (t: Throwable) {
            // Do nothing
        }
        bitmap.recycle()
    }
}
