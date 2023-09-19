package com.c7z.mappilogue_aos.presentation.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.c7z.mappilogue_aos.R
import com.c7z.mappilogue_aos.databinding.FragmentHomeBinding
import com.c7z.mappilogue_aos.presentation.ui.home.today.TodayFragment
import com.c7z.mappilogue_aos.presentation.ui.home.upcoming.UpcomingFragment
import com.c7z.mappilogue_aos.presentation.ui.home.viewmodel.HomeViewModel
import com.google.android.material.tabs.TabLayout

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val viewModel: HomeViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)

        initBinding()
        initView()

        return binding.root
    }

    private fun initBinding() {
        binding.fragment = this
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
    }

    private fun initView() {
        childFragmentManager.beginTransaction().replace(binding.homeFrameContentFl.id, TodayFragment()).commit()

        binding.homeTabTl.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                val position = tab!!.position
                var selectedFragment: Fragment? = null

                when (position) {
                    0 -> { selectedFragment = TodayFragment() }
                    1 -> { selectedFragment = UpcomingFragment() }
                }

                if (selectedFragment != null) {
                    childFragmentManager.beginTransaction().replace(binding.homeFrameContentFl.id, selectedFragment).commit()
                }
            }
            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }

    private fun Fragment.changeFragment() {

    }
}