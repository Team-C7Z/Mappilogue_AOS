package com.c7z.mappilogue_aos.domain.repository

import com.c7z.mappilogue_aos.data.remote.response.ResponseNotificationSettingData

interface NotificationRepository {

    suspend fun requestNotificationData() : Result<ResponseNotificationSettingData.ResultNotificationSettingData>

    suspend fun requestModifyNotificationData(body : ResponseNotificationSettingData.ResultNotificationSettingData) : Result<Int>
}