package com.c7z.mappilogue_aos.data.remote.response

data class ResponseTodoColor(
    val colors : List<ResultTodoColor>
) {
    data class ResultTodoColor(
        val id : Int,
        val name : String,
        val code : String
    )
}
