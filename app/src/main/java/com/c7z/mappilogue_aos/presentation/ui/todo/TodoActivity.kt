package com.c7z.mappilogue_aos.presentation.ui.todo

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.c7z.mappilogue_aos.R
import com.c7z.mappilogue_aos.databinding.ActivityTodoBinding
import com.c7z.mappilogue_aos.databinding.ComponentDialog2ButtonBinding
import com.c7z.mappilogue_aos.presentation.ui.component.dialog.ComponentDialogTwoButton
import com.c7z.mappilogue_aos.presentation.ui.todo.write_todo.AddTodoFragment
import com.c7z.mappilogue_aos.presentation.ui.todo.viewmodel.AddTodoViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TodoActivity() : AppCompatActivity() {
    private lateinit var binding : ActivityTodoBinding
    private val viewModel: AddTodoViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_todo)

        initBinding()
        checkFlags()
        initFragment()
        initObserve()
    }

    private fun initBinding() {
        binding.activity = this
    }

    private fun checkFlags() {
        if(intent.flags != 0) {
            viewModel.requestTodoDetailData(intent.flags)
            viewModel.setScheduleId(intent.flags)
        }
    }

    private fun initFragment() {
        supportFragmentManager.beginTransaction().replace(R.id.todo_layout_container, AddTodoFragment(), intent.type).commit()
    }

    private fun initObserve() {
        observeUploadTodoStatus()
    }

    private fun observeUploadTodoStatus() {
        viewModel.todoUploadState.observe(this) {
            if(it == 201 || it == 204) finishActivity(null)
        }
    }

    fun onSaveClicked() {
        viewModel.requestSaveTodo()
    }

    fun onCloseClicked() {
        ComponentDialogTwoButton(null, ::finishActivity, "RED").show(supportFragmentManager, "STOP_WRITING_TODO")
    }

    private fun finishActivity(noinline : Int?) {
        finish()
    }
}