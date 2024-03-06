package com.c7z.mappilogue_aos.presentation.ui.main.calendar.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.c7z.mappilogue_aos.data.remote.response.ResponseCurrentMonthScheduleData
import com.c7z.mappilogue_aos.data.remote.response.ResponseDetailDateScheduleData
import com.c7z.mappilogue_aos.domain.repository.ScheduleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class CalendarViewModel @Inject constructor(private val scheduleRepository : ScheduleRepository): ViewModel() {
    private val _requireYear = MutableLiveData<Int>()
    val requireYear : LiveData<Int> = _requireYear

    private val _requireMonth = MutableLiveData<Int>()
    val requireMonth : LiveData<Int> = _requireMonth

    private val _currentMonthSchedule = MutableLiveData<List<ResponseCurrentMonthScheduleData.ResultCurrentMonthScheduleData>>()
    val currentMonthSchedule : LiveData<List<ResponseCurrentMonthScheduleData.ResultCurrentMonthScheduleData>> = _currentMonthSchedule

    private val _currentMonthScheduleSet = MutableLiveData<List<LocalDate>>()
    val currentMonthScheduleSet : LiveData<List<LocalDate>> = _currentMonthScheduleSet

    private val _detailDateScheduleData = MutableLiveData<ResponseDetailDateScheduleData.ResultDetailDateScheduleData>()
    val detailDateScheduleData : LiveData<ResponseDetailDateScheduleData.ResultDetailDateScheduleData> = _detailDateScheduleData

    private val _deleteScheduleState = MutableLiveData<Int>()
    val deleteScheduleState : LiveData<Int> = _deleteScheduleState

    fun setRequireYear(year : Int) {
        _requireYear.value = year
    }

    fun setRequireMonth(month : Int) {
        _requireMonth.value = month
    }

    fun requestCurrentMonthScheduleData() {
        viewModelScope.launch {
            scheduleRepository.requestCurrentMonthScheduleData(requireYear.value!!, requireMonth.value!!)
                .onSuccess { _currentMonthSchedule.value = it}
                .onFailure { Log.e("----", "requestCurrentMonthScheduleData:FAILURE ${it.message}", ) }
        }
    }

    fun setCurrentScheduleSet(list : MutableSet<LocalDate>) {
        _currentMonthScheduleSet.value = list.toMutableList()
    }

    fun requestDetailScheduleDate(date : String) {
        viewModelScope.launch {
            scheduleRepository.requestPostDetailDateSchedule(date)
                .onSuccess { _detailDateScheduleData.value = it }
                .onFailure { Log.e("----", "requestDetailScheduleDate: ${it.message}", )}
        }
    }

    fun requestDeleteSchedule(scheduleId : Int) {
        viewModelScope.launch {
            scheduleRepository.requestDeleteSchedule(scheduleId)
                .onSuccess { _deleteScheduleState.value = it }
        }
    }

}