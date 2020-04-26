package com.ksayker.reddit.data

import com.ksayker.reddit.domain.entity.Post
import io.reactivex.Single

interface PostRepository {
    fun getTopPosts(): Single<List<Post>>
}