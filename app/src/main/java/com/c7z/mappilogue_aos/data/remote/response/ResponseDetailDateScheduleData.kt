package com.c7z.mappilogue_aos.data.remote.response

import com.c7z.mappilogue_aos.presentation.util.BaseResponse

data class ResponseDetailDateScheduleData(
    val result : ResultDetailDateScheduleData
) : BaseResponse() {
    data class ResultDetailDateScheduleData(
        val solarDate : String,
        val lunarDate : String,
        val schedulesOnSpecificDate : List<DetailDateScheduleData>
    )

    data class DetailDateScheduleData(
        val scheduleId : Int,
        val startDate : String,
        val endDate : String,
        val title : String,
        val colorId : Int,
        val colorCode : String,
        val areaName : String,
        val areaTime : String
    )
}
