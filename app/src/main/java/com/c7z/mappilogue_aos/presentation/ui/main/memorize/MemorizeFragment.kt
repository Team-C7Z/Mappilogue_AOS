package com.c7z.mappilogue_aos.presentation.ui.main.memorize

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.c7z.mappilogue_aos.R
import com.c7z.mappilogue_aos.presentation.ui.main.memorize.viewmodel.MemorizeViewModel

class MemorizeFragment : Fragment() {

    companion object {
        fun newInstance() = MemorizeFragment()
    }

    private lateinit var viewModel: MemorizeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_memorize, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MemorizeViewModel::class.java)
        // TODO: Use the ViewModel
    }

}