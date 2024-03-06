package com.c7z.mappilogue_aos.presentation.ui.splash.view

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.c7z.mappilogue_aos.presentation.ui.main.MainActivity
import com.c7z.mappilogue_aos.presentation.ui.onboarding.OnBoardingActivity
import com.c7z.mappilogue_aos.presentation.ui.permission.RequestPermissionActivity
import com.c7z.mappilogue_aos.presentation.ui.splash.view.viewmodel.SplashViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    private val viewModel : SplashViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        observeSignInState()

    }

    private fun observeSignInState() {
        viewModel.signInState.observe(this) {
            if(it == 200) {
                Thread.sleep(1000)
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            } else {
                Thread.sleep(1000)
                startActivity(Intent(this, RequestPermissionActivity::class.java))
                finish()
            }
        }
    }
}