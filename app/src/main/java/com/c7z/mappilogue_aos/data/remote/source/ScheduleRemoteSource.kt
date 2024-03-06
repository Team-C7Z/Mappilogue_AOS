package com.c7z.mappilogue_aos.data.remote.source

import com.c7z.mappilogue_aos.data.remote.request.RequestAddTodo
import com.c7z.mappilogue_aos.data.remote.response.ResponseCurrentMonthScheduleData
import com.c7z.mappilogue_aos.data.remote.response.ResponseDetailDateScheduleData
import com.c7z.mappilogue_aos.data.remote.response.ResponseScheduleDetailData
import com.c7z.mappilogue_aos.data.remote.response.ResponseTodoColor
import com.c7z.mappilogue_aos.data.remote.service.ScheduleService
import com.c7z.mappilogue_aos.domain.source.ScheduleSource
import com.c7z.mappilogue_aos.presentation.util.BaseResponse
import javax.inject.Inject

class ScheduleRemoteSource @Inject constructor(private val service : ScheduleService) : ScheduleSource{
    override suspend fun requestScheduleColorData(): Result<List<ResponseTodoColor.ResultTodoColor>> {
        val res = service.requestSchedulesColorData()
        return when(res.code()) {
            in 200..399 -> Result.success(res.body()!!.result)
            else -> Result.failure(IllegalArgumentException(res.errorBody()?.string()))
        }
    }

    override suspend fun requestCurrentMonthScheduleData(year : Int, month : Int): Result<List<ResponseCurrentMonthScheduleData.ResultCurrentMonthScheduleData>> {
        val res = service.requestCurrentMonthScheduleData(year, month)
        return when(res.code()) {
            in 200..399 -> Result.success(res.body()!!.result.calendarSchedules)
            else -> Result.failure(IllegalArgumentException(res.errorBody()?.string()))
        }
    }

    override suspend fun requestAddTodo(body: RequestAddTodo): Result<BaseResponse> {
        val res = service.requestAddTodo(body)
        return when(res.code()) {
            in 200..399 -> Result.success(res.body()!!)
            else -> Result.failure(IllegalArgumentException(res.errorBody()?.string()))
        }
    }

    override suspend fun requestPostDetailDateSchedule(date: String): Result<ResponseDetailDateScheduleData.ResultDetailDateScheduleData> {
        val res = service.requestPostDetailDateSchedule(date)
        return when(res.code()) {
            in 200..399 -> Result.success(res.body()!!.result)
            else -> Result.failure(IllegalArgumentException(res.errorBody()?.string()))
        }
    }

    override suspend fun requestDeleteSchedule(scheduleId: Int) : Result<Int> {
        val res = service.requestDeleteSchedule(scheduleId)
        return when(res.code()) {
            in 200..399 -> Result.success(res.code())
            else -> Result.failure(IllegalArgumentException(res.errorBody()?.string()))
        }
    }

    override suspend fun requestGetDetailDateSchedule(scheduleId: Int): Result<ResponseScheduleDetailData.ResultScheduleDetailData> {
        val res = service.requestGetDetailDateSchedule(scheduleId)
        return when(res.code()) {
            in 200..399 -> Result.success(res.body()!!.result)
            else -> Result.failure(IllegalArgumentException(res.errorBody()?.string()))
        }
    }

    override suspend fun requestModifyDetailDateSchedule(
        scheduleId: Int,
        body: RequestAddTodo
    ): Result<Int> {
        val res = service.requestModifyDetailDateSchedule(scheduleId, body)
        return when(res.code()) {
            in 200..399 -> Result.success(res.code())
            else -> Result.failure(IllegalArgumentException(res.errorBody()?.string()))
        }
    }

}