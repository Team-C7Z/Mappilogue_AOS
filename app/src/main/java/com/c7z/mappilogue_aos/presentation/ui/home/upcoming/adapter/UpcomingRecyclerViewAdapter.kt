package com.c7z.mappilogue_aos.presentation.ui.home.upcoming.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.c7z.mappilogue_aos.data.data.HomeUpcomingItem
import com.c7z.mappilogue_aos.databinding.ItemRvUpcomingBinding

class UpcomingRecyclerViewAdapter(private val homeUpcomingItem: ArrayList<HomeUpcomingItem>?)
    :RecyclerView.Adapter<UpcomingRecyclerViewAdapter.ViewHolder>(){

    inner class ViewHolder(val binding: ItemRvUpcomingBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(upcomingItem: HomeUpcomingItem) {
            binding.itemUpcomingDateTv.text = upcomingItem.date

            if(upcomingItem.time != null) {
                binding.itemUpcomingTimeContainerCl.visibility = View.VISIBLE
                binding.itemUpcomingTimeTv.text = upcomingItem.time
            } else binding.itemUpcomingTimeContainerCl.visibility = View.GONE

            binding.itemUpcomingTitleTv.text = upcomingItem.title
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        Log.d("TodayRecyclerAdapter", "onCreateViewHolder()")
        val binding: ItemRvUpcomingBinding = ItemRvUpcomingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UpcomingRecyclerViewAdapter.ViewHolder, position: Int) {
        holder.bind(homeUpcomingItem!![position])
    }

    override fun getItemCount(): Int {
        return homeUpcomingItem!!.size
    }
}