package com.c7z.mappilogue_aos.presentation.ui.sign_out_reason

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.c7z.mappilogue_aos.R
import com.c7z.mappilogue_aos.databinding.ActivitySignOutReasonBinding
import com.c7z.mappilogue_aos.presentation.ui.main.MainActivity
import com.c7z.mappilogue_aos.presentation.ui.sign_out_reason.adapter.SignOutReasonAdapter
import com.c7z.mappilogue_aos.presentation.ui.sign_out_reason.viewmodel.SignOutReasonViewModel
import com.kakao.sdk.user.UserApiClient
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignOutReasonActivity : AppCompatActivity() {
    private lateinit var binding : ActivitySignOutReasonBinding
    private val viewModel : SignOutReasonViewModel by viewModels()
    private val reasonAdapter : SignOutReasonAdapter by lazy {
        SignOutReasonAdapter(viewModel.returnSignOutReasonItems(), ::manageCheckedItems)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_out_reason)

        initBinding()
        initUi()
        initObserve()

    }

    private fun initBinding() {
        binding.activity = this
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
    }

    private fun initUi() {
        initReasonRv()
    }

    private fun initReasonRv() {
        binding.signOutReasonRvChecklist.adapter = reasonAdapter
    }

    private fun initObserve() {
        observeSignOutStatus()
    }

    private fun manageCheckedItems(position : Int, isChecked : Boolean) {
        viewModel.manageCheckedPosition(position, isChecked)
    }

    fun requestSignOut() {
        viewModel.requestSignOut()
    }

    private fun observeSignOutStatus() {
        viewModel.signOutStatus.observe(this) {
            if(it == 200) {
                socialLogOut().also { viewModel.removeUserDataAtLocal() }
                setResult(RESULT_OK, Intent(this, MainActivity::class.java).also { it.putExtra("state", true) }).also { finishActivity() }
            }
        }
    }

    private fun socialLogOut() {
        UserApiClient.instance.logout {}
    }

    fun finishActivity() {
        finish()
    }
}