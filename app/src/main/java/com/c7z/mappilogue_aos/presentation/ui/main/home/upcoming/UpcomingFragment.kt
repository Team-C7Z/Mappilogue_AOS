package com.c7z.mappilogue_aos.presentation.ui.main.home.upcoming

import android.content.Context
import android.graphics.Rect
import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.c7z.mappilogue_aos.R
import com.c7z.mappilogue_aos.data.data.HomeMarkedItem
import com.c7z.mappilogue_aos.databinding.FragmentUpcomingBinding
import com.c7z.mappilogue_aos.presentation.ui.main.home.upcoming.adapter.UpcomingMarkedRecyclerViewAdapter
import com.c7z.mappilogue_aos.presentation.ui.main.home.upcoming.adapter.UpcomingRecyclerViewAdapter
import com.c7z.mappilogue_aos.presentation.ui.main.home.upcoming.viewmodel.UpComingViewModel
import com.c7z.mappilogue_aos.presentation.util.DpToPxConverter
import com.c7z.mappilogue_aos.presentation.util.ItemDecorator

class UpcomingFragment: Fragment() {
    private lateinit var binding: FragmentUpcomingBinding
    private val viewModel : UpComingViewModel by viewModels()

    private val todayAdapter by lazy {
        UpcomingRecyclerViewAdapter(viewModel.upcomingList.value)
    }

    private val todayMarkedAdapter by lazy {
        UpcomingMarkedRecyclerViewAdapter(viewModel.upcomingMarkedList.value, requireActivity())
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_upcoming, container, false)

        initBinding()
        initUi()

        return binding.root
    }

    private fun initBinding() {
        binding.fragment = this
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
    }

    private fun initUi() {
        initList()
        initView()
    }

    private fun initList() {
        viewModel.upcomingMarkedList.observe(viewLifecycleOwner) {
            if(it.size > 3) it.take(3) as ArrayList<HomeMarkedItem>
        }
    }

    private fun initView() {
        initUpcomingRv()
        initUpcomingMarkedRv()
    }

    private fun initUpcomingRv() {
        binding.upcomingRv.setHasFixedSize(true)
        binding.upcomingRv.adapter = todayAdapter
        binding.upcomingRv.addItemDecoration(ItemDecorator.VerticalItemDecorator(DpToPxConverter.dpToPx(16f, requireContext())))
    }

    private fun initUpcomingMarkedRv() {
        binding.upcomingMarkedRv.setHasFixedSize(true)
        binding.upcomingMarkedRv.adapter = todayMarkedAdapter
        binding.upcomingMarkedRv.addItemDecoration(ItemDecorator.HorizontalItemDecorator(DpToPxConverter.dpToPx(14f, requireContext())))
    }
}