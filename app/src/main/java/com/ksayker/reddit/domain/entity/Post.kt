package com.ksayker.reddit.domain.entity

import android.os.Parcel
import android.os.Parcelable

data class Post(
    val author: String,
    val date: Long,
    val image: String = NO_IMAGE,
    val commentsCount: Int,
    val title: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readLong(),
        parcel.readString()!!,
        parcel.readInt(),
        parcel.readString()!!
    )

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeString(author)
        dest?.writeLong(date)
        dest?.writeString(image)
        dest?.writeInt(commentsCount)
        dest?.writeString(title)
    }

    override fun describeContents() = 0

    companion object CREATOR : Parcelable.Creator<Post> {
        const val NO_IMAGE = "default"

        val NONE = Post("", 0L, NO_IMAGE, 0, "")

        override fun createFromParcel(parcel: Parcel): Post {
            return Post(parcel)
        }

        override fun newArray(size: Int): Array<Post?> {
            return arrayOfNulls(size)
        }
    }
}