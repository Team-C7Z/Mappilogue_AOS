package com.c7z.mappilogue_aos.presentation.ui.todo.write_todo

import android.content.res.ColorStateList
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.toColorInt
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.c7z.mappilogue_aos.R
import com.c7z.mappilogue_aos.data.remote.response.ResponseTodoColor
import com.c7z.mappilogue_aos.databinding.FragmentAddTodoBinding
import com.c7z.mappilogue_aos.presentation.ui.todo.write_todo.adapter.AddTodoColorAdapter
import com.c7z.mappilogue_aos.presentation.ui.todo.write_todo.dialog.DialogAddTodoPickDate
import com.c7z.mappilogue_aos.presentation.ui.todo.write_todo.viewmodel.AddTodoViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDate
import java.time.Year
import java.time.YearMonth

@AndroidEntryPoint
class AddTodoFragment : Fragment() {
    private lateinit var binding: FragmentAddTodoBinding
    private val viewModel: AddTodoViewModel by activityViewModels()

    private val colorAdapter by lazy {
        AddTodoColorAdapter(::setSelectedColor)
    }

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
    }

    private fun initObserve() {
        observeScheduleColor()
    }

    private fun observeScheduleColor() {
        viewModel.colorData.observe(viewLifecycleOwner) {
            colorAdapter.colors = it.toMutableList()
            colorAdapter.notifyItemRangeChanged(0, it.size)
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

    private fun observeDates() {
        viewModel.startDate.observe(viewLifecycleOwner) {
            binding.fgAddTodoTvStartDate.text = it.convertToDate()
        }

        viewModel.endDate.observe(viewLifecycleOwner) {
            binding.fgAddTodoTvEndDate.text = it.convertToDate()
        }
    }

    private fun setSelectedColor(item: ResponseTodoColor.ResultTodoColor) {
        viewModel.setSelectedColor(item)
        selectedColorObserve()
    }

    private fun selectedColorObserve() {
        viewModel.selectedColor.observe(viewLifecycleOwner) {
            binding.fgAddTodoCardChangeFragment.setCardBackgroundColor(it.code.toColorInt())

            when(it.id) {
                in 1..13 -> setColorTextBlack()
                else -> setColorTextWhite()
            }
        }
    }

    private fun String.convertToYearMonth() : LocalDate {
        val date = this.split("-")
        return LocalDate.of(date[0].toInt(), date[1].toInt(), date[2].toInt())
    }

    private fun LocalDate.convertToDate() : String {
        return "${this.year}년 ${this.monthValue}월 ${this.dayOfMonth}일"
    }

    fun openDatePickerDialog() {
        DialogAddTodoPickDate().show(requireActivity().supportFragmentManager, null)
    }

    private fun setColorTextWhite() {
        binding.fgAddTodoTvChangeColor.setTextColor(resources.getColor(R.color.white, null))
        binding.fgAddTodoIvChangeColor.imageTintList = ColorStateList.valueOf(resources.getColor(R.color.white, null))
    }

    private fun setColorTextBlack() {
        binding.fgAddTodoTvChangeColor.setTextColor(resources.getColor(R.color.black, null))
        binding.fgAddTodoIvChangeColor.imageTintList = ColorStateList.valueOf(resources.getColor(R.color.black, null))
    }
}