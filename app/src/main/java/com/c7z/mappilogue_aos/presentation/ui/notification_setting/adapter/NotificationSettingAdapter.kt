package com.c7z.mappilogue_aos.presentation.ui.notification_setting.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.recyclerview.widget.RecyclerView
import com.c7z.mappilogue_aos.databinding.ItemRvNotificationSettingBinding

class NotificationSettingAdapter (private val notificationTitles : List<String>,
private val itemClicked : (Int, Boolean) -> Unit): RecyclerView.Adapter<NotificationSettingAdapter.NotificationSettingViewHolder>() {
    var setClickable = true
    var notificationCheckItems = mutableListOf<String>()

    inner class NotificationSettingViewHolder(private val binding : ItemRvNotificationSettingBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(title : String) {
            binding.title = title
            if(notificationCheckItems.isNotEmpty()) {
                binding.itemRvNotificationSettingCheckbox.isChecked = !notificationCheckItems[absoluteAdapterPosition].contains("IN")
            }
            binding.itemRvNotificationSettingCheckbox.isEnabled = setClickable
            binding.itemRvNotificationSettingCheckbox.setOnClickListener { itemClicked.invoke(absoluteAdapterPosition, (it as CheckBox).isChecked) }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NotificationSettingAdapter.NotificationSettingViewHolder {
        return NotificationSettingViewHolder(
            ItemRvNotificationSettingBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = notificationTitles.size

    override fun onBindViewHolder(holder: NotificationSettingAdapter.NotificationSettingViewHolder, position: Int) {
        holder.bind(notificationTitles[position])
    }
}