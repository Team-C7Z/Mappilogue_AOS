package com.c7z.mappilogue_aos.presentation.ui.todo

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.c7z.mappilogue_aos.R
import com.c7z.mappilogue_aos.databinding.ActivityTodoBinding
import com.c7z.mappilogue_aos.presentation.ui.todo.write_todo.AddTodoFragment
import com.c7z.mappilogue_aos.presentation.ui.todo.write_todo.viewmodel.AddTodoViewModel

class TodoActivity : AppCompatActivity() {
    private lateinit var binding : ActivityTodoBinding
    private val viewModel: AddTodoViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_todo)

        initFragment()
    }

    private fun initFragment() {
        supportFragmentManager.beginTransaction().replace(R.id.todo_layout_container, AddTodoFragment(), intent.type).commit()
    }
}