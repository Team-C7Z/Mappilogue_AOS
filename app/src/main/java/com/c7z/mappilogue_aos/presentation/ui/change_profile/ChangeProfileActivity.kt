package com.c7z.mappilogue_aos.presentation.ui.change_profile

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.c7z.mappilogue_aos.R
import com.c7z.mappilogue_aos.databinding.ActivityChangeProfileBinding
import com.c7z.mappilogue_aos.presentation.ui.change_profile.viewmodel.ChangeProfileViewModel
import com.c7z.mappilogue_aos.presentation.ui.component.dialog.ComponentDialogEditText
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChangeProfileActivity : AppCompatActivity() {
    private lateinit var binding : ActivityChangeProfileBinding
    private val viewModel : ChangeProfileViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_change_profile)

        initBinding()
        initObserve()
    }

    private fun initBinding() {
        binding.activity = this
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
    }

    private fun initObserve() {
        observeModifyNickNameStatus()
    }

    fun onNicknameChangeClicked(userNickname : String) {
        ComponentDialogEditText(::onSaveClicked, userNickname).show(supportFragmentManager, "EDIT_NICKNAME")
    }

    private fun onSaveClicked(nickname : String) {
        viewModel.requestModifyUserNickname(nickname)
    }

    private fun observeModifyNickNameStatus() {
        viewModel.userNicknameModifyStatus.observe(this) {
            if(it == 200) {
                viewModel.requestUserData()
            }
        }
    }
}