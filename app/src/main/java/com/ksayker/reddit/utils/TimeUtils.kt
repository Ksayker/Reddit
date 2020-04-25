package com.ksayker.reddit.utils

import android.content.Context
import com.ksayker.reddit.R

//todo Yura: handle GMT
fun getPostTimeFormat(context: Context, timestamp: Long): String {
    val hourDiff = (System.currentTimeMillis() - timestamp).toFloat() / (1000 * 60 * 60)

    val format = if (hourDiff >= 1) {
        "%.0f".format(hourDiff)
    } else {
        "%.1f".format(hourDiff)
    }

    return context.getString(R.string.text_postItem_hoursAgo, format)
}