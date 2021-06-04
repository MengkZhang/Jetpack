package com.example.android.newArch.bean

import com.google.gson.annotations.SerializedName

data class MomentBean(
    val comments: List<Comment>?,
    val content: String?,
    val images: List<Image>?,
    @SerializedName(value = "error", alternate = ["unknown error"])
    var error: String? = null,
    val sender: Sender?
)

data class Comment(
    val content: String?,
    val sender: Sender?
)

data class Image(
    val url: String?
)

data class Sender(
    val avatar: String?,
    val nick: String?,
    val username: String?
)

