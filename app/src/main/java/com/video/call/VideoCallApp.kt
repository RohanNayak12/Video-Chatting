package com.video.call

import android.app.Application
import com.video.call.di.appModule
import io.getstream.video.android.core.StreamVideo
import io.getstream.video.android.core.StreamVideoBuilder
import io.getstream.video.android.model.User
import io.getstream.video.android.model.UserType
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class VideoCallApp :Application() {
    private var currentName: String? = null
    var client : StreamVideo? = null

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@VideoCallApp)
            modules(appModule)
        }
    }

    fun initVideoClient(userName:String){
        if(client==null || userName!=currentName){
            StreamVideo.removeClient()
            currentName=userName
            client=StreamVideoBuilder(
                context = this,
                apiKey = apiKey,
                token = token,
                user = User(
                    id = userName,
                    name = userName,
                    type = UserType.Guest
                )
            ).build()
        }
    }
}