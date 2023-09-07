package com.c7z.mappilogue_aos.presentation.ui.home.today

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
import com.c7z.mappilogue_aos.data.data.HomeTodayItem
import com.c7z.mappilogue_aos.data.data.HomeTodayPlaceItem
import com.c7z.mappilogue_aos.databinding.FragmentTodayBinding
import com.c7z.mappilogue_aos.presentation.ui.home.today.adapter.TodayMarkedRecyclerViewAdapter
import com.c7z.mappilogue_aos.presentation.ui.home.today.adapter.TodayRecyclerViewAdapter

class TodayFragment: Fragment(){
    private lateinit var binding: FragmentTodayBinding

    private var homeList = arrayListOf<HomeTodayItem>()
    private var homePlaceList1 = arrayListOf<HomeTodayPlaceItem>()
    private var homePlaceList2 = arrayListOf<HomeTodayPlaceItem>()
    private var homeMarkedList = arrayListOf<HomeMarkedItem>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_today, container, false)

        initList()
        initBinding()
        initView()

        return binding.root
    }

    private fun initList() {
        homePlaceList1.add(HomeTodayPlaceItem("춘배식당", "9:00 AM"))
        homePlaceList1.add(HomeTodayPlaceItem("카페 문", "10:00 AM"))
        homePlaceList1.add(HomeTodayPlaceItem("아주 건강해지는 제이슨 건강원 냠냠냠냠냠냠냠냠냠냠냠냠냠냠냠", "9:00 PM"))

        homePlaceList2.add(HomeTodayPlaceItem("춘배식당", "9:00 AM"))
        homePlaceList2.add(HomeTodayPlaceItem("카페 문", "10:00 AM"))

        homeList.add(HomeTodayItem("아빠랑 데이트💕‍", homePlaceList1, "B1E9BE", true))
        homeList.add(HomeTodayItem("러닝메이트 회의🏃‍", homePlaceList2, "BAD7FA",true))

        homeMarkedList.add(HomeMarkedItem("2일 전", "롯데월드", R.drawable.example_bg_mark, "BAD7FA"))
        homeMarkedList.add(HomeMarkedItem("4월 28일", "서울숲 소풍", null, "FFAF82"))

        // 마크한 기록 3개로 제한
        if(homeMarkedList.count() > 3) homeMarkedList = homeMarkedList.take(3) as ArrayList<HomeMarkedItem>
    }

    private fun initBinding() {
        binding.fragment = this
        binding.lifecycleOwner = viewLifecycleOwner
    }

    private fun initView() {
        if(homeList.isEmpty()) {
            binding.todayRv.visibility = View.GONE
            binding.todayEmptyContainerCl.visibility = View.VISIBLE
        }
        else {
            binding.todayRv.visibility = View.VISIBLE
            binding.todayEmptyContainerCl.visibility = View.GONE

            binding.todayRv.layoutManager = LinearLayoutManager(requireContext())
            binding.todayRv.adapter = TodayRecyclerViewAdapter(homeList, requireContext())
            binding.todayRv.addItemDecoration(VerticalItemDecorator(dpToPx(16f, requireContext())))
        }

        binding.todayMarkedRv.setHasFixedSize(true)
        binding.todayMarkedRv.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
        binding.todayMarkedRv.adapter = TodayMarkedRecyclerViewAdapter(homeMarkedList, requireContext())
        binding.todayMarkedRv.addItemDecoration(HorizontalItemDecorator(dpToPx(14f, requireContext())))
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