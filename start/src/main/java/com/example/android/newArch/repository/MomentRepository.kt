package com.example.android.newArch.repository

import com.example.android.newArch.apiservice.NetWorkService
import com.example.android.newArch.bean.DataBaseBean
import com.example.android.newArch.bean.MomentBean
import com.example.android.newArch.dao.MomentDao
import com.google.gson.Gson

class MomentRepository private constructor(
    private val momentDao: MomentDao,
    private val newsService: NetWorkService
) {

    fun queryData(offSet: Int) = momentDao.getSomeMoments(offSet)

    suspend fun getAllData(): List<MomentBean>? {
        val moments = newsService.getMoments()
        insertToTable(moments)
        moments?.let { momentList ->
            if (momentList.size > 5) {
                return momentList.subList(0, 5)
            }
        }
        return moments
    }

    private suspend fun insertToTable(moments: List<MomentBean>?) {
        val arrayList = ArrayList<DataBaseBean>()
        moments?.let { list ->
            val gson = Gson()
            for (index in list.indices) {
                arrayList.add(
                    DataBaseBean(
                        index + 1,
                        list[index].content,
                        gson.toJson(list[index].sender),
                        gson.toJson(list[index].comments),
                        gson.toJson(list[index].images),
                    )
                )
            }
        }

        momentDao.insertAll(arrayList)
    }

    suspend fun getUserInfo() = newsService.getUserInfo()

    companion object {
        @Volatile
        private var instance: MomentRepository? = null
        fun getInstance(momentDao: MomentDao, newsService: NetWorkService) =
            instance ?: synchronized(this) {
                instance ?: MomentRepository(momentDao, newsService).also { instance = it }
            }
    }

}