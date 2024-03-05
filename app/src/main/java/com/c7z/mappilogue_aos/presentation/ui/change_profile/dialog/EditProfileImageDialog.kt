package com.c7z.mappilogue_aos.presentation.ui.main.calendar.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.c7z.mappilogue_aos.R
import com.c7z.mappilogue_aos.databinding.DialogBottomDeleteMarkScheduleBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class CalendarDetailDialog(
    private val scheduleId : Int,
    private val onDeleteClicked : (Int) -> Unit) : BottomSheetDialogFragment() {
    private lateinit var binding : DialogBottomDeleteMarkScheduleBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.dialog_bottom_delete_mark_schedule, container, false)

        initBinding()

        return binding.root
    }

    private fun initBinding() {
        binding.dialog = this
    }

    fun onDeleteClicked() {
        onDeleteClicked.invoke(scheduleId)
        dismiss()
    }

    override fun getTheme(): Int = R.style.BottomSheet
}