package com.c7z.mappilogue_aos.presentation.ui.todo.write_todo.dialog.add_todo

import android.content.Context
import android.graphics.Color
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.c7z.mappilogue_aos.R
import com.c7z.mappilogue_aos.databinding.DialogAddTodoDatePickerBinding
import com.c7z.mappilogue_aos.databinding.ItemDialogAddTodoPickDateDayBinding
import com.c7z.mappilogue_aos.presentation.ui.todo.write_todo.dialog.add_todo.viewmodel.DialogAddTodoPickDateViewModel
import com.c7z.mappilogue_aos.presentation.ui.todo.viewmodel.AddTodoViewModel
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.DayPosition
import com.kizitonwose.calendar.core.daysOfWeek
import com.kizitonwose.calendar.view.MonthDayBinder
import com.kizitonwose.calendar.view.ViewContainer
import java.time.LocalDate
import java.time.YearMonth

class DialogAddTodoPickDate() :
    androidx.fragment.app.DialogFragment() {
    private lateinit var binding: DialogAddTodoDatePickerBinding

    private val viewModel : DialogAddTodoPickDateViewModel by viewModels()
    private val todoViewModel: AddTodoViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.dialog_add_todo_date_picker,
            container,
            false
        )

        initData()
        initCalendar()
        initBinding()
        initObserve()

        return binding.root
    }

    private fun initData() {
        todoViewModel.startDate.value?.let { viewModel.setStartDate(it) }
        todoViewModel.endDate.value?.let { viewModel.setEndDate(it) }
    }

    private fun initCalendar() {
        val standardMonth = getStandardMonth()
        binding.dialogAddTodoDatePickerViewCalendar.setup(
            standardMonth.minusMonths(150),
            standardMonth.plusMonths(150),
            daysOfWeek().first()
        )
        standardMonth.scrollCalendar()
        setDayBinder()
        initCalendarScroll()
    }

    private fun YearMonth.scrollCalendar() {
        binding.dialogAddTodoDatePickerViewCalendar.scrollToMonth(this)
    }

    private fun setDayBinder() { binding.dialogAddTodoDatePickerViewCalendar.dayBinder = dayBinder }

    private fun getStandardMonth(): YearMonth = YearMonth.of(
        todoViewModel.startDate.value?.year!!,
        todoViewModel.startDate.value?.monthValue!!
    )

    private fun initCalendarScroll() {
        binding.dialogAddTodoDatePickerViewCalendar.monthScrollListener = { month ->
            viewModel.setStandardMonth(month.yearMonth)
        }
    }

    private val dayBinder = object : MonthDayBinder<DayViewContainer> {
        override fun bind(container: DayViewContainer, data: CalendarDay) {
            container.day = data
            val dateBinding =
                DataBindingUtil.bind<ItemDialogAddTodoPickDateDayBinding>(container.view)
            dateBinding?.let {
                it.itemDialogAddTodoPickDateDayTvDate.apply {
                    text = data.date.dayOfMonth.toString()
                    setTextColor(container.day.checkDateColor())
                }
                it.itemDialogAddTodoPickDateDayBgBackground.setBackgroundResource(container.day.checkBackgrounds())
                it.itemDialogAddTodoPickDateDayBgSelection.setBackgroundResource(container.day.checkSelections())
            }
        }

        override fun create(view: View): DayViewContainer = DayViewContainer(view)

    }

    inner class DayViewContainer(view: View) : ViewContainer(view) {
        lateinit var day: CalendarDay
        val dayText = ItemDialogAddTodoPickDateDayBinding.bind(view).itemDialogAddTodoPickDateDayTvDate

        init {
            view.setOnClickListener {
                val dateBinding = this
                LocalDate.of(viewModel.standardMonth.value!!.year, viewModel.standardMonth.value!!.monthValue, dateBinding.dayText.text.toString().toInt()).setDate()
            }
        }
    }

    private fun CalendarDay.checkDateColor(): Int {
        return if (this.position == DayPosition.MonthDate) {
            /** 오늘 **/
            when (this.date) {
                viewModel.startDate.value, viewModel.endDate.value -> resources.getColor(R.color.white, null)
                LocalDate.now() -> resources.getColor(R.color.green_3, null)
                else -> resources.getColor(R.color.black, null)
            }
        } else {
            resources.getColor(com.google.android.material.R.color.mtrl_btn_transparent_bg_color, null)
        }
    }

    private fun CalendarDay.checkBackgrounds() : Int {
        return if(this.position == DayPosition.MonthDate) {
            if(this.date == viewModel.startDate.value && this.date == viewModel.endDate.value) {
                R.color.white
            } else if(this.date == viewModel.startDate.value) {
                R.drawable.bg_calendar_date_picker_start_day
            } else if(this.date == viewModel.endDate.value) {
                R.drawable.bg_calendar_date_picker_end_day
            } else if(this.date.toString() in viewModel.startDate.value.toString() .. viewModel.endDate.value.toString()) {
                R.color.gray_2
            } else {
                R.color.white
            }
        } else {
            R.color.white
        }
    }

    private fun CalendarDay.checkSelections() : Int {
        return when (this.date) {
            viewModel.startDate.value, viewModel.endDate.value -> R.drawable.bg_calendar_date_today
            LocalDate.now() -> R.drawable.bg_calendar_date_picker_today
            else -> Color.TRANSPARENT
        }
    }

    fun onPreviousClicked() {
        viewModel.standardMonth.value?.let { viewModel.setStandardMonth(it.minusMonths(1)) }
        viewModel.standardMonth.value?.scrollCalendar()
    }

    fun onNextClicked() {
        viewModel.standardMonth.value?.let { viewModel.setStandardMonth(it.plusMonths(1)) }
        viewModel.standardMonth.value?.scrollCalendar()
    }

    private fun initBinding() {
        binding.dialog = this
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
    }

    private fun initObserve() {
        viewModel.startDate.observe(viewLifecycleOwner) {
            binding.dialogAddTodoDatePickerViewCalendar.notifyCalendarChanged()
        }
        viewModel.endDate.observe(viewLifecycleOwner) {
            binding.dialogAddTodoDatePickerViewCalendar.notifyCalendarChanged()
        }
    }

    private fun LocalDate.setDate() {
        if (viewModel.startDate.value == viewModel.endDate.value) {
            if(this > viewModel.startDate.value) {
                viewModel.setEndDate(this)
            } else {
                viewModel.setStartDate(this)
            }
        } else {
            viewModel.setStartDate(this)
            viewModel.setEndDate(this)
        }
    }

    fun onSaveClicked() {
        saveRange()
        setDismiss()
    }

    private fun saveRange() {
        viewModel.startDate.value?.let {todoViewModel.setStartDate(it) }
        viewModel.endDate.value?.let {todoViewModel.setEndDate(it)}
    }

    fun onCancelClicked() {
        setDismiss()
    }

    private fun setDismiss() {
        dismiss()
    }

    /** Set Dialog Size **/
    override fun onResume() {
        super.onResume()
        val windowManager =
            requireActivity().getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val display = windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)
        val params: ViewGroup.LayoutParams? = dialog?.window?.attributes
        val deviceWidth = size.x;
        val deviceHeight = size.y
        params?.width = (deviceWidth * 0.9).toInt(); params?.height = (deviceHeight * 0.6).toInt()
        dialog?.window?.attributes = params as WindowManager.LayoutParams
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }
}