package com.c7z.mappilogue_aos.data.remote.service

import com.c7z.mappilogue_aos.data.remote.response.ResponseTodoColor
import retrofit2.Response
import retrofit2.http.GET

interface ScheduleService {

    @GET("/api/v1/schedules/colors")
    suspend fun requestSchedulesColorData() : Response<ResponseTodoColor>
}