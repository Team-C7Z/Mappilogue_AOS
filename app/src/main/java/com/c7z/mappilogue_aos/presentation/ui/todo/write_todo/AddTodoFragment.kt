package com.c7z.mappilogue_aos.presentation.ui.todo.write_todo

import android.app.Activity
import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.graphics.toColorInt
import androidx.core.view.children
import androidx.core.view.get
import androidx.core.view.size
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.ItemTouchHelper
import com.c7z.mappilogue_aos.R
import com.c7z.mappilogue_aos.data.data.TodoAlarmData
import com.c7z.mappilogue_aos.data.remote.response.ResponseTodoColor
import com.c7z.mappilogue_aos.databinding.FragmentAddTodoBinding
import com.c7z.mappilogue_aos.presentation.ui.todo.write_todo.adapter.AddTodoColorAdapter
import com.c7z.mappilogue_aos.presentation.ui.todo.write_todo.adapter.AddTodoLocationAdapter
import com.c7z.mappilogue_aos.presentation.ui.todo.write_todo.dialog.add_todo.DialogAddTodoPickDate
import com.c7z.mappilogue_aos.presentation.ui.todo.write_todo.dialog.location.DialogAddTodoSearchLocation
import com.c7z.mappilogue_aos.presentation.ui.todo.write_todo.dialog.time.DialogAddTodoSetTime
import com.c7z.mappilogue_aos.presentation.ui.todo.viewmodel.AddTodoViewModel
import com.c7z.mappilogue_aos.presentation.ui.todo_alarm.AddTodoAlarmActivity
import com.c7z.mappilogue_aos.presentation.util.ItemTouchCallback
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDate

@AndroidEntryPoint
class AddTodoFragment : Fragment() {
    private lateinit var binding: FragmentAddTodoBinding
    private val viewModel: AddTodoViewModel by activityViewModels()

    private val colorAdapter by lazy {
        AddTodoColorAdapter(::setSelectedColor)
    }

    private val locationAdapter by lazy {
        AddTodoLocationAdapter(::onLocationDeleteChecked, ::onLocationTimeClicked)
    }
    private val itemTouchHelper by lazy { ItemTouchHelper(ItemTouchCallback(locationAdapter)) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_todo, container, false)

        initBinding()
        initUi()
        initObserve()

