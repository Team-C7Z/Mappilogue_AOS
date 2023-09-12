package com.c7z.mappilogue_aos.presentation.ui.main.calendar.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CalendarViewModel : ViewModel() {
    private val _requireYear = MutableLiveData<Int>()
    val requireYear : LiveData<Int> = _requireYear

    private val _requireMonth = MutableLiveData<Int>()
    val requireMonth : LiveData<Int> = _requireMonth


    fun setRequireYear(year : Int) {
        _requireYear.value = year
    }

    fun setRequireMonth(month : Int) {
        _requireMonth.value = month
    }


}