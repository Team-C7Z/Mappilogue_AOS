package com.c7z.mappilogue_aos.presentation.ui.home.upcoming.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.c7z.mappilogue_aos.R
import com.c7z.mappilogue_aos.data.data.HomeMarkedItem
import com.c7z.mappilogue_aos.data.data.HomeUpcomingItem
class UpComingViewModel: ViewModel() {
    // 더미 데이터
    private val _upcomingList = MutableLiveData<ArrayList<HomeUpcomingItem>>().apply {
        value = arrayListOf(
            HomeUpcomingItem("5월 13일", null, "찬희랑 저녁 약속💕"),
            HomeUpcomingItem("5월 16일", "10:00 AM 시작", "말이 길어진다면 말줄임표로 대체해 주세요 이렇게!!!!!!")
        )
    }
    val upcomingList: LiveData<ArrayList<HomeUpcomingItem>> = _upcomingList

    private val _upcomingMarkedList = MutableLiveData<ArrayList<HomeMarkedItem>>().apply {
        value = arrayListOf(
            HomeMarkedItem("2일 전", "롯데월드", R.drawable.example_bg_mark, "BAD7FA"),
            HomeMarkedItem("4월 28일", "서울숲 소풍", null, "FFAF82")
        )
    }
    val upcomingMarkedList: LiveData<ArrayList<HomeMarkedItem>> = _upcomingMarkedList
}