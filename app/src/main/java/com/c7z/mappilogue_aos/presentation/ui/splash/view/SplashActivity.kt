package com.c7z.mappilogue_aos.presentation.ui.splash.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.c7z.mappilogue_aos.presentation.ui.main.view.MainActivity
import com.c7z.mappilogue_aos.presentation.ui.onboarding.OnBoardingActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //:TODO 임시로 1초 정지 후 메인화면 이동 -> 추후 자동로그인 이후 이동으로 변경 예정
        Thread.sleep(1000)
        startActivity(Intent(this, OnBoardingActivity::class.java))
    }
}