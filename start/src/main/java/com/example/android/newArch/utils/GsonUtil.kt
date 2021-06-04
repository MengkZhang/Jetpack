package com.example.android.newArch.utils

import com.example.android.newArch.bean.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object GsonUtil {
    fun getMomentList(value: List<DataBaseBean>?): List<MomentBean> {
        val list = ArrayList<MomentBean>()
        val gson = Gson()
        value?.let { dataBeanList ->
            dataBeanList.forEach { dataBaseBean ->

                var comment: List<Comment>? = null
                var image: List<Image>? = null
                var sender: Sender? = null

                dataBaseBean.comments?.let { commentStr ->
                    if ("[]" != commentStr) {
                        comment =
                            gson.fromJson(
                                commentStr,
                                object : TypeToken<ArrayList<Comment>>() {}.type
                            )
                    }

                }

                dataBaseBean.images?.let { imgStr ->
                    if ("[]" != imgStr) {
                        image = gson.fromJson(
                            imgStr,
                            object : TypeToken<ArrayList<Image>>() {}.type
                        )
                    }
                }

                dataBaseBean.sender?.let { senderStr ->
                    sender = gson.fromJson(senderStr, Sender::class.java)
                }

                list.add(
                    MomentBean(comment, dataBaseBean.content, image, null, sender)
                )
            }
        }

        return list
    }
}