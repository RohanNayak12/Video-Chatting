package com.video.call.connect

data class ConnectState(
    val name:String="",
    val isConnected:Boolean=false,
    val errorMsg:String?=null
)
