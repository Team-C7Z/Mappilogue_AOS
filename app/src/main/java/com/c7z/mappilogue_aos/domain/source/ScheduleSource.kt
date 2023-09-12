package com.c7z.mappilogue_aos.domain.source

import com.c7z.mappilogue_aos.data.remote.response.ResponseTodoColor

interface ScheduleSource {
    suspend fun requestScheduleColorData() : Result<List<ResponseTodoColor.ResultTodoColor>>
}