package com.c7z.mappilogue_aos.data.remote.source

import com.c7z.mappilogue_aos.data.remote.request.RequestModifyUserNickname
import com.c7z.mappilogue_aos.data.remote.response.ResponseUserProfileData
import com.c7z.mappilogue_aos.data.remote.service.UserService
import com.c7z.mappilogue_aos.domain.source.UserSource
import com.c7z.mappilogue_aos.presentation.util.ErrorConverter.convertAndGetCode
import javax.inject.Inject

class UserRemoteSource @Inject constructor(private val service : UserService): UserSource {
    override suspend fun requestUserProfileData(): Result<ResponseUserProfileData.ResultUserProfileData> {
        val res = service.requestUserProfileData()
        return when(res.code()) {
            in 200..399 -> Result.success(res.body()!!.result)
            else -> Result.failure(IllegalArgumentException(res.errorBody()?.convertAndGetCode().toString()))
        }
    }

    override suspend fun requestModifyUserNickname(body: RequestModifyUserNickname): Result<Int> {
        val res = service.requestModifyUserNickname(body)
        return when(res.code()) {
            in 200..399 -> Result.success(res.code())
            else -> Result.failure(IllegalArgumentException(res.errorBody()?.convertAndGetCode().toString()))
        }
    }
}