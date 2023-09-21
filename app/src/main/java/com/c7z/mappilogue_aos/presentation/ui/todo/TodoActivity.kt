package com.c7z.mappilogue_aos.presentation.ui.todo

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.c7z.mappilogue_aos.R
import com.c7z.mappilogue_aos.databinding.ActivityTodoBinding
import com.c7z.mappilogue_aos.databinding.ComponentDialog2ButtonBinding
import com.c7z.mappilogue_aos.presentation.ui.component.dialog.ComponentDialogTwoButton
import com.c7z.mappilogue_aos.presentation.ui.todo.write_todo.AddTodoFragment
import com.c7z.mappilogue_aos.presentation.ui.todo.write_todo.viewmodel.AddTodoViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TodoActivity : AppCompatActivity() {
    private lateinit var binding : ActivityTodoBinding
    private val viewModel: AddTodoViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_todo)

        initBinding()
        initFragment()

    }

    private fun initBinding() {
        binding.activity = this
    }

    private fun initFragment() {
        supportFragmentManager.beginTransaction().replace(R.id.todo_layout_container, AddTodoFragment(), intent.type).commit()
    }

    fun onCloseClicked() {
        ComponentDialogTwoButton(::finishActivity, "RED").show(supportFragmentManager, "STOP_WRITING_TODO")
    }

    private fun finishActivity() {
        finish()
    }
}