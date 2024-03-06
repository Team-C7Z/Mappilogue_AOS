package com.c7z.mappilogue_aos.presentation.ui.todo_alarm.dialog

import android.content.Context
import android.graphics.Color
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.c7z.mappilogue_aos.R
import com.c7z.mappilogue_aos.databinding.DialogAddTodoAlarmSelectTimeBinding
import com.c7z.mappilogue_aos.databinding.DialogAddTodoSelectTimeBinding

class DialogAddTodoAlarmSetTime (
    private val onSaveClicked : (String) -> Unit
        ): DialogFragment() {
    private lateinit var binding : DialogAddTodoAlarmSelectTimeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.dialog_add_todo_alarm_select_time, container, false)

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
        initAMPMPicker()
    }

    fun onSaveClicked() {
        onSaveClicked.invoke(getTime())
        onDismiss()
    }

    fun onCancelClicked() {
        onDismiss()
    }

    private fun onDismiss() {
        dismiss()
    }

    private fun getTime() : String {
        return "${getHourArray()[binding.dialogAddTodoAlarmSelectTimePickerHour.value]}:${getMinArray()[binding.dialogAddTodoAlarmSelectTimePickerMinute.value]} ${getAMPM()[binding.dialogAddTodoAlarmSelectTimePickerAmpm.value]}"
    }

    private fun initHourPicker() {
        binding.dialogAddTodoAlarmSelectTimePickerHour.apply {
            displayedValues = getHourArray()
            maxValue = getHourArray().size - 1
            value = getHourArray().indexOf("9")

            displayedValues[0].setHourGuideText()

            setOnValueChangedListener { _, _, i2 ->
                displayedValues[i2].setHourGuideText()
            }
        }
    }

    private fun String.setHourGuideText() {
        binding.dialogAddTodoAlarmSelectTimeToolsTvHour.text = this
    }

    private fun initMinPicker() {
        binding.dialogAddTodoAlarmSelectTimePickerMinute.apply {
            displayedValues = getMinArray()
            maxValue = getMinArray().size - 1

            displayedValues[0].setMinGuideText()

            setOnValueChangedListener { _, _, i2 ->
                displayedValues[i2].setMinGuideText()
            }
        }
    }

    private fun String.setMinGuideText() {
        binding.dialogAddTodoAlarmSelectTimeToolsTvMinute.text = this
    }

    private fun initAMPMPicker() {
        binding.dialogAddTodoAlarmSelectTimePickerAmpm.apply {
            displayedValues = getAMPM()
            maxValue = getAMPM().size - 1

            displayedValues[0].setAmPmGuideText()

            setOnValueChangedListener { _, _, i2 ->
                displayedValues[i2].setAmPmGuideText()
            }
        }
    }

    private fun String.setAmPmGuideText() {
        binding.dialogAddTodoAlarmSelectTimeToolsTvAmpm.text = this
    }

    private fun getHourArray() : Array<String> = mutableListOf<String>().apply {
        for (i in 1 until 13) {
            this.add(i.toString())
        }
    }.toTypedArray()

    private fun getMinArray() : Array<String> = mutableListOf<String>().apply {
        for(i in 0 until 60) {
            this.add(String.format("%02d", i))
        }
    }.toTypedArray()

    private fun getAMPM() : Array<String> = mutableListOf("AM", "PM").toTypedArray()

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