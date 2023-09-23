package com.c7z.mappilogue_aos.domain.repository

import com.c7z.mappilogue_aos.data.remote.request.RequestSignIn
import com.c7z.mappilogue_aos.data.remote.response.ResponseSignIn

interface SignInRepository {

    suspend fun requestSignIn(body : RequestSignIn) : Result<ResponseSignIn.ResultSignIn>

    suspend fun saveSignInData(access : String, refresh : String)

    suspend fun deleteUserData()
}