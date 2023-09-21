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
        binding.upcomingRv.addItemDecoration(VerticalItemDecorator(DpToPxConverter.dpToPx(16f, requireContext())))
    }

    private fun initUpcomingMarkedRv() {
        binding.upcomingMarkedRv.setHasFixedSize(true)
        binding.upcomingMarkedRv.adapter = todayMarkedAdapter
        binding.upcomingMarkedRv.addItemDecoration(HorizontalItemDecorator(DpToPxConverter.dpToPx(14f, requireContext())))
    }

    inner class VerticalItemDecorator(private var spacing: Int): RecyclerView.ItemDecoration() {
        override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
            super.getItemOffsets(outRect, view, parent, state)
            if (parent.getChildAdapterPosition(view) != 0) outRect.top = spacing
        }
    }

    inner class HorizontalItemDecorator(private var spacing: Int): RecyclerView.ItemDecoration() {
        override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
            super.getItemOffsets(outRect, view, parent, state)
            if (parent.getChildAdapterPosition(view) != 0) outRect.left = spacing
        }
    }

    private fun dpToPx(dp: Float, context: Context): Int {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.resources.displayMetrics).toInt()
    }
}