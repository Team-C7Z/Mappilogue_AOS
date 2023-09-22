package com.c7z.mappilogue_aos.data.remote.repository

import com.c7z.mappilogue_aos.data.remote.request.RequestModifyUserNickname
import com.c7z.mappilogue_aos.data.remote.response.ResponseUserProfileData
import com.c7z.mappilogue_aos.domain.repository.UserRepository
import com.c7z.mappilogue_aos.domain.source.UserSource
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(private val source : UserSource): UserRepository {
    override suspend fun requestUserProfileData(): Result<ResponseUserProfileData.ResultUserProfileData> {
        return source.requestUserProfileData()
    }

    override suspend fun requestModifyUserNickname(body: RequestModifyUserNickname): Result<Int> {
        return source.requestModifyUserNickname(body)
    }
}