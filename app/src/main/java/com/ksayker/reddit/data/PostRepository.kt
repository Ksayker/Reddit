package com.ksayker.reddit.data

import com.ksayker.reddit.domain.entity.PostResponse
import io.reactivex.Single

interface PostRepository {
    fun getTopPosts(nextPageToken: String): Single<PostResponse>
}