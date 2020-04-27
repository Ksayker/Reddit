package com.ksayker.reddit.data.rest

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class NetworkService() {
    private val retrofit: Retrofit

    val redditApi: RedditApi

    init {
        retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

        redditApi = retrofit.create(RedditApi::class.java)
    }

    companion object {
        private const val BASE_URL = "https://reddit.com"
    }
}