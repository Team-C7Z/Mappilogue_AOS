package com.c7z.mappilogue_aos.data.remote.repository

import com.c7z.mappilogue_aos.data.remote.request.RequestAutoSignIn
import com.c7z.mappilogue_aos.data.remote.request.RequestModifyUserNickname
import com.c7z.mappilogue_aos.data.remote.request.RequestRefreshToken
import com.c7z.mappilogue_aos.data.remote.request.RequestSignOut
import com.c7z.mappilogue_aos.data.remote.response.ResponseSignIn
import com.c7z.mappilogue_aos.data.remote.response.ResponseUserProfileData
import com.c7z.mappilogue_aos.domain.repository.UserRepository
import com.c7z.mappilogue_aos.domain.source.SharedPreferenceSource
import com.c7z.mappilogue_aos.domain.source.UserSource
import okhttp3.MultipartBody
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(private val source : UserSource,
    private val localRepository : SharedPreferenceSource): UserRepository {
    override suspend fun requestUserProfileData(): Result<ResponseUserProfileData.ResultUserProfileData> {
        return source.requestUserProfileData()
    }

    override suspend fun requestModifyUserNickname(body: RequestModifyUserNickname): Result<Int> {
        return source.requestModifyUserNickname(body)
    }

    override suspend fun requestLogOut(): Result<Int> {
        return source.requestLogOut()
    }

    override suspend fun requestSignOut(body: RequestSignOut): Result<Int> {
        return source.requestSignOut(body)
    }

    override suspend fun requestRefreshToken(): Result<ResponseSignIn.ResultSignIn> {
        return source.requestRefreshToken(RequestRefreshToken(localRepository.requestRefreshToken()))
    }

    override suspend fun requestAutoSignIn(): Result<Int> {
        return source.requestAutoSignIn(RequestAutoSignIn(localRepository.requestAccessToken()))
    }

    override suspend fun requestModifyUserProfileImage(image: MultipartBody.Part): Result<Int> {
        return source.requestModifyUserProfileImage(image)
    }
}