package com.example.android.newArch.utils

import android.content.Context
import com.example.android.newArch.apiservice.NetWorkService
import com.example.android.newArch.repository.MomentRepository

interface ViewModelFactoryProvider {
    fun provideNewsViewModelFactory(context: Context): MomentViewModelFactory
}

val Injector: ViewModelFactoryProvider get() = currentInjector

private object DefaultViewModelProvider : ViewModelFactoryProvider {

    override fun provideNewsViewModelFactory(context: Context): MomentViewModelFactory {
        val repository = getNewsRepository(context)
        return MomentViewModelFactory(repository)
    }

    private fun getNewsRepository(context: Context): MomentRepository {
        return MomentRepository.getInstance(
            momentDao(context),
            newsService()
        )
    }

    private fun newsService() = NetWorkService()

    private fun momentDao(context: Context) =
        AppDatabase.getInstance(context.applicationContext).momentDao()

}

@Volatile
private var currentInjector: ViewModelFactoryProvider = DefaultViewModelProvider