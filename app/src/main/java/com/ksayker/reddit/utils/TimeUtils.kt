package com.ksayker.reddit.utils

import android.content.Context
import com.ksayker.reddit.R

fun getPostTimeFormat(context: Context, timestamp: Long): String {
    val hourDiff = (System.currentTimeMillis() / 1000 - timestamp).toFloat() / (60 * 60)

    val format = if (hourDiff >= 1) {
        "%.0f".format(hourDiff)
    } else {
        "%.1f".format(hourDiff)
    }

    return context.getString(R.string.text_postItem_hoursAgo, format)
}