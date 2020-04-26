package com.ksayker.reddit.ui.di

import android.content.Context
import com.ksayker.reddit.data.PostRepository
import com.ksayker.reddit.data.PostRepositoryImpl
import com.ksayker.reddit.ui.screen.postlist.PostListViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

object AppModule {
    fun initAppModule(context: Context) {

        val repositoryModule = module {
            single<PostRepository> { PostRepositoryImpl() }
        }

        val appModule = module {
            viewModel { PostListViewModel(get()) }
        }

        startKoin {
            androidLogger()
            androidContext(context.applicationContext)
            modules(repositoryModule, appModule)
        }
    }
}