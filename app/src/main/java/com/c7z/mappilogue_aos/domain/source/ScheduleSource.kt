package com.c7z.mappilogue_aos.domain.source

import com.c7z.mappilogue_aos.data.remote.request.RequestAddTodo
import com.c7z.mappilogue_aos.data.remote.response.ResponseCurrentMonthScheduleData
import com.c7z.mappilogue_aos.data.remote.response.ResponseDetailDateScheduleData
import com.c7z.mappilogue_aos.data.remote.response.ResponseScheduleDetailData
import com.c7z.mappilogue_aos.data.remote.response.ResponseTodoColor
import com.c7z.mappilogue_aos.presentation.util.BaseResponse

interface ScheduleSource {
    suspend fun requestScheduleColorData() : Result<List<ResponseTodoColor.ResultTodoColor>>

    suspend fun requestCurrentMonthScheduleData(year : Int, month : Int) : Result<List<ResponseCurrentMonthScheduleData.ResultCurrentMonthScheduleData>>

    suspend fun requestAddTodo(body : RequestAddTodo) : Result<BaseResponse>

    suspend fun requestPostDetailDateSchedule(date : String) : Result<ResponseDetailDateScheduleData.ResultDetailDateScheduleData>

    suspend fun requestDeleteSchedule(scheduleId : Int) : Result<Int>

    suspend fun requestGetDetailDateSchedule(scheduleId: Int) : Result<ResponseScheduleDetailData.ResultScheduleDetailData>

    suspend fun requestModifyDetailDateSchedule(scheduleId: Int, body : RequestAddTodo) : Result<Int>
}