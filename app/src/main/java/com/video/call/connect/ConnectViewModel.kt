package com.video.call.connect

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import com.video.call.VideoCallApp

class ConnectViewModel(
    private val app:Application
):AndroidViewModel(app) {
    var state by mutableStateOf(ConnectState())
        private set

    fun onAction(action: ConnectAction){
        when(action){
            ConnectAction.OnConnectClick -> {
                connectToRoom()
            }

            is ConnectAction.OnNameChange -> {
                state=state.copy(name = action.name)
            }
        }
    }

    private fun connectToRoom(){
        state=state.copy(errorMsg = null)
        if (state.name.isNullOrBlank()){
            state=state.copy(
                errorMsg = "User name can't be blank."
            )
        }
        //Video Connection
        (app as VideoCallApp).initVideoClient(state.name)
        state=state.copy(isConnected = true)
    }
}