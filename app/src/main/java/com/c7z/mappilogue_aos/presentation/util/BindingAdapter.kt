package com.c7z.mappilogue_aos.presentation.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("common_circle_image")
fun ImageView.setCircleImage(path : Any?) {
    Glide.with(this).load(path).circleCrop().into(this)
}

@BindingAdapter("common_set_image")
fun ImageView.setImage(path : Any?) {
    Glide.with(this).load(path).into(this)
}