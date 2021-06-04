package com.example.android.newArch.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.newArch.bean.DataBaseBean
import com.example.android.newArch.bean.MomentBean
import com.example.android.newArch.bean.UserInfoBean
import com.example.android.newArch.repository.MomentRepository
import com.example.android.newArch.utils.LogUtil
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MomentViewModel internal constructor(
    private val momentRepository: MomentRepository
) : ViewModel() {

    private var offset: Int = 0

    private val _snackBar = MutableLiveData<String?>()
    val snackBar: LiveData<String?> get() = _snackBar

    private val _spinner = MutableLiveData(false)
    val spinner: LiveData<Boolean> get() = _spinner

    private val _userInfo = MutableLiveData<UserInfoBean>()
    val userInfo: LiveData<UserInfoBean> = _userInfo

    private val _list = MutableLiveData<List<MomentBean>>()
    val moment: LiveData<List<MomentBean>> = _list

    fun getOffset(): Int = offset

    fun fetchNetData() {
        offset = 0

        launchDataLoad {
            val news = momentRepository.getAllData()
            news?.let {
                _list.value = it
            }
        }

        launchDataLoad {
            val userInfo = momentRepository.getUserInfo()
            userInfo?.let {
                _userInfo.value = it
            }
        }

    }


    /**
     * 加载更多 -- 从数据库读数据
     */
    fun loadMore(): LiveData<List<DataBaseBean>>? {
        offset += 5
        return momentRepository.queryData(offset)
    }

    fun onSnackBarShown() {
        _snackBar.value = null
    }

    /**
     * 加载数据的帮助函数
     */
    private fun launchDataLoad(block: suspend () -> Unit): Job {
        return viewModelScope.launch {
            try {
                _spinner.value = true
                block()
            } catch (error: Throwable) {
                LogUtil.printLog("e=${error.message}")
                _snackBar.value = error.message
            } finally {
                _spinner.value = false
            }
        }
    }

}