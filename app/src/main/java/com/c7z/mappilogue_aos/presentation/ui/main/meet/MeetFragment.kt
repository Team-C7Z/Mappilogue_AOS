package com.c7z.mappilogue_aos.presentation.ui.main.meet

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.c7z.mappilogue_aos.R
import com.c7z.mappilogue_aos.presentation.ui.main.meet.viewmodel.MeetViewModel

class MeetFragment : Fragment() {

    companion object {
        fun newInstance() = MeetFragment()
    }

    private lateinit var viewModel: MeetViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_meet, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MeetViewModel::class.java)
        // TODO: Use the ViewModel
    }

}