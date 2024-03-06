package com.c7z.mappilogue_aos.data.remote.request

import com.c7z.mappilogue_aos.data.data.TodoAreaData

data class RequestAddTodo(
    val title : String?,
    val colorId : Int,
    val startDate : String,
    val endDate : String,
    val alarmOptions : List<String>?,
    val area : MutableList<TodoAreaData.AreaWithDate>?
)
