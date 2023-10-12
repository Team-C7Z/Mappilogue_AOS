package com.c7z.mappilogue_aos.domain.source

import com.c7z.mappilogue_aos.data.remote.response.ResponseNotificationSettingData

interface NotificationSource {

    suspend fun requestNotificationData() : Result<ResponseNotificationSettingData.ResultNotificationSettingData>

    suspend fun requestModifyNotificationData(body : ResponseNotificationSettingData.ResultNotificationSettingData) : Result<Int>
}