package com.c7z.mappilogue_aos.presentation.ui.onboarding

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.c7z.mappilogue_aos.R
import com.c7z.mappilogue_aos.databinding.ActivityOnboardingBinding
import com.c7z.mappilogue_aos.presentation.ui.signin.SignInActivity
import com.c7z.mappilogue_aos.presentation.ui.onboarding.adapter.OnBoardingAdapter
import com.google.android.material.tabs.TabLayoutMediator

class OnBoardingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOnboardingBinding
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
            getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
            adapter = onBoardingAdapter
            this.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    binding.onboardingBtnStart.isEnabled =
                        position + 1 == onBoardingAdapter.itemCount
                    binding.onboardingTvTitle.text = onBoardingTitles()[position]
                }
            })
            TabLayoutMediator(binding.onboardingTabIndicator, this) { _, _ ->
            }.attach()
        }
    }

    private fun onBoardingTitles(): List<String> =
        listOf("일정에 여러 장소를 추가해 보세요",
            "내 일정을 기반으로 하루를 기록해 보세요",
            "지도로 내 기록을 한 눈에 볼 수 있어요")

    fun onNextClicked() {
        startActivity(Intent(this, SignInActivity::class.java))
        //startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}