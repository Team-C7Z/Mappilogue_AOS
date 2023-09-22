package com.c7z.mappilogue_aos.data.remote.response

import com.c7z.mappilogue_aos.presentation.util.BaseResponse
import com.google.android.datatransport.cct.StringMerger

data class ResponseUserProfileData (
    val result : ResultUserProfileData
) : BaseResponse() {
    data class ResultUserProfileData(
        val id : Int,
        val nickname : String,
        val email : String,
        var profileImageUrl : String,
        val snsType : String
    )
}