package com.ksayker.reddit.data

import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import com.ksayker.reddit.data.rest.NetworkService
import com.ksayker.reddit.data.rest.PostDeserializer
import com.ksayker.reddit.domain.entity.Post
import com.ksayker.reddit.domain.entity.PostResponse
import io.reactivex.Single

class PostRepositoryImpl : PostRepository {

    private fun parseResponse(json: JsonObject): PostResponse {
        val gsonBuilder = GsonBuilder()

        val deserializer = PostDeserializer()
        gsonBuilder.registerTypeAdapter(Post::class.java, deserializer)

        val customGson = gsonBuilder.create()
        val posts = customGson.fromJson<List<Post>>(
            json["data"].asJsonObject["children"],
            object : TypeToken<List<Post>>() {}.type
        )

        return PostResponse(json["data"].asJsonObject["after"].asString, posts)
    }

    override fun getTopPosts(nextPageToken: String): Single<PostResponse> {
        return NetworkService.instance.redditApi.getTopPosts(nextPageToken)
            .flatMap { json: JsonObject ->
                Single.fromCallable { parseResponse(json) }
            }
    }
}