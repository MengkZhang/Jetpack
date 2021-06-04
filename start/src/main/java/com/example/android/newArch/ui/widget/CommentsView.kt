package com.example.android.newArch.ui.widget

import android.content.Context
import android.text.Spannable
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.TextPaint
import android.text.style.ClickableSpan
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.example.android.newArch.bean.Comment
import com.example.android.newArch.bean.Sender

/**
 * Desc CommentsView自定义评论view
 *
 * @author zhangxiaolin
 * Date 2020/9/17
 */
class CommentsView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {
    private val mContext: Context
    private var mData: List<Comment>? = null

    /**
     * 设置评论列表信息
     *
     * @param list
     */
    fun setList(list: List<Comment>?) {
        mData = list
    }

    fun notifyDataSetChanged() {
        removeAllViews()
        if (mData == null || mData!!.isEmpty()) {
            return
        }
        val layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
        layoutParams.setMargins(0, 10, 0, 10)
        for (i in mData!!.indices) {
            val view = getView(i)
            addView(view, i, layoutParams)
        }
    }

    private fun getView(position: Int): View {
        val (content, replyUser) = mData!![position]
        val textView = TextView(mContext)
        textView.textSize = 15f
        textView.setTextColor(-0x979798)
        val builder = SpannableStringBuilder()
        if (replyUser != null) {
            val name = replyUser.username
            builder.append(setClickableSpan(name, replyUser))
        }
        builder.append(" : ")
        builder.append(setClickableSpanContent(content, position))
        textView.text = builder
        return textView
    }

    /**
     * 设置评论内容点击事件
     */
    private fun setClickableSpanContent(item: String?, position: Int): SpannableString {
        val string = SpannableString(item)
        val span: ClickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {}
            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                // 设置显示的内容文本颜色
                ds.color = -0x979798
                ds.isUnderlineText = false
            }
        }
        string.setSpan(span, 0, string.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        return string
    }

    /**
     * 设置评论用户名字点击事件
     */
    private fun setClickableSpan(item: String?, bean: Sender?): SpannableString {
        val string = SpannableString(item)
        val span: ClickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {}
            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                // 设置显示的用户名文本颜色
                ds.color = -0xc78234
                ds.isUnderlineText = false
            }
        }
        string.setSpan(span, 0, string.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        return string
    }

    init {
        orientation = VERTICAL
        mContext = context
    }
}