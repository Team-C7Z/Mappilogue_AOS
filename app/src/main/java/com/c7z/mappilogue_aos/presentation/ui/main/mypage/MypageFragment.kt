package com.c7z.mappilogue_aos.presentation.ui.main.mypage

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.c7z.mappilogue_aos.BuildConfig
import com.c7z.mappilogue_aos.R
import com.c7z.mappilogue_aos.databinding.FragmentMypageBinding
import com.c7z.mappilogue_aos.presentation.ui.change_profile.ChangeProfileActivity
import com.c7z.mappilogue_aos.presentation.ui.component.dialog.ComponentDialogCheckBox
import com.c7z.mappilogue_aos.presentation.ui.component.dialog.ComponentDialogOneButton
import com.c7z.mappilogue_aos.presentation.ui.component.dialog.ComponentDialogTwoButton
import com.c7z.mappilogue_aos.presentation.ui.inquire.InquireActivity
import com.c7z.mappilogue_aos.presentation.ui.main.mypage.adapter.MyPageMenuAdapter
import com.c7z.mappilogue_aos.presentation.ui.main.mypage.viewmodel.MypageViewModel
import com.c7z.mappilogue_aos.presentation.ui.notification_setting.NotificationSettingActivity
import com.c7z.mappilogue_aos.presentation.ui.sign_out_reason.SignOutReasonActivity
import com.c7z.mappilogue_aos.presentation.ui.signin.SignInActivity
import com.c7z.mappilogue_aos.presentation.util.ItemDecorator
import com.kakao.sdk.user.UserApiClient
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MypageFragment : Fragment() {
    private lateinit var binding : FragmentMypageBinding
    private val viewModel: MypageViewModel by viewModels()
    private val upperMenuAdapter by lazy { MyPageMenuAdapter(viewModel.upperMenuData(), ::onUpperMenuClicked) }
    private val lowerMenuAdapter by lazy { MyPageMenuAdapter(viewModel.lowerMenuData(), ::onLowerMenuClicked) }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_mypage, container, false)

        initBinding()
        initUi()
        initObserve()

        return binding.root
    }

    private fun initBinding() {
        binding.fragment = this
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
    }

    private fun initUi() {
        initLocalVersionInfo()
        initUpperRv()
        initLowerRv()
    }

    private fun initObserve() {
        observeLogOut()
    }

    private fun initLocalVersionInfo() {
        binding.fgMyTvVersionInfoLocal.text = BuildConfig.VERSION_NAME
    }

    private fun initUpperRv() {
        binding.fgMyRvUpperMenu.apply {
            adapter = upperMenuAdapter
            addItemDecoration(ItemDecorator.MyPageItemDecorator(requireActivity()))
        }
    }

    private fun initLowerRv() {
        binding.fgMyRvLowerMenu.apply {
            adapter = lowerMenuAdapter
            addItemDecoration(ItemDecorator.MyPageItemDecorator(requireActivity()))
        }
    }

    fun openChangeProfile() {
        requestProfileChangeActivity.launch(Intent(requireActivity(), ChangeProfileActivity::class.java))
    }

    private fun onUpperMenuClicked(position : Int) {
        when(position) {
            0 -> startActivity(Intent(requireActivity(), NotificationSettingActivity::class.java))
            2 -> startActivity(Intent(requireActivity(), InquireActivity::class.java))
        }
    }

    private fun onLowerMenuClicked(position : Int) {
        when(position) {
            0 -> onLogoutClicked()
            1 -> onSignOutClicked()
        }
    }

    private fun onLogoutClicked() {
        ComponentDialogTwoButton(null, ::requestLogOut, "GREEN").show(requireActivity().supportFragmentManager, "LOG_OUT")
    }

    private fun requestLogOut(noinline : Int?) {
        viewModel.requestLogOut()
    }

    private fun observeLogOut() {
        viewModel.logOutStatus.observe(viewLifecycleOwner) {
            if(it == 204) {
                socialLogOut().also { viewModel.removeUserDataAtLocal() }
                startActivity(Intent(requireActivity(), SignInActivity::class.java)).also { requireActivity().finish() }
            }
        }
    }

    private fun onSignOutClicked() {
        ComponentDialogCheckBox(::openSignOutReasonActivity, "RED").show(requireActivity().supportFragmentManager, "SIGN_OUT")
    }

    private fun openSignOutReasonActivity() {
        requestSignOutReasonActivity.launch(Intent(requireActivity(), SignOutReasonActivity::class.java))
    }

    private fun socialLogOut() {
        UserApiClient.instance.logout {}
    }

    private fun showSignOutSuccessDialog() {
        ComponentDialogOneButton(::moveToSignIn).show(requireActivity().supportFragmentManager, "SUCCESS_SIGN_OUT")
    }

    private fun moveToSignIn() {
        startActivity(Intent(requireActivity(), SignInActivity::class.java)).also { requireActivity().finish() }
    }

    private val requestProfileChangeActivity =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                viewModel.requestUserProfile()
            }
        }

    private val requestSignOutReasonActivity =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                val state = it.data!!.extras?.getBoolean("state")
                if(state == true) {
                    showSignOutSuccessDialog()
                }
            }
        }


    override fun onResume() {
        super.onResume()
        viewModel.requestUserProfile()
    }
}