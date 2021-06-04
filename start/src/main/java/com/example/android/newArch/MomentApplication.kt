package com.example.android.newArch

import android.app.Application
import com.example.android.newArch.utils.GlideImageLoader
import com.lzy.ninegrid.NineGridView

class MomentApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initNineGridView()
    }

    private fun initNineGridView() {
        NineGridView.setImageLoader(GlideImageLoader())
    }
}