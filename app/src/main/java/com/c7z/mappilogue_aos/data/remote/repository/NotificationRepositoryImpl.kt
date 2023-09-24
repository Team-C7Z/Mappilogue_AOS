package com.c7z.mappilogue_aos.data.remote.repository

import com.c7z.mappilogue_aos.data.remote.response.ResponseNotificationSettingData
import com.c7z.mappilogue_aos.domain.repository.NotificationRepository
import com.c7z.mappilogue_aos.domain.source.NotificationSource
import javax.inject.Inject

class NotificationRepositoryImpl @Inject constructor(private val source : NotificationSource): NotificationRepository {
    override suspend fun requestNotificationData(): Result<ResponseNotificationSettingData.ResultNotificationSettingData> {
        return source.requestNotificationData()
    }

    override suspend fun requestModifyNotificationData(body: ResponseNotificationSettingData.ResultNotificationSettingData): Result<Int> {
        return source.requestModifyNotificationData(body)
    }
}