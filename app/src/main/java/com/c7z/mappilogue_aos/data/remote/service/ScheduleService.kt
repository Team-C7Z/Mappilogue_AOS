package com.c7z.mappilogue_aos.data.remote.service

import com.c7z.mappilogue_aos.data.remote.request.RequestAddTodo
import com.c7z.mappilogue_aos.data.remote.response.ResponseCurrentMonthScheduleData
import com.c7z.mappilogue_aos.data.remote.response.ResponseDetailDateScheduleData
import com.c7z.mappilogue_aos.data.remote.response.ResponseScheduleDetailData
import com.c7z.mappilogue_aos.data.remote.response.ResponseTodoColor
import com.c7z.mappilogue_aos.presentation.util.BaseResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface ScheduleService {

    @GET("/api/v1/colors")
    suspend fun requestSchedulesColorData() : Response<ResponseTodoColor>

    @GET("/api/v1/schedules/calendars")
    suspend fun requestCurrentMonthScheduleData(
        @Query("year") year : Int,
        @Query("month") month : Int
    ) : Response<ResponseCurrentMonthScheduleData>

    @POST("/api/v1/schedules")
    suspend fun requestAddTodo(
        @Body body : RequestAddTodo
    ) : Response<BaseResponse>

    @GET("/api/v1/schedules/detail-by-date")
    suspend fun requestPostDetailDateSchedule(
        @Query("date") date : String
    ) : Response<ResponseDetailDateScheduleData>

    @DELETE("/api/v1/schedules/{scheduleId}")
    suspend fun requestDeleteSchedule(
        @Path("scheduleId") scheduleId : Int
    ) : Response<Int>

    @GET("/api/v1/schedules/detail-by-id")
    suspend fun requestGetDetailDateSchedule(
        @Query("scheduleId") scheduleId : Int
    ) : Response<ResponseScheduleDetailData>

    @PUT("/api/v1/schedules/{scheduleId}")
    suspend fun requestModifyDetailDateSchedule(
        @Path("scheduleId") scheduleId: Int,
        @Body body : RequestAddTodo
    ) : Response<BaseResponse>
}