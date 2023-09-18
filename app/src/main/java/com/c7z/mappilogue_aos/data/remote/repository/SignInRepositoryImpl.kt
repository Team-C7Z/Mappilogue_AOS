package com.c7z.mappilogue_aos.data.remote.repository

import com.c7z.mappilogue_aos.data.remote.request.RequestSignIn
import com.c7z.mappilogue_aos.data.remote.response.ResponseSignIn
import com.c7z.mappilogue_aos.domain.repository.SignInRepository
import com.c7z.mappilogue_aos.domain.source.SharedPreferenceSource
import com.c7z.mappilogue_aos.domain.source.SignInSource
import javax.inject.Inject

class SignInRepositoryImpl @Inject constructor(
    private val source: SignInSource,
    private val localSource: SharedPreferenceSource
) : SignInRepository {
    override suspend fun requestSignIn(body: RequestSignIn): Result<ResponseSignIn.ResultSignIn> {
        return source.requestSignIn(body)
    }

    override suspend fun saveSignInData(access: String, refresh: String) {
        localSource.saveSignInData(access, refresh)
    }
}