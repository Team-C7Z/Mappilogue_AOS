package com.c7z.mappilogue_aos.presentation.ui.todo_alarm.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.c7z.mappilogue_aos.data.data.TodoAlarmData
import com.c7z.mappilogue_aos.databinding.ItemRvAddTodoAlarmAlarmsBinding
import com.google.android.material.snackbar.Snackbar

class AddTodoAlarmAdapter(
    private val onCloseClicked : (Int) -> Unit
) : RecyclerView.Adapter<AddTodoAlarmAdapter.AddTodoAlarmViewHolder>() {
    var alarmList = mutableListOf<TodoAlarmData>()
    inner class AddTodoAlarmViewHolder(private val binding : ItemRvAddTodoAlarmAlarmsBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item : TodoAlarmData) {
            binding.data = item
            binding.itemRvAddTodoAlarmAlarmsBtnRemove.setOnClickListener { onCloseClicked.invoke(absoluteAdapterPosition) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddTodoAlarmViewHolder {
        return AddTodoAlarmViewHolder(ItemRvAddTodoAlarmAlarmsBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int = alarmList.size

    override fun onBindViewHolder(holder: AddTodoAlarmViewHolder, position: Int) {
        holder.bind(alarmList[position])
    }
}