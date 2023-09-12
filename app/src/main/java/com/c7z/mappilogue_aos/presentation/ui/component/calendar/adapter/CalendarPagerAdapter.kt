package com.c7z.mappilogue_aos.presentation.ui.component.calendar.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.c7z.mappilogue_aos.databinding.ComponentCalendarLayerBinding
import com.c7z.mappilogue_aos.presentation.ui.component.calendar.view.ComponentCalendarLayer
import java.time.LocalDate

class CalendarPagerAdapter (fragment : Fragment): FragmentStateAdapter(fragment) {
    var months = MutableList(100, ::init)
    override fun getItemCount(): Int = months.size

    override fun createFragment(position: Int): Fragment {
        return ComponentCalendarLayer(months[position])
    }
    private fun init(noinline : Int) : LocalDate {
        return LocalDate.now()
    }

}