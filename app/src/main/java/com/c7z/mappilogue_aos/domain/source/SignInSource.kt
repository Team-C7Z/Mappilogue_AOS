package com.c7z.mappilogue_aos.domain.source

import com.c7z.mappilogue_aos.data.remote.request.RequestSignIn
import com.c7z.mappilogue_aos.data.remote.response.ResponseSignIn

interface SignInSource {

    suspend fun requestSignIn(body : RequestSignIn) : Result<ResponseSignIn.ResultSignIn>
}