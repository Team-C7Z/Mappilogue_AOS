package com.c7z.mappilogue_aos.presentation.ui.todo.write_todo.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.c7z.mappilogue_aos.data.remote.response.ResponseTodoColor

class AddTodoViewModel : ViewModel() {

    private val _colorVisibility = MutableLiveData<Boolean>(false)
    val colorVisibility : LiveData<Boolean> = _colorVisibility

    private val _selectedColor = MutableLiveData<ResponseTodoColor.ResultTodoColor>()
    val selectedColor : LiveData<ResponseTodoColor.ResultTodoColor> = _selectedColor

    private val _startDate = MutableLiveData<String>()
    val startDate : LiveData<String> = _startDate

    private val _endDate = MutableLiveData<String>()
    val endDate : LiveData<String> = _endDate

    fun changeColorListVisibility() {
        _colorVisibility.value = _colorVisibility.value?.not()
    }

    fun setSelectedColor(item : ResponseTodoColor.ResultTodoColor) {
        _selectedColor.value = item
    }

    fun setStartDate(date : String) {
        _startDate.value = date
    }

    fun setEndDate(date : String) {
        _endDate.value = date
    }
}