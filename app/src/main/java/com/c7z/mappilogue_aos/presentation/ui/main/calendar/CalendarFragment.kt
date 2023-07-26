package com.c7z.mappilogue_aos.presentation.ui.main.calendar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.c7z.mappilogue_aos.R
import com.c7z.mappilogue_aos.databinding.FragmentCalendarBinding
import com.c7z.mappilogue_aos.presentation.ui.main.calendar.viewmodel.CalendarViewModel
import com.c7z.mappilogue_aos.presentation.ui.main.viewmodel.MainViewModel

class CalendarFragment : Fragment() {
    private lateinit var binding : FragmentCalendarBinding
    private val viewModel : CalendarViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_calendar, container, false)

        initBinding()
        observeComponent()

        return binding.root
    }

    private fun initBinding() {
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
    }

    private fun observeComponent() {
        binding.fgCalendarLayoutCalendar.requireYear.observe(viewLifecycleOwner) {
            viewModel.setRequireYear(it)
        }

        binding.fgCalendarLayoutCalendar.requireMonth.observe(viewLifecycleOwner) {
            viewModel.setRequireMonth(it)
        }
    }

}