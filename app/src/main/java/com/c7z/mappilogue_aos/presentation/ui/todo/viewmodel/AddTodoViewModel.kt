package com.c7z.mappilogue_aos.presentation.ui.todo.write_todo.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.c7z.mappilogue_aos.data.remote.response.ResponseKakaoLocation
import com.c7z.mappilogue_aos.data.remote.response.ResponseTodoColor
import com.c7z.mappilogue_aos.domain.repository.ScheduleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.YearMonth
import javax.inject.Inject

@HiltViewModel
class AddTodoViewModel @Inject constructor(private val scheduleRepository : ScheduleRepository): ViewModel() {

    private val _colorVisibility = MutableLiveData<Boolean>(false)
    val colorVisibility : LiveData<Boolean> = _colorVisibility

    private val _colorData = MutableLiveData<List<ResponseTodoColor.ResultTodoColor>>()
    val colorData : LiveData<List<ResponseTodoColor.ResultTodoColor>> = _colorData

    private val _selectedColor = MutableLiveData<ResponseTodoColor.ResultTodoColor>()
    val selectedColor : LiveData<ResponseTodoColor.ResultTodoColor> = _selectedColor

    private val _selectedDates = MutableLiveData<List<String>>()
    val selectedDates : LiveData<List<String>> = _selectedDates

    private val _startDate = MutableLiveData<LocalDate>()
    val startDate : LiveData<LocalDate> = _startDate

    private val _endDate = MutableLiveData<LocalDate>()
    val endDate : LiveData<LocalDate> = _endDate

    private val _checkStatus = MutableLiveData<Boolean>(false)
    val checkStatus : LiveData<Boolean> = _checkStatus

    private val _selectedDate = MutableLiveData<String>()
    val selectedDate : LiveData<String> = _selectedDate

    private val _locationWithDate = mutableMapOf<String, MutableList<ResponseKakaoLocation.Document>>()
    var locationWithDate : Map<String, MutableList<ResponseKakaoLocation.Document>> = _locationWithDate

    private val _locationList = MutableLiveData<MutableList<ResponseKakaoLocation.Document>>()
    val locationList : LiveData<MutableList<ResponseKakaoLocation.Document>> = _locationList
    private var toolsLocationList = mutableListOf<ResponseKakaoLocation.Document>()

    private var _checkedLocationList = mutableListOf<Int>()
    val checkedLocationList : List<Int> = _checkedLocationList

    fun changeColorListVisibility() {
        _colorVisibility.value = _colorVisibility.value?.not()
    }

    fun setSelectedColor(item : ResponseTodoColor.ResultTodoColor) {
        _selectedColor.value = item
    }

    fun setStartDate(date : LocalDate) {
        _startDate.value = date
    }

    fun setEndDate(date : LocalDate) {
        _endDate.value = date
    }

    fun setSelectedDates(dates : List<String>) {
        _selectedDates.value = dates
    }

    fun changeCheckStatus() {
        _checkStatus.value = _checkStatus.value?.not()
    }

    fun setSelectedDate(date : String) {
        _selectedDate.value = date
    }

    fun insertLocationList(document : ResponseKakaoLocation.Document) {
        toolsLocationList.add(document)
        notifyLocationList()
    }

    fun removeLocationList(position: Int) {
        toolsLocationList.removeAt(position)
        notifyLocationList()
    }

    private fun notifyLocationList() {
        _locationList.value = toolsLocationList
        if(toolsLocationList.isEmpty()) _checkStatus.value = false
    }

    fun setLocationWithDate(locationList : MutableList<ResponseKakaoLocation.Document>) {
        _locationWithDate[selectedDate.value!!] = locationList
    }

    fun onChangeSelectedDate() {
        toolsLocationList = _locationWithDate[selectedDate.value!!] ?: mutableListOf()
        notifyLocationList()
    }

    fun appendCheckedLocationList(position : Int) {
        _checkedLocationList.add(position)
    }

    fun removeCheckedLocationList(position : Int) {
        _checkedLocationList.remove(position)
    }

    fun initCheckedLocationList() {
        _checkedLocationList.clear()
    }

    fun requestScheduleColorData() {
        viewModelScope.launch {
            scheduleRepository.requestScheduleColorData()
                .onSuccess { _colorData.value = it }
        }
    }
}