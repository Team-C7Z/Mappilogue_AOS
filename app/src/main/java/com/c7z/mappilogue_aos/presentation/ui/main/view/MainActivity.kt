package com.c7z.mappilogue_aos.presentation.ui.main.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.c7z.mappilogue_aos.R
import com.c7z.mappilogue_aos.databinding.ActivityMainBinding
import com.c7z.mappilogue_aos.presentation.ui.main.calendar.CalendarFragment
import com.c7z.mappilogue_aos.presentation.ui.main.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private val viewModel : MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        initAttachFragment()
    }

    private fun initAttachFragment() {
        supportFragmentManager.beginTransaction().replace(R.id.main_layout_container, CalendarFragment()).commit()
    }
}