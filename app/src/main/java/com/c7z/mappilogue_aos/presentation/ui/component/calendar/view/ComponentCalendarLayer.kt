package com.c7z.mappilogue_aos.presentation.ui.component.calendar.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.c7z.mappilogue_aos.R
import com.c7z.mappilogue_aos.databinding.ComponentCalendarLayerBinding
import java.time.LocalDate

class ComponentCalendarLayer(private val localDate : LocalDate) : Fragment() {
    private lateinit var binding : ComponentCalendarLayerBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.component_calendar_layer, container,false)

        Log.e("----", "onCreateView: ${localDate}", )
        binding.test.text = localDate.toString()


        return binding.root
    }


}