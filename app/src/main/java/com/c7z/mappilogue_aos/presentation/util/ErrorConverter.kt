package com.c7z.mappilogue_aos.presentation.util

import android.util.Log
import com.google.gson.Gson
import okhttp3.ResponseBody

object ErrorConverter {

    fun String.convertAndGetCode() : BaseResponse {
        return Gson().fromJson(this, BaseResponse::class.java)
    }
}