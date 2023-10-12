package com.c7z.mappilogue_aos.domain.source

import com.c7z.mappilogue_aos.data.remote.request.RequestModifyUserNickname
import com.c7z.mappilogue_aos.data.remote.request.RequestSignOut
import com.c7z.mappilogue_aos.data.remote.response.ResponseUserProfileData

interface UserSource {

    suspend fun requestUserProfileData() : Result<ResponseUserProfileData.ResultUserProfileData>

    suspend fun requestModifyUserNickname(body : RequestModifyUserNickname) : Result<Int>

    suspend fun requestLogOut() : Result<Int>

    suspend fun requestSignOut(body : RequestSignOut) : Result<Int>
}