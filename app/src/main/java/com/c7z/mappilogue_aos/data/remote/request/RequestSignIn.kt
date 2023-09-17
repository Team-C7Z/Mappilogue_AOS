package com.c7z.mappilogue_aos.data.remote.request

data class RequestSignIn(
    val socialAccessToken : String,
    val socialVendor : String,
    val fcmToken : String,
    val isAlarmAccept : String
)