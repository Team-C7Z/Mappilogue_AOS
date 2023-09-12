package com.c7z.mappilogue_aos.presentation.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.c7z.mappilogue_aos.R
import com.c7z.mappilogue_aos.databinding.ActivityMainBinding
import com.c7z.mappilogue_aos.presentation.ui.home.HomeFragment
import com.c7z.mappilogue_aos.presentation.ui.main.calendar.CalendarFragment
import com.c7z.mappilogue_aos.presentation.ui.main.viewmodel.MainViewModel
import com.c7z.mappilogue_aos.presentation.ui.meet.MeetFragment
import com.c7z.mappilogue_aos.presentation.ui.memorize.MemorizeFragment
import com.c7z.mappilogue_aos.presentation.ui.mypage.MypageFragment
import com.c7z.mappilogue_aos.presentation.ui.todo.TodoActivity

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel : MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        initBinding()
        initNavi()
    }

    private fun initBinding() {
        binding.activity = this
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
    }

    private fun initNavi() {
        binding.mainNaviBnv.apply {
            setOnItemSelectedListener {
                when(it.itemId) {
                    R.id.main_bottom_home -> changeFragment(HomeFragment(), true, false, "")
                    R.id.main_bottom_calender -> changeFragment(CalendarFragment(), false, true, "캘린더")
                    R.id.main_bottom_meet -> changeFragment(MeetFragment(), false, false, "")
                    R.id.main_bottom_memorize -> changeFragment(MemorizeFragment(), false, true, "기록")
                    R.id.main_bottom_mypage -> changeFragment(MypageFragment(), false, true, "마이페이지")
                }
                return@setOnItemSelectedListener true
            }
            selectedItemId = R.id.main_bottom_home
        }
    }

    private fun changeFragment(fragment: Fragment, logoTag: Boolean, titleTag: Boolean, title: String) {
        viewModel.setLogoTag(logoTag)
        viewModel.setTitleTag(titleTag, title)
        supportFragmentManager.beginTransaction().replace(R.id.main_layout_container, fragment).commit()
    }

    fun openTodoActivity(date : String?) {
        startActivity(Intent(this, TodoActivity::class.java).setType(date))
    }

}