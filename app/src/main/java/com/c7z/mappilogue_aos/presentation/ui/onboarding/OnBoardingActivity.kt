package com.c7z.mappilogue_aos.presentation.ui.onboarding

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.viewpager2.widget.ViewPager2
import com.c7z.mappilogue_aos.R
import com.c7z.mappilogue_aos.databinding.ActivityOnboardingBinding
import com.c7z.mappilogue_aos.presentation.ui.main.MainActivity
import com.c7z.mappilogue_aos.presentation.ui.onboarding.adapter.OnBoardingAdapter
import com.google.android.material.tabs.TabLayoutMediator

class OnBoardingActivity : AppCompatActivity() {
    private lateinit var binding : ActivityOnboardingBinding
    private val onBoardingAdapter by lazy {
        OnBoardingAdapter()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_onboarding)

        initBinding()
        initVP()
    }

    private fun initBinding() {
        binding.activity = this
    }

    private fun initVP() {
        binding.onboardingVpOnboard.apply {
            adapter = onBoardingAdapter
            this.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    binding.onboardingBtnStart.isEnabled = position + 1 == onBoardingAdapter.itemCount
                }
            })
            TabLayoutMediator(binding.onboardingTabIndicator, this) { _, _ ->
            }.attach()
        }
    }

    fun onNextClicked() {
        //startActivity(Intent(this, SignInActivity::class.java))
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}