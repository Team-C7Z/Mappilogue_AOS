package com.c7z.mappilogue_aos.data.remote.response

import com.c7z.mappilogue_aos.presentation.util.BaseResponse

data class ResponseCurrentMonthScheduleData(
    val result : ResultCurrentMonthScheduleObject
) : BaseResponse() {

    data class ResultCurrentMonthScheduleObject(
        val calendarSchedules : List<ResultCurrentMonthScheduleData>
    )
    data class ResultCurrentMonthScheduleData(
        val scheduleId : Int,
        val userId : Int,
        val colorId : Int,
        val startDate : String,
        val endDate : String,
        val title : String,
        val colorCode : String
    )
}
