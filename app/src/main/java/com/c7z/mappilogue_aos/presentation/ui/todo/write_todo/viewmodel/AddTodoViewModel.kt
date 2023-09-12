package com.c7z.mappilogue_aos.presentation.ui.todo.write_todo.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

    private val _startDate = MutableLiveData<LocalDate>()
    val startDate : LiveData<LocalDate> = _startDate

    private val _endDate = MutableLiveData<LocalDate>()
    val endDate : LiveData<LocalDate> = _endDate



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
    fun requestScheduleColorData() {
        viewModelScope.launch {
            scheduleRepository.requestScheduleColorData()
                .onSuccess { _colorData.value = it }
        }
    }
}