        return binding.root
    }

    private fun initBinding() {
        binding.fragment = this
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
    }

    private fun initUi() {
        initColorRv()
        initDates()
        initLocationRv()
        initChipGroup()
    }

    private fun initObserve() {
        observeScheduleColor()
        observeSelectedDates()
        selectedColorObserve()
    }

    private fun observeScheduleColor() {
        viewModel.colorData.observe(viewLifecycleOwner) {
            colorAdapter.colors = it.toMutableList()
            colorAdapter.notifyItemRangeChanged(0, it.size)
        }
    }

    private fun initChipGroup() {
        binding.fgAddTodoChipgroupChip.apply {
            isSingleSelection = true
            setOnCheckedStateChangeListener { _, _ ->
                for (i in 0 until this.childCount) {
                    this[i].isClickable = true
                }

                for(i in this.children) {
                    if((i as Chip).id == checkedChipId && i.isCheckable) {
                        i.isClickable = false
                        viewModel.setSelectedDate(i.tag.toString())
                    }
                }
            }
        }
    }

    private fun initColorRv() {
        binding.fgAddTodoRvColors.adapter = colorAdapter

        /** 서버에 Color Data Request**/
        viewModel.requestScheduleColorData()
    }

    private fun initDates() {
        tag?.let { viewModel.setStartDate(it.convertToYearMonth()) }
        tag?.let { viewModel.setEndDate(it.convertToYearMonth()) }

        observeDates()
    }

    private fun initLocationRv() {
        binding.fgAddTodoRvLocations.adapter = locationAdapter
        itemTouchHelper.attachToRecyclerView(binding.fgAddTodoRvLocations)
    }

    private fun observeDates() {
        viewModel.startDate.observe(viewLifecycleOwner) {
            binding.fgAddTodoTvStartDate.text = it.convertToDate()
            viewModel.initTodoAlarmList()
            if(viewModel.endDate.value != null) getDateRangeForChip()
        }

        viewModel.endDate.observe(viewLifecycleOwner) {
            binding.fgAddTodoTvEndDate.text = it.convertToDate()
            if(viewModel.startDate.value != null) getDateRangeForChip()
        }

        viewModel.locationList.observe(viewLifecycleOwner) {
            viewModel.setLocationWithDate(it)
            locationAdapter.locationData = it
            locationAdapter.notifyDataSetChanged()
        }

        viewModel.checkStatus.observe(viewLifecycleOwner) {
            locationAdapter.checkStatus = it
            locationAdapter.notifyDataSetChanged()
        }
    }

    private fun onLocationDeleteChecked(position: Int, selected: Boolean) {
        when (selected) {
            true -> viewModel.appendCheckedLocationList(position)
            else -> viewModel.removeCheckedLocationList(position)
        }
    }

    fun onLocationCheckClicked() {
        viewModel.changeCheckStatus()

        if (viewModel.checkStatus.value == true) {
            locationAdapter.initSelectClicked()
            viewModel.initCheckedLocationList()
        }
    }

    fun onLocationDeleteClicked() {
        for (i in 0 until viewModel.checkedLocationList.size) {
            val position =
                if ((viewModel.checkedLocationList[i] - i) <= 0) 0 else viewModel.checkedLocationList[i] - i
            viewModel.removeLocationList(position)
        }
        viewModel.initCheckedLocationList()
    }

    private fun onLocationTimeClicked(position: Int) {
        DialogAddTodoSetTime(::onLocationTimeSave).show(
            requireActivity().supportFragmentManager,
            position.toString()
        )
    }

    private fun onLocationTimeSave(position: Int, time: String) {
        locationAdapter.locationData[position].time = time
        locationAdapter.notifyItemChanged(position)
    }

    private fun setSelectedColor(item: ResponseTodoColor.ResultTodoColor) {
        viewModel.setSelectedColor(item)
    }

    private fun selectedColorObserve() {
        viewModel.selectedColor.observe(viewLifecycleOwner) {
            binding.fgAddTodoCardChangeFragment.setCardBackgroundColor(it.code.toColorInt())

            when (it.id) {
                in 1..13 -> setColorTextBlack()
                else -> setColorTextWhite()
            }
        }
    }

    private fun observeSelectedDates() {
        viewModel.selectedDates.observe(viewLifecycleOwner) {
            it.setDateChip()
        }

        viewModel.selectedDate.observe(viewLifecycleOwner) {
            viewModel.onChangeSelectedDate()
        }
    }

    private fun List<String>.setDateChip() {
        binding.fgAddTodoChipgroupChip.apply {
            removeAllViewsInLayout()

            for (i in this@setDateChip) {
                this.addView(setChip(i))
            }

            if(this.size > 0) check(this[0].id)
        }
    }

    private fun setChip(title: String): Chip {
        return Chip(requireContext()).apply {
            text = title.convertToMonthDate()
            tag = title
            setTextAppearance(R.style.ChipTextStyle)
            setChipBackgroundColorResource(R.color.selector_add_todo_chip)
            isCheckable = true
            isCheckedIconVisible = false
        }
    }


    private fun getDateRangeForChip() {
        viewModel.setSelectedDates(mutableListOf<String>().apply {
            var standard = viewModel.startDate.value!!

            while (standard <= viewModel.endDate.value) {
                this.add(standard.convertToDate())
                standard = standard.plusDays(1)
            }
        })
    }

    private fun String.convertToYearMonth(): LocalDate {
        val date = this.split("-")
        return LocalDate.of(date[0].toInt(), date[1].toInt(), date[2].toInt())
    }

    private fun String.convertToMonthDate(): String {
        return "${this.split(" ")[1]} ${this.split(" ")[2]}"
    }

    private fun LocalDate.convertToDate(): String {
        return "${this.year}년 ${this.monthValue}월 ${this.dayOfMonth}일"
    }

    fun openDatePickerDialog() {
        DialogAddTodoPickDate().show(requireActivity().supportFragmentManager, null)
    }

    fun openLocationSearchDialog() {
        DialogAddTodoSearchLocation().show(requireActivity().supportFragmentManager, null)
    }

    fun openAlarmActivity() {
        requestAddTodoAlarmActivity.launch(Intent(requireActivity(), AddTodoAlarmActivity::class.java).setType(viewModel.startDate.value!!.toString()).putExtra("TodoAlarm", viewModel.todoAlarmList.value as ArrayList))
    }

    private val requestAddTodoAlarmActivity =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                it.data!!.extras?.getStringArrayList("TodoAlarm")?.let {
                    viewModel.setTodoAlarmList(it as MutableList<String>) }
            }
        }

    private fun setColorTextWhite() {
        binding.fgAddTodoTvChangeColor.setTextColor(resources.getColor(R.color.white, null))
        binding.fgAddTodoIvChangeColor.imageTintList =
            ColorStateList.valueOf(resources.getColor(R.color.white, null))
    }

    private fun setColorTextBlack() {
        binding.fgAddTodoTvChangeColor.setTextColor(resources.getColor(R.color.black, null))
        binding.fgAddTodoIvChangeColor.imageTintList =
            ColorStateList.valueOf(resources.getColor(R.color.black, null))
    }
}