package com.c7z.mappilogue_aos.presentation.ui.notification_setting

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.c7z.mappilogue_aos.R
import com.c7z.mappilogue_aos.databinding.ActivityNotificationSettingBinding
import com.c7z.mappilogue_aos.presentation.ui.notification_setting.adapter.NotificationSettingAdapter
import com.c7z.mappilogue_aos.presentation.ui.notification_setting.viewmodel.NotificationSettingViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NotificationSettingActivity : AppCompatActivity() {
    private lateinit var binding : ActivityNotificationSettingBinding
    private val viewModel : NotificationSettingViewModel by viewModels()

    private val notificationAdapter : NotificationSettingAdapter by lazy {
        NotificationSettingAdapter(viewModel.returnNotificationTitles(), ::onCheckClicked)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_notification_setting)

        initBinding()
        initUi()
        initObserve()
    }

    private fun initBinding() {
        binding.activity = this
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
    }

    private fun initUi() {
        binding.notificationSettingRvNotification.apply {
            adapter = notificationAdapter
            itemAnimator = null
        }
    }

    private fun initObserve() {
        observeNotificationData()
    }

    private fun observeNotificationData() {
        viewModel.acceptTotalNotification.observe(this) {
            notificationAdapter.setClickable = !it.contains("IN")
            //notificationAdapter.notifyDataSetChanged()
        }

        viewModel.notificationCheckItems.observe(this) {
            notificationAdapter.notificationCheckItems = it
            notificationAdapter.notifyItemRangeChanged(0, it.size)
        }
    }

    fun onTotalAcceptClicked() {
        viewModel.changeTotalNotificationStatus(binding.notificationSettingCheckboxTotalNotification.isChecked)
    }

    private fun onCheckClicked(position : Int, checked : Boolean) {
        viewModel.changeEachNotificationStatus(position, checked)
    }

    fun finishActivity() {
        finish()
    }
}