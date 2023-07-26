package com.c7z.mappilogue_aos.presentation.ui.component

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.c7z.mappilogue_aos.R
import com.c7z.mappilogue_aos.databinding.ComponentCalendarBinding
import java.text.SimpleDateFormat
import java.util.*

class ComponentCalendar(context : Context, attr : AttributeSet) : ConstraintLayout(context ,attr) {
    private lateinit var binding : ComponentCalendarBinding

    private var currentTime = System.currentTimeMillis()

    private val _requireYear = MutableLiveData<Int>()
    val requireYear : LiveData<Int> = _requireYear

    private val _requireMonth = MutableLiveData<Int>()
    val requireMonth : LiveData<Int> = _requireMonth

    init {
        initBinding()
        initYearMonth()

        initUi()
    }

    private fun initBinding() {
        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.component_calendar, this, false)
        addView(binding.root)
    }

    private fun initYearMonth() {
        SimpleDateFormat("yyyy-MM", Locale.KOREA).format(currentTime).split("-").also {
            _requireYear.value = it[0].toInt()
            _requireMonth.value = it[1].toInt()
        }
    }

    private fun initUi() {

    }


}