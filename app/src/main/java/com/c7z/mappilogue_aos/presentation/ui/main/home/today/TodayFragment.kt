package com.c7z.mappilogue_aos.presentation.ui.main.home.today

import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.c7z.mappilogue_aos.R
import com.c7z.mappilogue_aos.data.data.HomeMarkedItem
import com.c7z.mappilogue_aos.databinding.FragmentTodayBinding
import com.c7z.mappilogue_aos.presentation.ui.main.home.today.adapter.TodayMarkedRecyclerViewAdapter
import com.c7z.mappilogue_aos.presentation.ui.main.home.today.adapter.TodayRecyclerViewAdapter
import com.c7z.mappilogue_aos.presentation.ui.main.home.today.viewmodel.TodayViewModel
import com.c7z.mappilogue_aos.presentation.util.DpToPxConverter
import com.c7z.mappilogue_aos.presentation.util.ItemDecorator

class TodayFragment: Fragment(){
    private lateinit var binding: FragmentTodayBinding
    private val viewModel : TodayViewModel by viewModels()

    private val todayAdapter by lazy {
        TodayRecyclerViewAdapter(viewModel.homeList.value)
    }

    private val todayMarkedAdapter by lazy {
        TodayMarkedRecyclerViewAdapter(viewModel.homeMarkedList.value, requireActivity())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_today, container, false)

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
        viewModel.homeMarkedList.observe(viewLifecycleOwner) {
            if(it.size > 3) it.take(3) as ArrayList<HomeMarkedItem>
        }
    }

    private fun initView() {
        initTodayRv()
        initTodayMarkedRv()
    }

    private fun initTodayRv() {
        binding.todayRv.adapter = todayAdapter
        binding.todayRv.addItemDecoration(ItemDecorator.VerticalItemDecorator(DpToPxConverter.dpToPx(16f, requireContext())))
    }

    private fun initTodayMarkedRv() {
        binding.todayMarkedRv.setHasFixedSize(true)
        binding.todayMarkedRv.adapter = todayMarkedAdapter
        binding.todayMarkedRv.addItemDecoration(ItemDecorator.HorizontalItemDecorator(DpToPxConverter.dpToPx(14f, requireContext())))
    }
}