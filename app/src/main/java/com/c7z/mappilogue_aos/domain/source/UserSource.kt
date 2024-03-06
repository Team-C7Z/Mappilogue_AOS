package com.c7z.mappilogue_aos.domain.source

import com.c7z.mappilogue_aos.data.remote.request.RequestAutoSignIn
import com.c7z.mappilogue_aos.data.remote.request.RequestModifyUserNickname
import com.c7z.mappilogue_aos.data.remote.request.RequestRefreshToken
import com.c7z.mappilogue_aos.data.remote.request.RequestSignIn
import com.c7z.mappilogue_aos.data.remote.request.RequestSignOut
import com.c7z.mappilogue_aos.data.remote.response.ResponseSignIn
import com.c7z.mappilogue_aos.data.remote.response.ResponseUserProfileData
import com.c7z.mappilogue_aos.presentation.util.BaseResponse
import okhttp3.MultipartBody
import retrofit2.http.Body

interface UserSource {

    suspend fun requestUserProfileData() : Result<ResponseUserProfileData.ResultUserProfileData>

    suspend fun requestModifyUserNickname(body : RequestModifyUserNickname) : Result<Int>

    suspend fun requestLogOut() : Result<Int>

    suspend fun requestSignOut(body : RequestSignOut) : Result<Int>

    suspend fun requestRefreshToken(body : RequestRefreshToken) : Result<ResponseSignIn.ResultSignIn>

    suspend fun requestAutoSignIn(body : RequestAutoSignIn) : Result<Int>

    suspend fun requestModifyUserProfileImage(image : MultipartBody.Part) : Result<Int>
}