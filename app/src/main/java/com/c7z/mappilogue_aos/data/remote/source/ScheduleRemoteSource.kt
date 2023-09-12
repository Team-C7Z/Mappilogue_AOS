package com.c7z.mappilogue_aos.data.remote.source

import com.c7z.mappilogue_aos.data.remote.response.ResponseTodoColor
import com.c7z.mappilogue_aos.data.remote.service.ScheduleService
import com.c7z.mappilogue_aos.domain.source.ScheduleSource
import com.c7z.mappilogue_aos.presentation.util.ErrorConverter.convertAndGetCode
import javax.inject.Inject

class ScheduleRemoteSource @Inject constructor(private val service : ScheduleService) : ScheduleSource{
    override suspend fun requestScheduleColorData(): Result<List<ResponseTodoColor.ResultTodoColor>> {
        val res = service.requestSchedulesColorData()
        return when(res.code()) {
            in 200..399 -> Result.success(res.body()!!.result)
            else -> Result.failure(IllegalArgumentException(res.errorBody()?.convertAndGetCode().toString()))
        }
    }

}