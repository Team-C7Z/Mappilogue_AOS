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
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.c7z.mappilogue_aos.R
import com.c7z.mappilogue_aos.databinding.DialogCalendarDateDetailBinding
import com.c7z.mappilogue_aos.presentation.ui.component.dialog.ComponentDialogTwoButton
import com.c7z.mappilogue_aos.presentation.ui.main.MainActivity
import com.c7z.mappilogue_aos.presentation.ui.main.calendar.dialog.adapter.CalendarScheduleAdapter
import com.c7z.mappilogue_aos.presentation.ui.main.calendar.viewmodel.CalendarViewModel
import com.c7z.mappilogue_aos.presentation.ui.main.viewmodel.MainViewModel
import java.time.LocalDate

class CalendarDailyDialog (
    private val openModifyTodo : (Int) -> Unit
) : DialogFragment() {
    private lateinit var binding: DialogCalendarDateDetailBinding
    private val mainViewModel: MainViewModel by activityViewModels()
    private val calendarViewModel : CalendarViewModel by activityViewModels()

    private val adapter by lazy {
        CalendarScheduleAdapter(::openModifyTodo, ::openDetailDialog)
    }

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
        initObserve()

        return binding.root
    }

    private fun initBinding() {
        binding.dialog = this
    }

    private fun initUi() {
        binding.dialogCalendarDateDetailTvDate.text = tag?.convertToDate()

        binding.dialogCalendarDateDetailRvTodo.adapter = adapter
    }

    private fun initObserve() {
        observeDetailData()
        observeScheduleDeleteStatus()
    }

    private fun observeDetailData() {
        calendarViewModel.detailDateScheduleData.observe(viewLifecycleOwner) {
            binding.dialogCalendarDateDetailTvLunarDate.text = "음력" + it.lunarDate.substring(5)
            adapter.items = it.schedulesOnSpecificDate.toMutableList().also { adapter.notifyDataSetChanged() }
        }
    }

    private fun openDetailDialog(scheduleId : Int) {
        CalendarDetailDialog(scheduleId, ::onScheduleDeleteClicked).show(requireActivity().supportFragmentManager, null)
    }

    private fun openModifyTodo(scheduleId : Int) {
        openModifyTodo.invoke(scheduleId)
        dismiss()
    }

    private fun onScheduleDeleteClicked(scheduleId: Int) {
        ComponentDialogTwoButton(scheduleId, ::deleteSchedule, "RED").show(requireActivity().supportFragmentManager, "DELETE_SCHEDULE")
    }

    private fun deleteSchedule(scheduleId: Int?) {
        calendarViewModel.requestDeleteSchedule(scheduleId!!)
    }

    private fun observeScheduleDeleteStatus() {
        calendarViewModel.deleteScheduleState.observe(viewLifecycleOwner) {
            if(it == 204) {
                calendarViewModel.requestDetailScheduleDate(returnForRefresh())
            }
        }
    }

    private fun returnForRefresh() : String {
        val ref = calendarViewModel.detailDateScheduleData.value?.solarDate!!.replace("년 ", "-").replace("월 ", "-").replace("일", "")
        return LocalDate.of(ref.split("-")[0].toInt(), ref.split("-")[1].toInt(), ref.split("-")[2].toInt()).toString()
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