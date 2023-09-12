package com.c7z.mappilogue_aos.data.remote.repository

import com.c7z.mappilogue_aos.data.remote.response.ResponseTodoColor
import com.c7z.mappilogue_aos.domain.repository.ScheduleRepository
import com.c7z.mappilogue_aos.domain.source.ScheduleSource
import javax.inject.Inject

class ScheduleRepositoryImpl @Inject constructor(private val source : ScheduleSource) : ScheduleRepository {
    override suspend fun requestScheduleColorData(): Result<List<ResponseTodoColor.ResultTodoColor>> {
        return source.requestScheduleColorData()
    }
}