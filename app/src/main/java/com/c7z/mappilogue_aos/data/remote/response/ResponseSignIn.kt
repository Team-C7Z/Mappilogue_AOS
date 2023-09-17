package com.c7z.mappilogue_aos.data.remote.response

import com.c7z.mappilogue_aos.presentation.util.BaseResponse

data class ResponseSignIn (
    val result : ResultSignIn
) : BaseResponse() {
    data class ResultSignIn(
        val logInUserId : Int,
        val type : String,
        val accessToken : String,
        val refreshToken : String
    )
}