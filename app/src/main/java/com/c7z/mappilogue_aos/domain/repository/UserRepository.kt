package com.c7z.mappilogue_aos.domain.repository

import com.c7z.mappilogue_aos.data.remote.request.RequestAutoSignIn
import com.c7z.mappilogue_aos.data.remote.request.RequestModifyUserNickname
import com.c7z.mappilogue_aos.data.remote.request.RequestRefreshToken
import com.c7z.mappilogue_aos.data.remote.request.RequestSignIn
import com.c7z.mappilogue_aos.data.remote.request.RequestSignOut
import com.c7z.mappilogue_aos.data.remote.response.ResponseSignIn
import com.c7z.mappilogue_aos.data.remote.response.ResponseUserProfileData
import okhttp3.MultipartBody

interface UserRepository {

    suspend fun requestUserProfileData() : Result<ResponseUserProfileData.ResultUserProfileData>

    suspend fun requestModifyUserNickname(body : RequestModifyUserNickname) : Result<Int>

    suspend fun requestLogOut() : Result<Int>

    suspend fun requestSignOut(body : RequestSignOut) : Result<Int>

    suspend fun requestRefreshToken() : Result<ResponseSignIn.ResultSignIn>

    suspend fun requestAutoSignIn() : Result<Int>

    suspend fun requestModifyUserProfileImage(image : MultipartBody.Part) : Result<Int>
}