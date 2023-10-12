package com.c7z.mappilogue_aos.data.remote.response

import com.c7z.mappilogue_aos.presentation.util.BaseResponse

data class ResponseNotificationSettingData(
    val result : ResultNotificationSettingData
) : BaseResponse() {
    data class ResultNotificationSettingData(
        var isTotalAlarm : String,
        var isNoticeAlarm : String,
        var isMarketingAlarm : String,
        var isScheduleReminderAlarm : String
    )
}
