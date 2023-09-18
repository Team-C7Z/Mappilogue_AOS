package com.c7z.mappilogue_aos.presentation.ui.sign_up_result

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.c7z.mappilogue_aos.databinding.ActivitySignUpResultBinding
import com.c7z.mappilogue_aos.presentation.ui.main.MainActivity

class SignUpResultActivity : AppCompatActivity() {
    private lateinit var binding : ActivitySignUpResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignUpResultBinding.inflate(layoutInflater)

        Thread.sleep(3000)
        startActivity(Intent(this, MainActivity::class.java)).also { finish() }
    }
}