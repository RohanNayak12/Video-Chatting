package com.video.call.di

//import org.koin.core.module.dsl.viewModel
import com.video.call.VideoCallApp
import com.video.call.connect.ConnectViewModel
import com.video.call.video.VideoCallViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule= module {

    factory {
        val app=androidContext().applicationContext as VideoCallApp
        app.client
    }

    viewModel { VideoCallViewModel(get()) }
    viewModel { ConnectViewModel(get()) }
}
