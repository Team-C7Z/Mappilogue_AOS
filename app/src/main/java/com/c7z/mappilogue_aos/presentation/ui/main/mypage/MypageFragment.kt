package com.c7z.mappilogue_aos.presentation.ui.main.mypage

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.c7z.mappilogue_aos.R
import com.c7z.mappilogue_aos.databinding.FragmentMypageBinding
import com.c7z.mappilogue_aos.presentation.ui.change_profile.ChangeProfileActivity
import com.c7z.mappilogue_aos.presentation.ui.component.dialog.ComponentDialogTwoButton
import com.c7z.mappilogue_aos.presentation.ui.main.mypage.adapter.MyPageMenuAdapter
import com.c7z.mappilogue_aos.presentation.ui.main.mypage.viewmodel.MypageViewModel
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
        initUpperRv()
        initLowerRv()
    }

    private fun initObserve() {
        observeLogOut()
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
        startActivity(Intent(requireActivity(), ChangeProfileActivity::class.java))
    }

    private fun onUpperMenuClicked(position : Int) {

    }

    private fun onLowerMenuClicked(position : Int) {
        when(position) {
            0 -> onLogoutClicked()
            1 -> onSignOutClicked()
        }
    }

    private fun onLogoutClicked() {
        ComponentDialogTwoButton(::requestLogOut, "GREEN").show(requireActivity().supportFragmentManager, "LOG_OUT")
    }

    private fun requestLogOut() {
        viewModel.requestLogOut()
    }

    private fun observeLogOut() {
        viewModel.logOutStatus.observe(viewLifecycleOwner) {
            if(it == 200) {
                socialLogOut()
                startActivity(Intent(requireActivity(), SignInActivity::class.java)).also { requireActivity().finish() }
            }
        }
    }

    private fun onSignOutClicked() {

    }

    private fun socialLogOut() {
        UserApiClient.instance.logout {  }
    }

}