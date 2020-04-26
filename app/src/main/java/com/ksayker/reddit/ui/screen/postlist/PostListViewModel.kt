package com.ksayker.reddit.ui.screen.postlist

import android.os.Handler
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ksayker.reddit.data.PostRepository
import com.ksayker.reddit.domain.entity.Post
import com.ksayker.reddit.utils.extension.threadingSubscribe
import io.reactivex.observers.DisposableSingleObserver

class PostListViewModel(private val postRepository: PostRepository): ViewModel() {
    private val postItems = ArrayList<Post>()

    val ldPostItems = MutableLiveData<MutableList<Post>>()

    fun initList(init: Boolean) {
        if (init) {
            val observer = object: DisposableSingleObserver<List<Post>>() {

                override fun onSuccess(items: List<Post>) {
                    postItems.addAll(items)
                    ldPostItems.value = postItems
                }

                override fun onError(e: Throwable) {
                    e.printStackTrace()
                }
            }

            postRepository.getTopPosts()
                .threadingSubscribe()
                .subscribe(observer)
        }
    }
}