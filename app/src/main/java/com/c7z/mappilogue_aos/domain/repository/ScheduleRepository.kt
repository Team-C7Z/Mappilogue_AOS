package com.c7z.mappilogue_aos.domain.repository

import com.c7z.mappilogue_aos.data.remote.response.ResponseTodoColor

interface ScheduleRepository {
    suspend fun requestScheduleColorData() : Result<List<ResponseTodoColor.ResultTodoColor>>
}