package com.c7z.mappilogue_aos.data.remote.service

import com.c7z.mappilogue_aos.data.remote.response.ResponseNotificationSettingData
import com.c7z.mappilogue_aos.presentation.util.BaseResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT

interface NotificationService {

    @GET("/api/v1/user-profiles/alarm-settings")
    suspend fun requestNotificationSettingData() : Response<ResponseNotificationSettingData>

    @PUT("/api/v1/user-profiles/alarm-settings")
    suspend fun requestModifyNotificationSettingData(
        @Body body : ResponseNotificationSettingData.ResultNotificationSettingData
    ) : Response<BaseResponse>
}