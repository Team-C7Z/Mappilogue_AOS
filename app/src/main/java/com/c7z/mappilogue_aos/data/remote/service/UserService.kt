package com.c7z.mappilogue_aos.data.remote.service

import com.c7z.mappilogue_aos.data.remote.request.RequestAutoSignIn
import com.c7z.mappilogue_aos.data.remote.request.RequestModifyUserNickname
import com.c7z.mappilogue_aos.data.remote.request.RequestRefreshToken
import com.c7z.mappilogue_aos.data.remote.request.RequestSignOut
import com.c7z.mappilogue_aos.data.remote.response.ResponseSignIn
import com.c7z.mappilogue_aos.data.remote.response.ResponseUserProfileData
import com.c7z.mappilogue_aos.presentation.util.BaseResponse
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Part

interface UserService {
    @GET("/api/v1/user-profiles")
    suspend fun requestUserProfileData() : Response<ResponseUserProfileData>

    @PATCH("/api/v1/user-profiles/nicknames")
    suspend fun requestModifyUserNickname(
        @Body body : RequestModifyUserNickname
    ) : Response<BaseResponse>

    @POST("/api/v1/users/logout")
    suspend fun requestLogOut() : Response<BaseResponse>

    @POST("/api/v1/users/withdrawal")
    suspend fun requestSignOut(
        @Body body : RequestSignOut
    ) : Response<BaseResponse>

    @POST("/api/v1/users/token-refresh")
    suspend fun requestRefreshToken(
        @Body body : RequestRefreshToken
    ) : Response<ResponseSignIn>

    @POST("/api/v1/users/auto-login")
    suspend fun requestAutoSignIn(
        @Body body : RequestAutoSignIn
    ) : Response<BaseResponse>

    @Multipart
    @JvmSuppressWildcards
    @PATCH("/api/v1/user-profiles/images")
    suspend fun requestModifyUserProfileImage(
        @Part image : MultipartBody.Part
    ) : Response<BaseResponse>

}