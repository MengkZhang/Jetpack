package com.example.android.newArch.bean

import com.google.gson.annotations.SerializedName

data class UserInfoBean(
    val avatar: String,
    val nick: String,
    @SerializedName("profile-image")
    val profileImage: String,
    val username: String
)