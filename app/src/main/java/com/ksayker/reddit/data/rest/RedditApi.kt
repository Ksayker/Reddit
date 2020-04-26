package com.ksayker.reddit.data.rest

import com.google.gson.JsonObject
import io.reactivex.Single
import retrofit2.http.GET


interface RedditApi {
    @GET("/top.json")
    fun getTopPosts(): Single<JsonObject>
}

