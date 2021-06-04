package com.example.android.newArch.utils

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.load.resource.bitmap.RoundedCorners


/**
 * Desc GlideUtil
 *
 * @author zhangxiaolin
 * Date 2020/9/16
 */
object GlideUtil {
    /**
     * 加载圆角图片
     *
     * @param context   Context
     * @param imageUrl  url
     * @param imageView ImageView
     * @param placeId   缺省图
     */
    fun loadRoundedCorner(
        context: Context?,
        imageUrl: String?,
        imageView: ImageView?,
        placeId: Int
    ) {
        //设置图片圆角角度
        //通过RequestOptions扩展功能,override:采样率,因为ImageView就这么大,可以压缩图片,降低内存消耗
        val options =
            RequestOptions.bitmapTransform(RoundedCorners(35)).placeholder(placeId).error(placeId)
        Glide.with(context!!).load(imageUrl).apply(options.diskCacheStrategy(DiskCacheStrategy.ALL))
            .into(
                imageView!!
            )
    }

    /**
     * 加载图片
     *
     * @param context   Context
     * @param imageUrl  url
     * @param imageView ImageView
     * @param placeId   缺省图
     */
    fun load(context: Context?, imageUrl: String?, imageView: ImageView?, placeId: Int) {
        Glide.with(context!!).load(imageUrl)
            .apply(getPlaceErrorCenter(placeId).diskCacheStrategy(DiskCacheStrategy.ALL)).into(
                imageView!!
            )
    }

    private fun getPlaceErrorCenter(errorResId: Int): RequestOptions {
        return RequestOptions().placeholder(errorResId).error(errorResId).centerCrop()
    }
}