package com.ksayker.reddit.domain.entity

data class PostResponse(
    val nextPageToken: String,
    val postList: List<Post>
)