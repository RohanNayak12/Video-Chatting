package com.video.call.video

sealed interface VideoActions {
    data object OnDisconnectClick:VideoActions
    data object JoinCall: VideoActions
}