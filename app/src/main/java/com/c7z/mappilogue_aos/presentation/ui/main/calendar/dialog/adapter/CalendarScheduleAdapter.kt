package com.c7z.mappilogue_aos.presentation.ui.main.calendar.dialog.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.c7z.mappilogue_aos.data.remote.response.ResponseDetailDateScheduleData
import com.c7z.mappilogue_aos.databinding.ItemRvCalendarDateDetailSchedulesBinding
import com.c7z.mappilogue_aos.databinding.ItemRvCalendarDateDetailSchedulesWithoutAnyBinding

class CalendarScheduleAdapter
    (
    private val openModifyTodo: (Int) -> Unit,
    private val onOptionClicked: (Int) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var items = mutableListOf<ResponseDetailDateScheduleData.DetailDateScheduleData>()

    inner class CalendarScheduleViewHolder(private val binding: ItemRvCalendarDateDetailSchedulesBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ResponseDetailDateScheduleData.DetailDateScheduleData) {
            binding.data = item
            binding.itemRvCalendarDateDetailSchedulesTvTitle.setOnClickListener { openModifyTodo.invoke(item.scheduleId) }
            binding.itemRvCalendarDateDetailSchedulesIvMenu.setOnClickListener {
                onOptionClicked.invoke(
                    item.scheduleId
                )
            }
        }
    }

    inner class CalendarScheduleWithoutAnyViewHolder(private val binding: ItemRvCalendarDateDetailSchedulesWithoutAnyBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind() {

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return if (viewType == 0) CalendarScheduleViewHolder(
            ItemRvCalendarDateDetailSchedulesBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
        else CalendarScheduleWithoutAnyViewHolder(
            ItemRvCalendarDateDetailSchedulesWithoutAnyBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = if (items.size == 0) 1 else items.size

    override fun getItemViewType(position: Int): Int {
        return if (items.size == 0) 1 else 0
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (holder) {
            is CalendarScheduleViewHolder -> holder.bind(items[position])
            is CalendarScheduleWithoutAnyViewHolder -> holder.bind()
        }
    }
}