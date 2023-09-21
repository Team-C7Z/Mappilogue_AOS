package com.c7z.mappilogue_aos.presentation.ui.main.home.upcoming.adapter

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.c7z.mappilogue_aos.data.data.HomeMarkedItem
import com.c7z.mappilogue_aos.databinding.ItemRvMarkedBinding

class UpcomingMarkedRecyclerViewAdapter(private val upcomingMarkedItem: ArrayList<HomeMarkedItem>?, private val context: Context)
    :RecyclerView.Adapter<UpcomingMarkedRecyclerViewAdapter.ViewHolder>(){

    inner class ViewHolder(val binding: ItemRvMarkedBinding): RecyclerView.ViewHolder(binding.root) {
        fun add() {
            binding.itemMarkedAddMainContainerCl.visibility = View.VISIBLE
            binding.itemMarkedMainContainerCl.visibility = View.GONE
        }
        fun bind(markedItem: HomeMarkedItem) {
            binding.itemMarkedAddMainContainerCl.visibility = View.GONE
            binding.itemMarkedMainContainerCl.visibility = View.VISIBLE

            binding.itemMarkedDateTv.text = markedItem.date
            binding.itemMarkedTitleTv.text = markedItem.title
            if(markedItem.image != null) {
                Glide.with(context).load(markedItem.image).into(binding.itemMarkedBackgroundIv)
                binding.itemMarkedLogoIv.visibility = View.GONE
            }
            else binding.itemMarkedLogoIv.visibility = View.VISIBLE
            binding.itemMarkedMarkingBackgroundCl.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#${markedItem.markColor}"))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemRvMarkedBinding = ItemRvMarkedBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if(position == (upcomingMarkedItem?.size ?: 0)) holder.add()
        else holder.bind(upcomingMarkedItem!![position])
    }

    override fun getItemCount(): Int {
        return ((upcomingMarkedItem?.size ?: 0) + 1)
    }
}