package com.c7z.mappilogue_aos.presentation.ui.inquire

import android.content.ClipData
import android.content.ClipboardManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.c7z.mappilogue_aos.R
import com.c7z.mappilogue_aos.databinding.ActivityInquireBinding
import com.c7z.mappilogue_aos.presentation.ui.component.toast.ComponentAlertToast

class InquireActivity : AppCompatActivity() {
    private lateinit var binding : ActivityInquireBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_inquire)

        initBinding()
    }

    private fun initBinding() {
        binding.activity = this
    }

    fun onEmailCopyClicked() {
        val clipBoardManager = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
        clipBoardManager.setPrimaryClip(ClipData.newPlainText("", binding.inquireTvEmail.text))
        

        if(Build.VERSION.SDK_INT <= Build.VERSION_CODES.S_V2) {
            ComponentAlertToast().show(supportFragmentManager, "COPY_EMAIL")
        }
    }

    fun finishActivity() {
        finish()
    }
}