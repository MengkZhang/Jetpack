package com.example.android.newArch.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.android.newArch.repository.MomentRepository
import com.example.android.newArch.viewmodel.MomentViewModel

class MomentViewModelFactory(
    private val repository: MomentRepository
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>) = MomentViewModel(repository) as T

}