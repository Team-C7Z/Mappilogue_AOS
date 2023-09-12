package com.c7z.mappilogue_aos.presentation.ui.component.calendar

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.size
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import androidx.viewpager2.widget.ViewPager2.SCROLL_STATE_IDLE
import com.c7z.mappilogue_aos.R
import com.c7z.mappilogue_aos.databinding.ComponentCalendarBinding
import com.c7z.mappilogue_aos.presentation.ui.component.calendar.adapter.CalendarPagerAdapter
import okhttp3.internal.notify
import java.time.LocalDate
import java.util.*
import kotlin.collections.ArrayDeque

class ComponentCalendar(context: Context, attr: AttributeSet) : ConstraintLayout(context, attr) {
    private lateinit var binding: ComponentCalendarBinding

    private val _requireLocalDate = MutableLiveData<LocalDate>()
    val requireLocalDate: LiveData<LocalDate> = _requireLocalDate

    private lateinit var requireFragment: Fragment

    val current = Int.MAX_VALUE / 2

    private val calendarPagerAdapter: CalendarPagerAdapter by lazy {
        CalendarPagerAdapter(requireFragment)
    }

    init {
        initBinding()
        initYearMonth()
    }

    fun getRequireFragment(fragment: Fragment) {
        requireFragment = fragment

        initUi()
    }

    private fun initBinding() {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.component_calendar,
            this,
            false
        )
        addView(binding.root)
    }

    private fun initYearMonth() {
        _requireLocalDate.value = LocalDate.now()
    }

    private fun initUi() {
        initCalendarPager()

        binding.lifecycleOwner = requireFragment
    }

    private fun initCalendarPager() {
        initPagerItems()
        binding.componentCalendarVpContainer.setCurrentItem(50, false)
        binding.componentCalendarVpContainer.registerOnPageChangeCallback(object :
            OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)

                if(current > position) {
                    calendarPagerAdapter.months[position] = _requireLocalDate.value!!.minusMonths((current - position).toLong())
                } else if (current < position) {
                    calendarPagerAdapter.months[position] = _requireLocalDate.value!!.plusMonths((position - current).toLong())
                }
            }
        })
    }

    fun addLast() {
        calendarPagerAdapter.months.add(0, _requireLocalDate.value!!.minusMonths(1))
        calendarPagerAdapter.notifyItemInserted(0)
    }

    fun addFirst() {
        calendarPagerAdapter.months.add(
            calendarPagerAdapter.months.size,
            _requireLocalDate.value!!.plusMonths(1)
        )
        calendarPagerAdapter.notifyItemInserted(calendarPagerAdapter.itemCount + 1)
    }

    private fun initPagerItems() {
        binding.componentCalendarVpContainer.apply {
            adapter = calendarPagerAdapter
        }
        calendarPagerAdapter.apply {
            months.add(_requireLocalDate.value!!)
        }
    }
}
