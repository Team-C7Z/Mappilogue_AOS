package com.c7z.mappilogue_aos.data.remote.source

import com.c7z.mappilogue_aos.data.remote.response.ResponseNotificationSettingData
import com.c7z.mappilogue_aos.data.remote.service.NotificationService
import com.c7z.mappilogue_aos.domain.source.NotificationSource
import com.c7z.mappilogue_aos.presentation.util.ErrorConverter.convertAndGetCode
import javax.inject.Inject

class NotificationRemoteSource @Inject constructor(private val service : NotificationService): NotificationSource {
    override suspend fun requestNotificationData(): Result<ResponseNotificationSettingData.ResultNotificationSettingData> {
        val res = service.requestNotificationSettingData()
        return when(res.code()) {
            in 200..399 -> Result.success(res.body()!!.result)
            else -> Result.failure(IllegalArgumentException(res.errorBody()?.string()))
        }
    }

    override suspend fun requestModifyNotificationData(body: ResponseNotificationSettingData.ResultNotificationSettingData): Result<Int> {
        val res = service.requestModifyNotificationSettingData(body)
        return when(res.code()) {
            in 200..399 -> Result.success(res.code())
            else -> Result.failure(IllegalArgumentException(res.errorBody()?.string()))
        }
    }
}