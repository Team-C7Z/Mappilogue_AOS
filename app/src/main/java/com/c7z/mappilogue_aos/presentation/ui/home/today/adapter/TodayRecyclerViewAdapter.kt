package com.c7z.mappilogue_aos.presentation.ui.home.today.adapter

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.c7z.mappilogue_aos.data.data.HomeTodayItem
import com.c7z.mappilogue_aos.databinding.ItemRvTodayBinding

class TodayRecyclerViewAdapter(private val homeTodayItem: ArrayList<HomeTodayItem>?)
    :RecyclerView.Adapter<TodayRecyclerViewAdapter.ViewHolder>(){

    inner class ViewHolder(val binding: ItemRvTodayBinding): RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("NotifyDataSetChanged")
        fun bind(todayItem: HomeTodayItem) {
            binding.itemTodayTv.text = todayItem.title
            binding.itemTodayMainContainer.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#${todayItem.color}"))
            binding.itemTodayPlaceRv.adapter = TodayPlaceRecyclerViewAdapter(todayItem.todayPlaceItem)

            val isExpandable = todayItem.isExpandable
            if(isExpandable) {
                binding.itemTodayPlaceRv.visibility = View.VISIBLE
                ObjectAnimator.ofFloat(binding.itemTodayArrowIv, View.ROTATION, 0f).setDuration(5).start()
            } else {
                binding.itemTodayPlaceRv.visibility = View.GONE
                ObjectAnimator.ofFloat(binding.itemTodayArrowIv, View.ROTATION, -180f).setDuration(5).start()
            }
            binding.itemTodayPlaceRv.visibility = if(isExpandable) View.VISIBLE else View.GONE

            binding.itemTodayMainContainer.setOnClickListener {
                todayItem.isExpandable = !todayItem.isExpandable
                notifyDataSetChanged()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemRvTodayBinding = ItemRvTodayBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TodayRecyclerViewAdapter.ViewHolder, position: Int) {
        holder.bind(homeTodayItem!![position])
    }

    override fun getItemCount(): Int {
        return homeTodayItem?.size ?: 0
    }
}