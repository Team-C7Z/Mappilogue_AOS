package com.c7z.mappilogue_aos.presentation.ui.todo.write_todo

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

class AddTodoFragment : Fragment() {
    private lateinit var binding: FragmentAddTodoBinding
    private val viewModel: AddTodoViewModel by activityViewModels()

    private val colorAdapter by lazy {
        AddTodoColorAdapter(::setSelectedColor).also { it.colors = colors() }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_todo, container, false)

        initBinding()
        initUi()

        Log.e("----", "onCreateView: $tag", )

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

    private fun initColorRv() {
        binding.fgAddTodoRvColors.adapter = colorAdapter
    }

    private fun initDates() {
        viewModel.setStartDate(tag!!)
        viewModel.setEndDate(tag!!)

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
        }
    }

    private fun String.convertToDate() : String {
        val dates = this.split("-").map { it.toInt() }
        return "${dates[0]}년 ${dates[1]}월 ${dates[2]}일"
    }

    fun openDatePickerDialog(type : String) {
        val target = if(type == "Start") viewModel.startDate.value!! else viewModel.endDate.value!!
        DialogAddTodoPickDate(type, target).show(requireActivity().supportFragmentManager, null)
    }

    /** Dummy **/
    fun colors(): MutableList<ResponseTodoColor.ResultTodoColor> =
        mutableListOf<ResponseTodoColor.ResultTodoColor>().apply {
            add(ResponseTodoColor.ResultTodoColor(0, "1", "#FFA1A1"))
            add(ResponseTodoColor.ResultTodoColor(0, "1", "#FFAF82"))
            add(ResponseTodoColor.ResultTodoColor(0, "1", "#F5DC82"))
            add(ResponseTodoColor.ResultTodoColor(0, "1", "#F0F1B0"))
            add(ResponseTodoColor.ResultTodoColor(0, "1", "#CAEDA8"))
            add(ResponseTodoColor.ResultTodoColor(0, "1", "#B1E9BE"))
            add(ResponseTodoColor.ResultTodoColor(0, "1", "#B2EAD6"))
            add(ResponseTodoColor.ResultTodoColor(0, "1", "#B2EBE7"))
            add(ResponseTodoColor.ResultTodoColor(0, "1", "#ABD9F3"))
            add(ResponseTodoColor.ResultTodoColor(0, "1", "#BAD7FA"))
            add(ResponseTodoColor.ResultTodoColor(0, "1", "#E6C3F2"))
            add(ResponseTodoColor.ResultTodoColor(0, "1", "#F0B4D5"))
            add(ResponseTodoColor.ResultTodoColor(0, "1", "#C9C6C2"))
            add(ResponseTodoColor.ResultTodoColor(0, "1", "#9B9791"))
            add(ResponseTodoColor.ResultTodoColor(0, "1", "#404040"))
        }
}