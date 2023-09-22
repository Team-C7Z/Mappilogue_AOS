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
import com.c7z.mappilogue_aos.presentation.ui.main.mypage.viewmodel.MypageViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MypageFragment : Fragment() {
    private lateinit var binding : FragmentMypageBinding
    private val viewModel: MypageViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_mypage, container, false)

        initBinding()
        initObserve()

        return binding.root
    }

    private fun initBinding() {
        binding.fragment = this
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
    }

    private fun initObserve() {
        viewModel.userProfileData.observe(viewLifecycleOwner) {
            Log.e("----", "initObserve: $it", )
        }
    }

    fun openChangeProfile() {
        startActivity(Intent(requireActivity(), ChangeProfileActivity::class.java))
    }

}