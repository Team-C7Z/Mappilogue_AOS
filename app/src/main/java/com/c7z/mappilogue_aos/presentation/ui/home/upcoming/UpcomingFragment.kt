package com.c7z.mappilogue_aos.presentation.ui.home.upcoming

import android.content.Context
import android.graphics.Rect
import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.c7z.mappilogue_aos.R
import com.c7z.mappilogue_aos.data.data.HomeMarkedItem
import com.c7z.mappilogue_aos.data.data.HomeUpcomingItem
import com.c7z.mappilogue_aos.databinding.FragmentUpcomingBinding
import com.c7z.mappilogue_aos.presentation.ui.home.upcoming.adapter.UpcomingMarkedRecyclerViewAdapter
import com.c7z.mappilogue_aos.presentation.ui.home.upcoming.adapter.UpcomingRecyclerViewAdapter

class UpcomingFragment: Fragment() {
    private lateinit var binding: FragmentUpcomingBinding

    private var upcomingList = arrayListOf<HomeUpcomingItem>()
    private var upcomingMarkedList = arrayListOf<HomeMarkedItem>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_upcoming, container, false)

        initList()
        initBinding()
        initView()

        return binding.root
    }

    private fun initList() {
        upcomingList.add(HomeUpcomingItem("5월 13일", null, "찬희랑 저녁 약속💕"))
        upcomingList.add(HomeUpcomingItem("5월 16일", "10:00 AM 시작", "말이 길어진다면 말줄임표로 대체해 주세요 이렇게!!!!!!"))

        upcomingMarkedList.add(HomeMarkedItem("2일 전", "롯데월드", R.drawable.example_bg_mark, "BAD7FA"))
        upcomingMarkedList.add(HomeMarkedItem("4월 28일", "서울숲 소풍", null, "FFAF82"))

        // 마크한 기록 3개로 제한
        if(upcomingMarkedList.count() > 3) upcomingMarkedList = upcomingMarkedList.take(3) as ArrayList<HomeMarkedItem>
    }

    private fun initBinding() {
        binding.fragment = this
        binding.lifecycleOwner = viewLifecycleOwner
    }

    private fun initView() {
        if(upcomingList.isEmpty()) {
            binding.upcomingRv.visibility = View.GONE
            binding.upcomingEmptyContainerCl.visibility = View.VISIBLE
        } else {
            binding.upcomingRv.visibility = View.VISIBLE
            binding.upcomingEmptyContainerCl.visibility = View.GONE

            binding.upcomingRv.setHasFixedSize(true)
            binding.upcomingRv.layoutManager = LinearLayoutManager(requireContext())
            binding.upcomingRv.adapter = UpcomingRecyclerViewAdapter(upcomingList, requireContext())
            binding.upcomingRv.addItemDecoration(VerticalItemDecorator(dpToPx(8f, requireContext())))
        }

        binding.upcomingMarkedRv.setHasFixedSize(true)
        binding.upcomingMarkedRv.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
        binding.upcomingMarkedRv.adapter = UpcomingMarkedRecyclerViewAdapter(upcomingMarkedList, requireContext())
        binding.upcomingMarkedRv.addItemDecoration(HorizontalItemDecorator(dpToPx(14f, requireContext())))
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