package com.ksayker.reddit.ui.core

import com.ksayker.reddit.domain.entity.Post

interface NavigationManager {
    fun openImageUrl(url: String)

    fun openPost(post: Post)
}