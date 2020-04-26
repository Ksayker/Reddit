package com.ksayker.reddit.data.rest

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.ksayker.reddit.domain.entity.Post
import java.lang.reflect.Type

class PostDeserializer : JsonDeserializer<Post> {

    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): Post {
        return if (json != null) {
            val j = json.asJsonObject["data"].asJsonObject
            Post(
                j["author"].asString,
                j["created_utc"].asLong,
                j["thumbnail"].asString,
                j["num_comments"].asInt
            )
        } else {
            Post.NONE
        }
    }
}