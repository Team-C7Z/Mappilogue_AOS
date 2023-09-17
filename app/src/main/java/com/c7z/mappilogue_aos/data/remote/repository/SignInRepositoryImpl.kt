package com.c7z.mappilogue_aos.data.remote.repository

import com.c7z.mappilogue_aos.data.remote.request.RequestSignIn
import com.c7z.mappilogue_aos.data.remote.response.ResponseSignIn
import com.c7z.mappilogue_aos.domain.repository.SignInRepository
import com.c7z.mappilogue_aos.domain.source.SignInSource
import javax.inject.Inject

class SignInRepositoryImpl @Inject constructor(private val source : SignInSource): SignInRepository {
    override suspend fun requestSignIn(body: RequestSignIn): Result<ResponseSignIn.ResultSignIn> {
        return source.requestSignIn(body)
    }
}