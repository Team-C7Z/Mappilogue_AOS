package com.c7z.mappilogue_aos.presentation.ui.sign_up_result

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.c7z.mappilogue_aos.R
import com.c7z.mappilogue_aos.databinding.ActivitySignUpResultBinding
import com.c7z.mappilogue_aos.presentation.ui.main.MainActivity
import kotlinx.coroutines.delay

class SignUpResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up_result)
    }

    override fun onResume() {
        super.onResume()
        setLogic()
    }

    private fun setLogic() {
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, MainActivity::class.java)).also { finish() }
        }, 3000)
    }
}