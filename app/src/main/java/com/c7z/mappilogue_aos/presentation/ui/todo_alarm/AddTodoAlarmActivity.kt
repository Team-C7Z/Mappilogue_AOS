package com.c7z.mappilogue_aos.presentation.ui.todo_alarm

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.c7z.mappilogue_aos.R
import com.c7z.mappilogue_aos.data.data.TodoAlarmData
import com.c7z.mappilogue_aos.databinding.ActivityAddTodoAlarmBinding
import com.c7z.mappilogue_aos.presentation.ui.todo.TodoActivity
import com.c7z.mappilogue_aos.presentation.ui.todo_alarm.adapter.AddTodoAlarmAdapter
import com.c7z.mappilogue_aos.presentation.ui.todo_alarm.dialog.DialogAddTodoAlarmDate
import com.c7z.mappilogue_aos.presentation.ui.todo_alarm.dialog.DialogAddTodoAlarmSetTime
import com.c7z.mappilogue_aos.presentation.ui.todo_alarm.viewmodel.AddTodoAlarmViewModel

class AddTodoAlarmActivity : AppCompatActivity() {
    private lateinit var binding : ActivityAddTodoAlarmBinding
    private val viewModel : AddTodoAlarmViewModel by viewModels()

    private val alarmListAdapter by lazy {
        AddTodoAlarmAdapter(::onRemoveAlarmClicked)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_todo_alarm)

        initBinding()
        initUi()
        initObserve()
    }

    private fun initBinding() {
        binding.activity = this
    }


    private fun initUi() {
        initStartDateText()
        initAlarmRv()
    }

    private fun initObserve() {
        observeDates()
        observeTimes()
        observeAlarmList()
    }

    private fun initStartDateText() {
        intent.type?.let {
            viewModel.setStandardDates(it, "당일 (${it.convertToMonthDate()})")
        }
        intent.getStringArrayListExtra("TodoAlarm")?.let{
            viewModel.initSelectedAlarm(it.map { TodoAlarmData(it.convertWithTime(), it) } as MutableList<TodoAlarmData>)
        }

    }

    private fun initAlarmRv() {
        binding.addTodoAlarmRvAlarmList.adapter = alarmListAdapter
    }

    private fun observeDates() {
        viewModel.displayDate.observe(this) {
            binding.addTodoAlarmTvDate.text = it
        }
    }

    private fun observeTimes() {
        viewModel.selectedTime.observe(this) {
            binding.addTodoAlarmTvTime.text = it
        }
    }

    private fun String.convertToMonthDate(): String {
        return "${this.split("-")[1]}월 ${this.split("-")[2]}일"
    }

    private fun String.convertWithTime() : String {
        return if(this.contains("T")) {
            val arr = this.split("T")[0]
            val arr2 = this.split("T")[1]
            "${arr.convertToMonthDate()} ${arr2.setTime()}"
        }
        else {
            val arr = this.split(" ")
            "${arr[0].convertToMonthDate()} ${arr[1]} ${arr[2]}"
        }
    }

    private fun String.setTime() : String {
        val arr = this.split(":")
        return if(this.contains("AM") || this.contains("PM")) this
        else if (arr[0].toInt() > 12) "${(arr[0].toInt() - 12)}:${arr[1]} PM"
        else "${arr[0]}:${arr[1]} AM"
    }

    fun openDateDialog() {
        DialogAddTodoAlarmDate().show(supportFragmentManager, intent.type)
    }

    fun openTimeDialog() {
        DialogAddTodoAlarmSetTime(::getAlarmTime).show(supportFragmentManager, "")
    }

    private fun getAlarmTime(time : String) {
        viewModel.setSelectedTimes(time)
    }

    fun onClickAddAlarm() {
        viewModel.notifySelectedAlarm(alarmListAdapter.alarmList, null)
        viewModel.initAlarm()
    }

    private fun onRemoveAlarmClicked(position : Int) {
        viewModel.notifySelectedAlarm(alarmListAdapter.alarmList, position)
    }

    private fun observeAlarmList() {
        viewModel.selectedAlarmList.observe(this) {
            alarmListAdapter.alarmList = it
            alarmListAdapter.notifyDataSetChanged()
        }
    }

    fun onBackPress() {
        setResult(RESULT_OK, Intent(this, TodoActivity::class.java).putExtra("TodoAlarm", viewModel.selectedAlarmList.value?.map { it.alarmTime } as ArrayList))
        finish()
    }
}