package com.c7z.mappilogue_aos.data.remote.source

import com.c7z.mappilogue_aos.data.remote.request.RequestSignIn
import com.c7z.mappilogue_aos.data.remote.response.ResponseSignIn
import com.c7z.mappilogue_aos.data.remote.service.SignInService
import com.c7z.mappilogue_aos.domain.source.SignInSource
import com.c7z.mappilogue_aos.presentation.util.ErrorConverter.convertAndGetCode
import javax.inject.Inject

class SignInRemoteSource @Inject constructor(private val service : SignInService): SignInSource {
    override suspend fun requestSignIn(body: RequestSignIn): Result<ResponseSignIn.ResultSignIn> {
        val res = service.requestSignIn(body)
        return when(res.code()) {
            in 200..399 -> Result.success(res.body()!!.result)
            else -> Result.failure(IllegalArgumentException(res.errorBody()?.convertAndGetCode().toString()))
        }
    }
}