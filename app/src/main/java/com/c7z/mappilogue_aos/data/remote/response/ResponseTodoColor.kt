package com.c7z.mappilogue_aos.data.remote.response

import com.c7z.mappilogue_aos.presentation.util.BaseResponse

data class ResponseTodoColor(
    val result : List<ResultTodoColor>
): BaseResponse() {
    data class ResultTodoColor(
        val id : Int,
        val name : String,
        val code : String
    )
}
