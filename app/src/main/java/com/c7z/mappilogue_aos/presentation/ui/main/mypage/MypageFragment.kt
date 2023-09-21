package com.c7z.mappilogue_aos.presentation.ui.main.mypage

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.c7z.mappilogue_aos.R
import com.c7z.mappilogue_aos.presentation.ui.main.mypage.viewmodel.MypageViewModel

class MypageFragment : Fragment() {

    companion object {
        fun newInstance() = MypageFragment()
    }

    private lateinit var viewModel: MypageViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_mypage, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MypageViewModel::class.java)
        // TODO: Use the ViewModel
    }

}