package com.video.call.video

import android.os.Build
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import io.getstream.video.android.compose.permission.rememberCallPermissionsState
import io.getstream.video.android.compose.ui.components.call.activecall.CallContent
import io.getstream.video.android.compose.ui.components.call.controls.actions.DefaultOnCallActionHandler
import io.getstream.video.android.core.call.state.LeaveCall

@Composable
fun VideoCallScreen(
    state: VideoCallState,
    onAction: (VideoActions) -> Unit
){
    when{
        state.error != null -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = state.error, color = MaterialTheme.colorScheme.error)
            }
        }
        state.callState == CallState.JOINING -> {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally)
            {
                CircularProgressIndicator()
                Text(text = "Joining call...")
            }
        }
        else -> {
            val basePermissions = listOf(android.Manifest.permission.CAMERA,android.Manifest.permission.RECORD_AUDIO)
            val bluetoothConnect= if(Build.VERSION.SDK_INT >= 31){
                listOf(android.Manifest.permission.BLUETOOTH_CONNECT)
            }else{
                emptyList()

            }
            val notificationPermission= if(Build.VERSION.SDK_INT >= 33){
                listOf(android.Manifest.permission.POST_NOTIFICATIONS)
            }else{
                emptyList()

            }
            val context = LocalContext.current
            CallContent(
                call = state.call,
                modifier = Modifier.fillMaxSize(),
                permissions = rememberCallPermissionsState(
                    call = state.call,
                    permissions = basePermissions+bluetoothConnect+notificationPermission,
                    //onAllPermissionsGranted = {onAction(VideoActions.JoinCall)},
                    onPermissionsResult = {permissionsResult ->
                        if(permissionsResult.containsValue(false)){
                            Toast.makeText(context, "Missing permissions", Toast.LENGTH_LONG).show()
                        }
                        else{
                            onAction(VideoActions.JoinCall)
                        }
                    }
                ),
                onCallAction = {action ->
                    if(action ==LeaveCall){
                        onAction(VideoActions.OnDisconnectClick)
                    }
                    DefaultOnCallActionHandler.onCallAction(state.call,action)
                },
                onBackPressed = {onAction(VideoActions.OnDisconnectClick)}
            )
        }
    }
}