package com.c7z.mappilogue_aos.presentation.ui.main.calendar.dialog

import android.content.Context
import android.content.DialogInterface
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
import com.c7z.mappilogue_aos.databinding.DialogCalendarDateDetailBinding
import com.c7z.mappilogue_aos.presentation.ui.main.MainActivity
import com.c7z.mappilogue_aos.presentation.ui.main.viewmodel.MainViewModel

class CalendarDailyDialog : DialogFragment() {
    private lateinit var binding: DialogCalendarDateDetailBinding
    private val mainViewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.dialog_calendar_date_detail,
            container,
            false
        )

        initBinding()
        initUi()

        return binding.root
    }

    private fun initBinding() {
        binding.dialog = this
    }

    private fun initUi() {
        binding.dialogCalendarDateDetailTvDate.text = tag?.convertToDate()
    }

    override fun onResume() {
        super.onResume()
        val windowManager =
            requireActivity().getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val display = windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)
        val params: ViewGroup.LayoutParams? = dialog?.window?.attributes
        val deviceWidth = size.x
        val deviceHeight = size.y
        params?.width = (deviceWidth * 0.9).toInt()
        params?.height = (deviceHeight * 0.65).toInt()
        dialog?.window?.attributes = params as WindowManager.LayoutParams
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    private fun String.convertToDate() : String {
        val dates = this.substring(5).split("-").map { it.toInt() }
        return "${dates[0]}월 ${dates[1]}일"
    }

    fun openTodoFragment() {
        (requireActivity() as MainActivity).openTodoActivity(tag)
        dismiss()
    }


    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        mainViewModel.notifyCalendarDialogClosed()
    }
}