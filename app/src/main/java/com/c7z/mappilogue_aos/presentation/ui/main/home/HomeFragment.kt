package com.c7z.mappilogue_aos.presentation.ui.main.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.c7z.mappilogue_aos.R
import com.c7z.mappilogue_aos.databinding.FragmentHomeBinding
import com.c7z.mappilogue_aos.presentation.ui.main.home.today.TodayFragment
import com.c7z.mappilogue_aos.presentation.ui.main.home.upcoming.UpcomingFragment
import com.google.android.material.tabs.TabLayout

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)

        initView()

        return binding.root
    }

    private fun initView() {
        this.changeFragment(TodayFragment())

        setTabSelected()
    }

    private fun Fragment.changeFragment(fragment: Fragment) {
        childFragmentManager.beginTransaction().replace(binding.homeFrameContentFl.id, fragment).commit()
    }

    private fun setTabSelected() {
        binding.homeTabTl.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                val position = tab!!.position
                var selectedFragment: Fragment? = null

                when (position) {
                    0 -> { selectedFragment = TodayFragment() }
                    1 -> { selectedFragment = UpcomingFragment() }
                }

                if (selectedFragment != null) {
                    this@HomeFragment.changeFragment(selectedFragment)
                }
            }
            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }
}