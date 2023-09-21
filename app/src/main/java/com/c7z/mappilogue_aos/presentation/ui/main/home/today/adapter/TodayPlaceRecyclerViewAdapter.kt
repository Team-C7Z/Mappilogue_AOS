package com.c7z.mappilogue_aos.presentation.ui.main.home.today.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.c7z.mappilogue_aos.data.data.HomeTodayPlaceItem
import com.c7z.mappilogue_aos.databinding.ItemRvTodayPlaceBinding

class TodayPlaceRecyclerViewAdapter(private val homeTodayPlaceItem: List<HomeTodayPlaceItem>)
    :RecyclerView.Adapter<TodayPlaceRecyclerViewAdapter.ViewHolder>(){

    inner class ViewHolder(val binding: ItemRvTodayPlaceBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(todayPlaceItem: HomeTodayPlaceItem) {
            binding.itemTodayPlaceTv.text = todayPlaceItem.place
            binding.itemTodayPlaceTimeTv.text = todayPlaceItem.time
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemRvTodayPlaceBinding = ItemRvTodayPlaceBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(homeTodayPlaceItem[position])
    }

    override fun getItemCount(): Int {
        return homeTodayPlaceItem.size
    }


}