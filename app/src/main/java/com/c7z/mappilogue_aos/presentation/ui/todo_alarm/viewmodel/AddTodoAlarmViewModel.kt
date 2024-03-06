package com.c7z.mappilogue_aos.presentation.ui.todo_alarm.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.c7z.mappilogue_aos.data.data.TodoAlarmData
import com.kakao.sdk.common.KakaoSdk.type

class AddTodoAlarmViewModel : ViewModel() {
    private val standardTime = "9:00 AM"
    private var standardSelectedDate : String = ""
    private var standardDisplayDate : String = ""

    private val _selectedDate = MutableLiveData<String>()
    val selectedDate : LiveData<String> = _selectedDate

    private val _displayDate = MutableLiveData<String>()
    val displayDate : LiveData<String> = _displayDate

    private val _selectedTime = MutableLiveData<String>().apply { value = standardTime }
    val selectedTime : LiveData<String> = _selectedTime

    private val _selectedAlarmList = MutableLiveData<MutableList<TodoAlarmData>>()
    val selectedAlarmList : LiveData<MutableList<TodoAlarmData>> = _selectedAlarmList

    fun setStandardDates(selectedDate: String, displayDate: String) {
        standardSelectedDate = selectedDate
        standardDisplayDate = displayDate
        setSelectedDates(selectedDate, displayDate)
    }

    fun setSelectedDates(selectedDate : String, displayDate : String) {
        _selectedDate.value = selectedDate
        _displayDate.value = displayDate
    }

    fun setSelectedTimes(time : String) {
        _selectedTime.value = time
    }

    fun initAlarm() {
        _selectedDate.value = standardSelectedDate
        _displayDate.value = standardDisplayDate
        _selectedTime.value = standardTime
    }


    private fun getDisplayAlarm() : String {
        return "${displayDate.value?.split("(")?.get(1)?.replace(")", "")} ${selectedTime.value}"
    }

    private fun getSelectedAlarm() : String {
        return "${selectedDate.value} ${selectedTime.value}"
    }

    fun initSelectedAlarm(list : MutableList<TodoAlarmData>) {
        _selectedAlarmList.value = list
    }

    fun notifySelectedAlarm(list : MutableList<TodoAlarmData>, position : Int?) {
        if(position == null) _selectedAlarmList.value = list.also { it.add(TodoAlarmData(getDisplayAlarm(), getSelectedAlarm())) }
        else _selectedAlarmList.value = list.also { it.removeAt(position) }
    }
}