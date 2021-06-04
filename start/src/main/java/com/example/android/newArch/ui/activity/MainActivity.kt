package com.example.android.newArch.ui.activity

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android.newArch.R
import com.example.android.newArch.bean.MomentBean
import com.example.android.newArch.bean.UserInfoBean
import com.example.android.newArch.ui.adpter.MomentAdapter
import com.example.android.newArch.utils.GlideUtil
import com.example.android.newArch.utils.GsonUtil
import com.example.android.newArch.utils.Injector
import com.example.android.newArch.viewmodel.MomentViewModel
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.top_view_layout.*


class MainActivity : AppCompatActivity() {

    private val viewModel: MomentViewModel by viewModels {
        Injector.provideNewsViewModelFactory(this)
    }

    private var list: MutableList<MomentBean> = ArrayList()

    private var momentAdapter: MomentAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
        initData()
        initObserver()
        initListener()
    }

    private fun initData() {
        viewModel.fetchNetData()
    }

    private fun initObserver() {

        viewModel.userInfo.observe(this, { userInfo ->
            setUserInfo(userInfo)
        })

        viewModel.moment.observe(this, { newsList ->
            swipeRefreshLayout.isRefreshing = false
            list.clear()
            list.addAll(newsList)
            momentAdapter?.setNewData(list)
        })

        viewModel.spinner.observe(this, { show ->
            spinner.visibility = if (show) View.VISIBLE else View.GONE
        })

        viewModel.snackBar.observe(this, { text ->
            text?.let { t ->
                Snackbar.make(root, t, Snackbar.LENGTH_SHORT).show()
                viewModel.onSnackBarShown()
            }
        })

    }

    private fun setUserInfo(userInfo: UserInfoBean?) {
        tv_self_name.text = userInfo?.username
        GlideUtil.load(this, userInfo?.profileImage, iv_user_bg, R.mipmap.default_place_img);
        GlideUtil.load(this, userInfo?.avatar, iv_self_head, R.mipmap.icon_default_small_head);
    }

    private fun initListener() {
        offsetChangedEvent()
        refreshEvent()
        loadMoreEvent()
    }

    private fun refreshEvent() {
        swipeRefreshLayout.setOnRefreshListener {
            swipeRefreshLayout.isRefreshing = true
            viewModel.fetchNetData()
        }
    }

    private fun offsetChangedEvent() {
        mAppBarLayout.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { _, verticalOffset ->
            if (verticalOffset >= 0) {
                swipeRefreshLayout.isEnabled = true
            } else {
                if (!swipeRefreshLayout.isRefreshing) {
                    swipeRefreshLayout.isEnabled = false
                }
            }
        })
    }

    private fun loadMoreEvent() {
        momentAdapter?.setOnLoadMoreListener({
            viewModel.loadMore()?.observe(this, {
                momentAdapter?.loadMoreComplete()
                val momentList = GsonUtil.getMomentList(it)
                if (momentList.isEmpty()) {
                    momentAdapter?.loadMoreEnd()
                } else {
                    list.addAll(momentList)
                    momentAdapter?.notifyItemChanged(viewModel.getOffset())
                }
            })

        }, recyclerView)
    }

    private fun initView() {
        swipeRefreshLayout.setColorSchemeResources(R.color.sunflower_green_700)
        recyclerView.layoutManager = LinearLayoutManager(this)
        momentAdapter = MomentAdapter(R.layout.item_moment, list)
        recyclerView.adapter = momentAdapter
    }

}
