package com.c7z.mappilogue_aos.presentation.ui.main.calendar.dialog

import android.content.Context
import android.graphics.Color
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.c7z.mappilogue_aos.R
import com.c7z.mappilogue_aos.databinding.DialogAddTodoSelectTimeBinding
import com.c7z.mappilogue_aos.databinding.DialogCalendarSelectMonthBinding
import com.c7z.mappilogue_aos.presentation.ui.main.calendar.viewmodel.CalendarViewModel

class CalendarSetYearMonthDialog (
    private val startYear : Int,
    private val endYear : Int,
        ): DialogFragment() {
    private lateinit var binding : DialogCalendarSelectMonthBinding
    private val viewModel : CalendarViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.dialog_calendar_select_month, container, false)

        initBinding()
        initUi()

        return binding.root
    }

    private fun initBinding() {
        binding.dialog = this
    }

    private fun initUi() {
        initHourPicker()
        initMinPicker()
    }
    fun onSaveClicked() {
        viewModel.setRequireYear(getYear())
        viewModel.setRequireMonth(getMonth())
        onDismiss()
    }

    fun onCancelClicked() {
        onDismiss()
    }

    private fun onDismiss() {
        dismiss()
    }

    private fun getYear() : Int {
        return getYearArray()[binding.dialogCalendarSelectMonthPickerYear.value].toInt()
    }

    private fun getMonth() : Int {
        return getMonthArray()[binding.dialogCalendarSelectMonthPickerMinute.value].toInt()
    }

    private fun initHourPicker() {
        binding.dialogCalendarSelectMonthPickerYear.apply {
            displayedValues = getYearArray()
            maxValue = getYearArray().size - 1

            value = getYearArray().indexOf(viewModel.requireYear.value!!.toString())
        }
    }

    private fun initMinPicker() {
        binding.dialogCalendarSelectMonthPickerMinute.apply {
            displayedValues = getMonthArray()
            maxValue = getMonthArray().size - 1
            value = viewModel.requireMonth.value!! - 1
        }
    }

    private fun getMonthArray(): Array<String> = mutableListOf<String>().apply {
        for (i in 1 until 13) {
            this.add(i.toString())
        }
    }.toTypedArray()

    private fun getYearArray(): Array<String> = mutableListOf<String>().apply {
        for (i in startYear until endYear) {
            this.add(i.toString())
        }
    }.toTypedArray()


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
        params?.width = (deviceWidth * 0.7).toInt(); params?.height = (deviceHeight * 0.4).toInt()
        dialog?.window?.attributes = params as WindowManager.LayoutParams
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }
}