package com.c7z.mappilogue_aos.data.remote.service

import com.c7z.mappilogue_aos.data.remote.request.RequestSignIn
import com.c7z.mappilogue_aos.data.remote.response.ResponseSignIn
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface SignInService {

    @POST("/api/v1/users/social-login")
    suspend fun requestSignIn(
        @Body body : RequestSignIn
    ) : Response<ResponseSignIn>
}