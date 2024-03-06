package com.c7z.mappilogue_aos.presentation.ui.todo.alarm

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.c7z.mappilogue_aos.R
import com.c7z.mappilogue_aos.databinding.FragmentAddTodoAlarmBinding
import com.c7z.mappilogue_aos.presentation.ui.todo.viewmodel.AddTodoViewModel
import java.time.LocalDate

class AddTodoAlarmFragment : Fragment() {
    private lateinit var binding : FragmentAddTodoAlarmBinding
    private val todoViewModel : AddTodoViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_todo_alarm, container ,false)

        initUi()
        initObserve()

        return binding.root
    }

    private fun initUi() {
        initStartTimeText()
    }

    private fun initObserve() {
        observeStartDate()
    }

    private fun initStartTimeText() {
        binding.fgAddTodoAlarmTvTime.text = "9:00 AM"
    }

    private fun observeStartDate() {
        todoViewModel.startDate.observe(viewLifecycleOwner) {
            binding.fgAddTodoAlarmTvDate.text = "당일 (${it.convertToMonthDate()})"
        }
    }

    private fun LocalDate.convertToMonthDate(): String {
        return "${this.monthValue}월 ${this.dayOfMonth}일"
    }
}