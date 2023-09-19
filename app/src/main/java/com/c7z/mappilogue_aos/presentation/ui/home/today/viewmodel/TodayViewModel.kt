package com.c7z.mappilogue_aos.presentation.ui.home.today.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.c7z.mappilogue_aos.R
import com.c7z.mappilogue_aos.data.data.HomeMarkedItem
import com.c7z.mappilogue_aos.data.data.HomeTodayItem
import com.c7z.mappilogue_aos.data.data.HomeTodayPlaceItem

class TodayViewModel: ViewModel() {

    // 더미 데이터
    private var homePlaceList1 = arrayListOf(
        HomeTodayPlaceItem("춘배식당", "9:00 AM"),
        HomeTodayPlaceItem("카페 문", "10:00 AM"),
        HomeTodayPlaceItem("아주 건강해지는 제이슨 건강원 냠냠냠냠냠냠냠냠냠냠냠냠냠냠냠", "9:00 PM"))

    private var homePlaceList2 = arrayListOf(
        HomeTodayPlaceItem("춘배식당", "9:00 AM"),
        HomeTodayPlaceItem("카페 문", "10:00 AM"))

    private val _homeList = MutableLiveData<ArrayList<HomeTodayItem>>().apply {
        value = arrayListOf(
            HomeTodayItem("아빠랑 데이트💕‍", homePlaceList1, "B1E9BE", true),
            HomeTodayItem("러닝메이트 회의🏃‍", homePlaceList2, "BAD7FA",true)
        )
    }
    val homeList: LiveData<ArrayList<HomeTodayItem>> = _homeList

    private val _homeMarkedList = MutableLiveData<ArrayList<HomeMarkedItem>>().apply {
        value = arrayListOf(
            HomeMarkedItem("2일 전", "롯데월드", R.drawable.example_bg_mark, "BAD7FA"),
            HomeMarkedItem("4월 28일", "서울숲 소풍", null, "FFAF82")
        )
    }
    val homeMarkedList: LiveData<ArrayList<HomeMarkedItem>> = _homeMarkedList

    private val _homeListViewStatus = MutableLiveData<Boolean>(false)
    val homeListViewStatus : LiveData<Boolean> = _homeListViewStatus

    private fun getViewStatus() {
        _homeListViewStatus.value = !_homeList.value.isNullOrEmpty()
    }
}