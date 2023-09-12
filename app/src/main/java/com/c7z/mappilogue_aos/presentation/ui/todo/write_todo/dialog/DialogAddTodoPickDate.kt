package com.c7z.mappilogue_aos.presentation.ui.todo.write_todo.dialog

import android.content.Context
import android.graphics.Color
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import com.c7z.mappilogue_aos.R
import com.c7z.mappilogue_aos.databinding.DialogAddTodoDatePickerBinding
import com.c7z.mappilogue_aos.presentation.ui.todo.write_todo.viewmodel.AddTodoViewModel
import java.time.LocalDate

class DialogAddTodoPickDate(private val type : String, private val date : String) : androidx.fragment.app.DialogFragment() {
    private lateinit var binding : DialogAddTodoDatePickerBinding

    private val todoViewModel : AddTodoViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.dialog_add_todo_date_picker, container, false)

        initUi()

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        val windowManager =
            requireActivity().getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val display = windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)
        val params: ViewGroup.LayoutParams? = dialog?.window?.attributes
        val deviceWidth = size.x; val deviceHeight = size.y
        params?.width = (deviceWidth * 1).toInt()
        params?.height = (deviceHeight * 0.4).toInt()
        dialog?.window?.attributes = params as WindowManager.LayoutParams
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
        dialog?.window?.setGravity(Gravity.BOTTOM)
    }

    private fun initUi() {
        initYear()
    }

    private fun initYear() {
        binding.dialogAddTodoPickerYear.apply {
            displayedValues = getYearArray()
            maxValue = getYearArray().size - 1

            Log.e("----", "initYear: ${todoViewModel.startDate.value}", )

            value = getYearArray().indexOf(if(type == "Start") todoViewModel.startDate.value?.catchYear() else todoViewModel.endDate.value?.catchYear())
            this.setOnValueChangedListener { _, _, i ->
                if(type == "Start") todoViewModel.setStartDate(todoViewModel.startDate.value!!.replaceRange(0, 4,  getYearArray()[i]))
            }
        }
    }


    private fun getMonthArray(): Array<String> = mutableListOf<String>().apply {
        for (i in 1 until 13) {
            this.add(i.toString())
        }
    }.toTypedArray()

    private fun getYearArray(): Array<String> = mutableListOf<String>().apply {
        for (i in LocalDate.now().year - 10 until LocalDate.now().year + 10) {
            this.add(i.toString())
        }
    }.toTypedArray()

    private fun String.catchYear() : String = this.substring(0, 4)
    private fun String.catchMonth() : String = this.substring(6, 8)
    private fun String.catchDay() : String = this.substring(10, 12)

}