package com.ksayker.reddit.ui.screen.postlist

import android.os.Handler
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PostListModel: ViewModel() {
    private val postItems = ArrayList<String>()

    val ldPostItems = MutableLiveData<MutableList<String>>()

    fun initList(init: Boolean) {
        if (init) {
            postItems.add("qwe")
            postItems.add("asd")
            postItems.add("zxc")
            postItems.add("rty")
            postItems.add("fgh")
            postItems.add("vbn")

            ldPostItems.value = postItems

            Handler().postDelayed({
                postItems.add("111")
                postItems.add("222")
                postItems.add("333")

                ldPostItems.value = postItems
            }, 5000)
        }
    }
}