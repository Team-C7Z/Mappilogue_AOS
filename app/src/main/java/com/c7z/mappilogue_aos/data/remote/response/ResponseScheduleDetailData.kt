package com.c7z.mappilogue_aos.data.remote.response

import com.c7z.mappilogue_aos.data.data.TodoAreaData
import com.c7z.mappilogue_aos.presentation.util.BaseResponse

data class ResponseScheduleDetailData(
    val result : ResultScheduleDetailData
) : BaseResponse() {
    data class ResultScheduleDetailData(
        val scheduleBaseInfo : ScheduleBaseInfoData,
        val scheduleAlarmInfo : List<String>,
        val scheduleAreaInfo : List<TodoAreaData.AreaWithDate>
    )

    data class ScheduleBaseInfoData (
        val id : Int,
        val userId : Int,
        val startDate : String,
        val endDate : String,
        val colorId : Int,
        val colorCode : String,
        val isAlarm : String,
        val title : String
    )


}
