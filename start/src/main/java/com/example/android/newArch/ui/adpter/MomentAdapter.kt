package com.example.android.newArch.ui.adpter

import android.view.View
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.android.newArch.R
import com.example.android.newArch.bean.Image
import com.example.android.newArch.bean.MomentBean
import com.example.android.newArch.ui.widget.CommentsView
import com.example.android.newArch.utils.GlideUtil
import com.lzy.ninegrid.ImageInfo
import com.lzy.ninegrid.NineGridView
import com.lzy.ninegrid.preview.NineGridViewClickAdapter

class MomentAdapter(
    layoutId: Int,
    data: MutableList<MomentBean>?
) : BaseQuickAdapter<MomentBean, BaseViewHolder>(layoutId, data) {

    private fun getImageInfo(bean: Image): ImageInfo {
        val imageInfo = ImageInfo()
        imageInfo.setBigImageUrl(bean.url)
        imageInfo.setThumbnailUrl(bean.url)
        return imageInfo
    }

    override fun convert(holder: BaseViewHolder?, data: MomentBean?) {
        GlideUtil.loadRoundedCorner(
            mContext,
            data?.sender?.avatar,
            holder?.getView(R.id.iv_head),
            R.mipmap.icon_default_small_head
        )

        holder?.getView<TextView>(R.id.tv_name)?.text = data?.sender?.username + holder?.position
        holder?.getView<TextView>(R.id.tv_desc)?.text = data?.content

        //设置评论数据
        val comments = data?.comments
        val commentsView: CommentsView? = holder?.getView(R.id.commentsView)

        comments?.let {
            commentsView?.visibility = View.VISIBLE
            commentsView?.setList(it)
            commentsView?.notifyDataSetChanged()
        } ?: let {
            commentsView?.visibility = View.GONE
        }

        //设置九宫格图片数据
        val nineGridView: NineGridView? = holder?.getView(R.id.nineGridView)
        val images = data?.images

        images?.let { img ->
            nineGridView?.visibility = View.VISIBLE
            val imageInfoList = ArrayList<ImageInfo>()
            img.forEach {
                imageInfoList.add(getImageInfo(it))
            }
            nineGridView?.setAdapter(NineGridViewClickAdapter(mContext, imageInfoList))
        } ?: let {
            nineGridView?.visibility = View.GONE
        }
    }

}