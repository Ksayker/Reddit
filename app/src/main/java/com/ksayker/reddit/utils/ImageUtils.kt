package com.ksayker.reddit.utils

import android.widget.ImageView
import com.bumptech.glide.Glide

fun loadImage(target: ImageView, url: String) {
    Glide.with(target.context)
        .load(url)
        .into(target)
}