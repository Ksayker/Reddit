package com.ksayker.reddit.ui.screen.postlist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ksayker.reddit.data.PostRepository
import com.ksayker.reddit.domain.entity.Post
import com.ksayker.reddit.domain.entity.PostResponse
import com.ksayker.reddit.utils.extension.threadingSubscribe
import io.reactivex.observers.DisposableSingleObserver

class PostListViewModel(private val postRepository: PostRepository): ViewModel() {

    private val postItems = ArrayList<Post>()
    private var nextPageToken = ""

    val ldPostItems = MutableLiveData<MutableList<Post>>()

    fun loadNextPage() {
        val observer = object: DisposableSingleObserver<PostResponse>() {

            override fun onSuccess(response: PostResponse) {
                nextPageToken = response.nextPageToken

                postItems.addAll(response.postList)
                ldPostItems.value = postItems
            }

            override fun onError(e: Throwable) {
                e.printStackTrace()
            }
        }

        postRepository.getTopPosts(nextPageToken)
            .threadingSubscribe()
            .subscribe(observer)
    }
}