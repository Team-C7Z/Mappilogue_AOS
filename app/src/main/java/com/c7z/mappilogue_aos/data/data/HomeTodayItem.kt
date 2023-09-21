package com.c7z.mappilogue_aos.data.data

data class HomeTodayItem(
    val title: String,
    val todayPlaceItem: ArrayList<HomeTodayPlaceItem>,
    val color: String,
    var isExpandable: Boolean = false,
)
