package com.example.android.newArch.apiservice

import com.example.android.newArch.bean.MomentBean
import com.example.android.newArch.bean.UserInfoBean
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

/**
 * 网络请求Service
 */
class NetWorkService {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://thoughtworks-mobile-2018.herokuapp.com/")
        .client(OkHttpClient())
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val newsService = retrofit.create(MomentService::class.java)

    suspend fun getMoments(): List<MomentBean>? = withContext(Dispatchers.Default) {
        val moments: List<MomentBean>? = newsService.getMoments()
        moments
    }

    suspend fun getUserInfo(): UserInfoBean? = withContext(Dispatchers.Default) {
        val userInfo = newsService.getUserInfo()
        userInfo
    }


}

interface MomentService {
    /**
     * 请求列表
     */
    @GET("user/jsmith/tweets")
    suspend fun getMoments(): List<MomentBean>?

    /**
     * 请求用户信息
     */
    @GET("user/jsmith")
    suspend fun getUserInfo(): UserInfoBean?
}