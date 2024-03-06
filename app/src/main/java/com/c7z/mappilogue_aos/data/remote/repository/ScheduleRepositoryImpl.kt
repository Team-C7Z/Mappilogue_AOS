package com.c7z.mappilogue_aos.data.remote.repository

import com.c7z.mappilogue_aos.data.remote.request.RequestAddTodo
import com.c7z.mappilogue_aos.data.remote.response.ResponseCurrentMonthScheduleData
import com.c7z.mappilogue_aos.data.remote.response.ResponseDetailDateScheduleData
import com.c7z.mappilogue_aos.data.remote.response.ResponseScheduleDetailData
import com.c7z.mappilogue_aos.data.remote.response.ResponseTodoColor
import com.c7z.mappilogue_aos.domain.repository.ScheduleRepository
import com.c7z.mappilogue_aos.domain.source.ScheduleSource
import com.c7z.mappilogue_aos.presentation.util.BaseResponse
import javax.inject.Inject

class ScheduleRepositoryImpl @Inject constructor(private val source : ScheduleSource) : ScheduleRepository {
    override suspend fun requestScheduleColorData(): Result<List<ResponseTodoColor.ResultTodoColor>> {
        return source.requestScheduleColorData()
    }

    override suspend fun requestCurrentMonthScheduleData(
        year: Int,
        month: Int
    ): Result<List<ResponseCurrentMonthScheduleData.ResultCurrentMonthScheduleData>> {
        return source.requestCurrentMonthScheduleData(year, month)
    }

    override suspend fun requestAddTodo(body: RequestAddTodo): Result<BaseResponse> {
        return source.requestAddTodo(body)
    }

    override suspend fun requestPostDetailDateSchedule(date: String): Result<ResponseDetailDateScheduleData.ResultDetailDateScheduleData> {
        return source.requestPostDetailDateSchedule(date)
    }

    override suspend fun requestDeleteSchedule(scheduleId: Int) : Result<Int> {
        return source.requestDeleteSchedule(scheduleId)
    }

    override suspend fun requestGetDetailDateSchedule(scheduleId: Int): Result<ResponseScheduleDetailData.ResultScheduleDetailData> {
        return source.requestGetDetailDateSchedule(scheduleId)
    }

    override suspend fun requestModifyDetailDateSchedule(
        scheduleId: Int,
        body: RequestAddTodo
    ): Result<Int> {
        return source.requestModifyDetailDateSchedule(scheduleId, body)
    }
}