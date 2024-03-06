package com.c7z.mappilogue_aos.data.remote.source

import com.c7z.mappilogue_aos.data.remote.request.RequestAutoSignIn
import com.c7z.mappilogue_aos.data.remote.request.RequestModifyUserNickname
import com.c7z.mappilogue_aos.data.remote.request.RequestRefreshToken
import com.c7z.mappilogue_aos.data.remote.request.RequestSignIn
import com.c7z.mappilogue_aos.data.remote.request.RequestSignOut
import com.c7z.mappilogue_aos.data.remote.response.ResponseSignIn
import com.c7z.mappilogue_aos.data.remote.response.ResponseUserProfileData
import com.c7z.mappilogue_aos.data.remote.service.UserService
import com.c7z.mappilogue_aos.domain.source.UserSource
import com.c7z.mappilogue_aos.presentation.util.ErrorConverter.convertAndGetCode
import okhttp3.MultipartBody
import javax.inject.Inject

class UserRemoteSource @Inject constructor(private val service : UserService): UserSource {
    override suspend fun requestUserProfileData(): Result<ResponseUserProfileData.ResultUserProfileData> {
        val res = service.requestUserProfileData()
        return when(res.code()) {
            in 200..399 -> Result.success(res.body()!!.result)
            else -> Result.failure(IllegalArgumentException(res.errorBody()?.string()))
        }
    }

    override suspend fun requestModifyUserNickname(body: RequestModifyUserNickname): Result<Int> {
        val res = service.requestModifyUserNickname(body)
        return when(res.code()) {
            in 200..399 -> Result.success(res.code())
            else -> Result.failure(IllegalArgumentException(res.errorBody()?.string()))
        }
    }

    override suspend fun requestLogOut(): Result<Int> {
        val res = service.requestLogOut()
        return when(res.code()) {
            in 200..399 -> Result.success(res.code())
            else -> Result.failure(IllegalArgumentException(res.errorBody()?.string()))
        }
    }

    override suspend fun requestSignOut(body: RequestSignOut): Result<Int> {
        val res = service.requestSignOut(body)
        return when(res.code()) {
            in 200..399 -> Result.success(res.code())
            else -> Result.failure(IllegalArgumentException(res.errorBody()?.string()))
        }
    }

    override suspend fun requestRefreshToken(body: RequestRefreshToken): Result<ResponseSignIn.ResultSignIn> {
        val res = service.requestRefreshToken(body)
        return when(res.code()) {
            in 200..399 -> Result.success(res.body()!!.result)
            else -> Result.failure(IllegalArgumentException(res.errorBody()?.string()))
        }
    }

    override suspend fun requestAutoSignIn(body: RequestAutoSignIn): Result<Int> {
        val res = service.requestAutoSignIn(body)
        return when(res.code()) {
            in 200..399 -> Result.success(res.code())
            else -> Result.failure(IllegalArgumentException(res.errorBody()?.string()))
        }
    }

    override suspend fun requestModifyUserProfileImage(image: MultipartBody.Part): Result<Int> {
        val res = service.requestModifyUserProfileImage(image)
        return when(res.code()) {
            in 200..399 -> Result.success(res.code())
            else -> Result.failure(IllegalArgumentException(res.errorBody()?.string()))
        }
    }
}