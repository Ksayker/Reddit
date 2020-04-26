package com.ksayker.reddit.data.rest

import com.google.gson.JsonObject
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query


interface RedditApi {
    @GET("/top.json")
    fun getTopPosts(@Query("after") nextPageToken: String): Single<JsonObject>
}

