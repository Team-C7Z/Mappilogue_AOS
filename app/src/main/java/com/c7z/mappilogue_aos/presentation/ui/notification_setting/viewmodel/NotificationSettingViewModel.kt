package com.c7z.mappilogue_aos.presentation.ui.notification_setting.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.c7z.mappilogue_aos.data.remote.response.ResponseNotificationSettingData
import com.c7z.mappilogue_aos.domain.repository.NotificationRepository
import com.c7z.mappilogue_aos.presentation.util.BaseResponse
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotificationSettingViewModel @Inject constructor(private val notificationRepository: NotificationRepository): ViewModel() {
    private val _acceptTotalNotification = MutableLiveData<String>()
    val acceptTotalNotification : LiveData<String> = _acceptTotalNotification

    private val _notificationCheckItems = MutableLiveData<MutableList<String>>()
    val notificationCheckItems : LiveData<MutableList<String>> = _notificationCheckItems

    init {
        requestNotificationData()
    }

    fun requestNotificationData() {
        viewModelScope.launch {
            notificationRepository.requestNotificationData()
                .onSuccess {
                    Log.e("----", "requestNotificationData: SUCCESS $it", )
                    _acceptTotalNotification.value = it.isTotalAlarm
                    _notificationCheckItems.value = mutableListOf(it.isNoticeAlarm, it.isScheduleReminderAlarm, it.isMarketingAlarm)
                }
                .onFailure { Log.e("----", "requestNotificationData: FAIL ${Gson().fromJson(it.message, BaseResponse::class.java).message}", ) }
        }
    }

    fun changeTotalNotificationStatus(checked : Boolean) {
        _acceptTotalNotification.value = if(checked) "ACTIVE" else "INACTIVE"

        requestModifyNotificationSettings()
    }

    fun changeEachNotificationStatus(position : Int, checked : Boolean) {
        _notificationCheckItems.value!![position] = if(checked) "ACTIVE" else "INACTIVE"

        requestModifyNotificationSettings()
    }

    private fun requestModifyNotificationSettings() {
        viewModelScope.launch {
            notificationRepository.requestModifyNotificationData(mapNotificationData())
                .onSuccess { requestNotificationData() }
        }
    }

    private fun mapNotificationData() : ResponseNotificationSettingData.ResultNotificationSettingData {
        return ResponseNotificationSettingData.ResultNotificationSettingData(
            acceptTotalNotification.value!!,
            notificationCheckItems.value!![0],
            notificationCheckItems.value!![2],
            notificationCheckItems.value!![1]
        )
    }

    /** Dummy **/
    fun returnNotificationTitles() : List<String> = listOf("공지사항 알림", "일정 미리 알림", "마케팅 알림")
}