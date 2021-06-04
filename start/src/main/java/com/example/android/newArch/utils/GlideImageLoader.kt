package com.example.android.newArch.utils

import android.content.Context
import android.graphics.Bitmap
import android.widget.ImageView
import com.example.android.newArch.R
import com.example.android.newArch.utils.GlideUtil.load
import com.lzy.ninegrid.NineGridView.ImageLoader

/**
 * Desc
 *
 * @author zhangxiaolin
 * Date 2020/9/16
 */
class GlideImageLoader : ImageLoader {
    override fun onDisplayImage(context: Context, imageView: ImageView, url: String) {
        load(context, url, imageView, R.mipmap.default_place_img)
    }

    override fun getCacheImage(url: String): Bitmap? {
        return null
    }
}