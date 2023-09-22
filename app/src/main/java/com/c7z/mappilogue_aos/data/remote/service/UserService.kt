package com.c7z.mappilogue_aos.data.remote.service

import com.c7z.mappilogue_aos.data.remote.request.RequestModifyUserNickname
import com.c7z.mappilogue_aos.data.remote.response.ResponseUserProfileData
import com.c7z.mappilogue_aos.presentation.util.BaseResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH

interface UserService {
    @GET("/api/v1/users/profile")
    suspend fun requestUserProfileData() : Response<ResponseUserProfileData>

    @PATCH("/api/v1/users/nickname")
    suspend fun requestModifyUserNickname(
        @Body body : RequestModifyUserNickname
    ) : Response<BaseResponse>
}