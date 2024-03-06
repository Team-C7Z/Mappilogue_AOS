package com.c7z.mappilogue_aos.presentation.ui.todo_alarm.dialog

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
import androidx.core.view.get
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.c7z.mappilogue_aos.R
import com.c7z.mappilogue_aos.databinding.DialogAddTodoAlarmSelectDateBinding
import com.c7z.mappilogue_aos.presentation.ui.todo_alarm.viewmodel.AddTodoAlarmViewModel
import java.time.LocalDate

class DialogAddTodoAlarmDate : DialogFragment() {
    private lateinit var binding : DialogAddTodoAlarmSelectDateBinding
    private val addTodoAlarmViewModel : AddTodoAlarmViewModel by activityViewModels()

    private lateinit var standardDate : LocalDate

    private var selectedDatePosition = 0
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.dialog_add_todo_alarm_select_date, container, false)

        setStandardDate()
        initBinding()
        initUi()

        return binding.root
    }

    private fun setStandardDate() {
        standardDate = LocalDate.parse(tag)
    }

    private fun initBinding() {
        binding.dialog = this
    }

    private fun initUi() {
        binding.dialogAddTodoAlarmSelectDatePicker.apply {
            displayedValues = getDateList()
            maxValue = getDateList().size - 1
            wrapSelectorWheel = false
            value = getDateList().indexOf(standardDate.parseToMonthDate())

            displayedValues[0].setGuideText()

            setOnValueChangedListener { _, _, i2 ->
                selectedDatePosition = i2
                displayedValues[i2].setGuideText()
            }
        }
    }

    private fun String.setGuideText() {
        binding.dialogAddTodoAlarmSelectTvDividerGuide.text = this
    }

    private fun getDateList() : Array<String> {
        val list = dateList().toMutableList()
        for(i in list.indices) {
            list[i] = list[i] + (" (${standardDate.parseToMonthDate()})")
            standardDate = standardDate.minusDays(1)
        }

        return list.toTypedArray()
    }

    private fun LocalDate.parseToMonthDate() : String {
        return "${this.monthValue}월 ${this.dayOfMonth}일"
    }

    private fun dateList() = listOf(
        "당일", "전날", "이틀 전", "3일 전", "4일 전", "5일 전", "6일 전", "7일 전"
    )

    fun onDateSelect() {
        addTodoAlarmViewModel.setSelectedDates(LocalDate.parse(tag).minusDays(selectedDatePosition.toLong()).toString(), binding.dialogAddTodoAlarmSelectDatePicker.displayedValues[selectedDatePosition])
        onDismiss()
    }

    fun onDismiss() {
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
        params?.width = (deviceWidth * 0.7).toInt(); params?.height = (deviceHeight * 0.4).toInt()
        dialog?.window?.attributes = params as WindowManager.LayoutParams
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }
}