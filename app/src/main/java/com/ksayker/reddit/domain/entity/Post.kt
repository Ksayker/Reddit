package com.ksayker.reddit.domain.entity

data class Post(
    val author: String,
    val date: Long,
    val image: String = NO_IMAGE,
    val commentsCount: Int
) {
    companion object {
        const val NO_IMAGE = "default"

        val NONE = Post("", 0L, NO_IMAGE, 0)
    }
}