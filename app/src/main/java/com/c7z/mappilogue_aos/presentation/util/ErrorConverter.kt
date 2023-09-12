package com.c7z.mappilogue_aos.presentation.util

import okhttp3.ResponseBody

object ErrorConverter {

    fun ResponseBody.convertAndGetCode() : Int {
        return this.string().split(",")[1].split(":")[1].trim().toInt()
    }
}