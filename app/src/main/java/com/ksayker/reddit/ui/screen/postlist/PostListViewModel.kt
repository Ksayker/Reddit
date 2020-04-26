package com.ksayker.reddit.ui.screen.postlist

import android.os.Handler
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ksayker.reddit.domain.entity.Post

class PostListViewModel: ViewModel() {
    private val postItems = ArrayList<Post>()

    val ldPostItems = MutableLiveData<MutableList<Post>>()

    fun initList(init: Boolean) {
        if (init) {
            postItems.add(Post("Vasya", 1580001240274L, "https://media.sproutsocial.com/uploads/2017/02/10x-featured-social-media-image-size.png", 1))
            postItems.add(Post("Petya", 1580001240274L, Post.NO_IMAGE, 1))
            postItems.add(Post("Gena", 1580001240274L, "https://media.sproutsocial.com/uploads/2017/02/10x-featured-social-media-image-size.png", 1))
            postItems.add(Post("Yura", 1580001240274L, "https://media.sproutsocial.com/uploads/2017/02/10x-featured-social-media-image-size.png", 1))
            postItems.add(Post("Katya", 1580001240274L, "https://media.sproutsocial.com/uploads/2017/02/10x-featured-social-media-image-size.png", 1))
            postItems.add(Post("Ivan", 1580001240274L, "https://media.sproutsocial.com/uploads/2017/02/10x-featured-social-media-image-size.png", 1))
            postItems.add(Post("Don", 1580001240274L, "https://media.sproutsocial.com/uploads/2017/02/10x-featured-social-media-image-size.png", 1))
            postItems.add(Post("Din", 1580001240274L, "https://media.sproutsocial.com/uploads/2017/02/10x-featured-social-media-image-size.png", 1))
            postItems.add(Post("Dun", 1580001240274L, "https://media.sproutsocial.com/uploads/2017/02/10x-featured-social-media-image-size.png", 1))

            ldPostItems.value = postItems

            Handler().postDelayed({
                postItems.add(Post("111", System.currentTimeMillis(), Post.NO_IMAGE, 1))
                postItems.add(Post("222", System.currentTimeMillis(), Post.NO_IMAGE, 1))
                postItems.add(Post("33", System.currentTimeMillis(), Post.NO_IMAGE, 1))

                ldPostItems.value = postItems
            }, 5000)
        }
    }
}