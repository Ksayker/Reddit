package com.ksayker.reddit.utils.listener

import com.ksayker.reddit.domain.entity.Post

interface PostClickListener {
    fun onPostClicked(post: Post)
}