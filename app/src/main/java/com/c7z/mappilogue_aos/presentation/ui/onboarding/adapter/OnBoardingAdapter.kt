package com.c7z.mappilogue_aos.presentation.ui.onboarding.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.c7z.mappilogue_aos.R
import com.c7z.mappilogue_aos.databinding.ItemVpOnboardingBinding

class OnBoardingAdapter : RecyclerView.Adapter<OnBoardingAdapter.OnBoardingViewHolder>() {
    private val images = listOf(R.drawable.bg_onboarding_1, R.drawable.bg_onboarding_2, R.drawable.bg_onboarding_3)
    inner class OnBoardingViewHolder(private val binding : ItemVpOnboardingBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(image : Int) {
            Glide.with(binding.iv).load(image).into(binding.iv)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OnBoardingViewHolder {
        return OnBoardingViewHolder(ItemVpOnboardingBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int = 3

    override fun onBindViewHolder(holder: OnBoardingViewHolder, position: Int) {
        holder.bind(images[position])
    }
}