package com.c7z.mappilogue_aos.presentation.ui.main.calendar

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.c7z.mappilogue_aos.R
import com.c7z.mappilogue_aos.databinding.ComponentCalendarDateBinding
import com.c7z.mappilogue_aos.databinding.FragmentCalendarBinding
import com.c7z.mappilogue_aos.presentation.ui.main.MainActivity
import com.c7z.mappilogue_aos.presentation.ui.main.calendar.dialog.CalendarDailyDialog
import com.c7z.mappilogue_aos.presentation.ui.main.calendar.dialog.CalendarSetYearMonthDialog
import com.c7z.mappilogue_aos.presentation.ui.main.calendar.viewmodel.CalendarViewModel
import com.c7z.mappilogue_aos.presentation.ui.main.viewmodel.MainViewModel
import com.c7z.mappilogue_aos.presentation.ui.todo.TodoActivity
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.DayPosition
import com.kizitonwose.calendar.core.daysOfWeek
import com.kizitonwose.calendar.view.MonthDayBinder
import com.kizitonwose.calendar.view.ViewContainer
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter

@AndroidEntryPoint
class CalendarFragment : Fragment() {
    private lateinit var binding: FragmentCalendarBinding
    private val viewModel: CalendarViewModel by activityViewModels()
    private val mainViewModel : MainViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_calendar, container, false)

        initCalendar()
        initBinding()
        initObserve()
        initRequire()
        initUi()


        return binding.root
    }

    private var startMonth = YearMonth.now().minusMonths(150)
    private var endMonth = YearMonth.now().plusMonths(150)
    private fun initCalendar() {
        binding.fgCalendarLayoutCalendar.setup(startMonth, endMonth, daysOfWeek().first())
        YearMonth.now().scrollCalendar()
        initCalendarScroll()
    }

    private fun initRequire() {
        viewModel.setRequireYear(YearMonth.now().year)
        viewModel.setRequireMonth(YearMonth.now().monthValue)
    }

    private fun initObserve() {
        observeCalendarSchedule()
        calendarObserver()
    }

    private fun initCalendarScroll() {
        binding.fgCalendarLayoutCalendar.monthScrollListener = { month ->
            viewModel.setRequireYear(month.yearMonth.year)
            viewModel.setRequireMonth(month.yearMonth.monthValue)
        }
        binding.fgCalendarLayoutCalendar.dayBinder = dayBinder
    }

    override fun onResume() {
        super.onResume()
        viewModel.requestCurrentMonthScheduleData()
    }

    private fun calendarObserver() {
        viewModel.requireMonth.observe(viewLifecycleOwner) {
            binding.fgCalendarLayoutCalendar.scrollToMonth(YearMonth.of(viewModel.requireYear.value!!, it))
        }

        viewModel.requireYear.observe(viewLifecycleOwner) {
            binding.fgCalendarLayoutCalendar.scrollToMonth(YearMonth.of(it, viewModel.requireMonth.value!!))
            viewModel.requestCurrentMonthScheduleData()
        }
    }

    private fun YearMonth.scrollCalendar() {
        binding.fgCalendarLayoutCalendar.scrollToMonth(this)
    }

    private val dayBinder = object : MonthDayBinder<DayViewContainer> {
        override fun bind(container: DayViewContainer, data: CalendarDay) {
            container.day = data
            val dateBinding = DataBindingUtil.bind<ComponentCalendarDateBinding>(container.view)
            dateBinding?.let {
                it.componentCalendarTvDate.apply {
                    text = data.date.dayOfMonth.toString()
                    setTextColor(container.day.checkDateColor())
                }
                it.componentCalendarLayoutToday.visibility =
                    if (data.date == LocalDate.now() && container.day.position == DayPosition.MonthDate) View.VISIBLE else View.GONE

                viewModel.currentMonthScheduleSet.observe(viewLifecycleOwner) {items ->
                    it.componentCalendarLayoutSchedule.visibility = if (container.day.date in items) View.VISIBLE else View.GONE
                }

            }
        }

        override fun create(view: View): DayViewContainer = DayViewContainer(view)
    }

    inner class DayViewContainer(view: View) : ViewContainer(view) {
        lateinit var day: CalendarDay
        val dayText = ComponentCalendarDateBinding.bind(view).componentCalendarTvDate

        init {
            view.setOnClickListener {
                val dateBinding = this
                if(binding.fgCalendarLayoutPicker.root.visibility == View.GONE) {
                    openDailyDialog(LocalDate.of(viewModel.requireYear.value!!, viewModel.requireMonth.value!!, dateBinding.dayText.text.toString().toInt()))
                } else {
                    binding.fgCalendarLayoutPicker.root.visibility = View.GONE
                }
            }
        }


    }

    private fun CalendarDay.checkDateColor(): Int {
        return if (this.position == DayPosition.MonthDate) {
            /** 오늘 **/
            if (this.date == LocalDate.now()) {
                resources.getColor(R.color.white, null)
            } else {
                /** 현재 월 **/
                when (this.date.dayOfWeek.value) {
                    6 -> resources.getColor(R.color.blue_1, null)
                    7 -> resources.getColor(R.color.red_1, null)
                    else -> resources.getColor(R.color.black, null)
                }
            }
        } else {
            resources.getColor(R.color.gray_4, null)
        }
    }

    private fun observeCalendarSchedule() {
        viewModel.currentMonthSchedule.observe(viewLifecycleOwner) {
            var set = mutableSetOf<LocalDate>()

            for(i in it) {
                var a = i.startDate.splitWithDate()
                while(a <= i.endDate.splitWithDate()) {
                    set.add(a)
                    a = a.plusDays(1)
                }
            }
            viewModel.setCurrentScheduleSet(set)
        }
    }

    private fun initBinding() {
        binding.fragment = this
        binding.viewModel = viewModel
        binding.mainViewModel = mainViewModel
        binding.lifecycleOwner = viewLifecycleOwner
    }

    private fun initUi() {
        binding.fgCalendarLayoutYearMonth.setOnClickListener {
            CalendarSetYearMonthDialog(startMonth.year, endMonth.year).show(requireActivity().supportFragmentManager, null)
        }
    }

    fun openDailyDialog(date : LocalDate) {
        viewModel.requestDetailScheduleDate(date.toString())
        mainViewModel.notifyCalendarDialogOpened()
        CalendarDailyDialog(::openModifyTodo).show(requireActivity().supportFragmentManager, date.toString())
    }

    private fun openModifyTodo(scheduleId : Int) {
        startActivity(Intent(requireActivity(), TodoActivity::class.java).addFlags(scheduleId))
    }

    private fun getMonthArray(): Array<String> = mutableListOf<String>().apply {
        for (i in 1 until 13) {
            this.add(i.toString())
        }
    }.toTypedArray()

    private fun getYearArray(): Array<String> = mutableListOf<String>().apply {
        for (i in startMonth.year until endMonth.year) {
            this.add(i.toString())
        }
    }.toTypedArray()

    fun openTodoActivity() {
        (requireActivity() as MainActivity).openTodoActivity(LocalDate.now().toString())
    }
    
    private fun String.splitWithDate() : LocalDate {
        val dateArray = this.split("-")
        return LocalDate.of(dateArray[0].toInt(), dateArray[1].toInt(), dateArray[2].toInt())
    }

}