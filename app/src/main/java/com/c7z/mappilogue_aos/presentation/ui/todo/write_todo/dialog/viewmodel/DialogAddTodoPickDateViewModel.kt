package com.c7z.mappilogue_aos.presentation.ui.todo.write_todo.dialog.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.time.LocalDate
import java.time.YearMonth

class DialogAddTodoPickDateViewModel : ViewModel() {

    private val _startDate = MutableLiveData<LocalDate>()
    val startDate : LiveData<LocalDate> = _startDate

    private val _endDate = MutableLiveData<LocalDate>()
    val endDate : LiveData<LocalDate> = _endDate

    private val _standardMonth = MutableLiveData<YearMonth>()
    val standardMonth : LiveData<YearMonth> = _standardMonth



    fun setStartDate(date : LocalDate) {
        _startDate.value = date
    }

    fun setEndDate(date : LocalDate) {
        _endDate.value = date
    }

    fun setStandardMonth(yearMonth: YearMonth) {
        _standardMonth.value = yearMonth
    }

}