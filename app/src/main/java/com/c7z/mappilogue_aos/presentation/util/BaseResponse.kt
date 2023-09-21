package com.c7z.mappilogue_aos.presentation.util

import com.google.gson.annotations.SerializedName

open class BaseResponse(
    @SerializedName("isSuccess") val isSuccess: Boolean = false,
    @SerializedName("statusCode") val code: Int = 0,
    @SerializedName("message") val message: String? = null
)
