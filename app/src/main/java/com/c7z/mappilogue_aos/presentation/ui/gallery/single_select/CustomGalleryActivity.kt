package com.c7z.mappilogue_aos.presentation.ui.gallery.single_select

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.c7z.mappilogue_aos.R
import com.c7z.mappilogue_aos.databinding.ActivityCustomGallerySinglePickBinding
import com.c7z.mappilogue_aos.presentation.ui.gallery.single_select.adapter.GalleryAdapter
import com.c7z.mappilogue_aos.presentation.ui.gallery.single_select.viewmodel.CustomGalleryViewModel
import com.google.android.material.snackbar.Snackbar

class CustomGalleryActivity : AppCompatActivity() {
    private lateinit var binding : ActivityCustomGallerySinglePickBinding
    private val viewModel : CustomGalleryViewModel by viewModels()
    private val galleryAdapter by lazy {
        GalleryAdapter(::onImageClick)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_custom_gallery_single_pick)

        initBinding()
        initRV()
        getGalleries()
        observe()
    }

    private fun initBinding() {
        binding.activity = this
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
    }

    private fun initRV() {
        binding.customGalleryRvImages.adapter = galleryAdapter
    }

    private fun getGalleries() {
        viewModel.fetchImageItemList(this)
    }

    private fun observe() {
        viewModel.imageItemList.observe(this) {
            galleryAdapter.galleryData = it
        }

        viewModel.checkedPosition.observe(this) {
            if(it != null) {
                galleryAdapter.galleryData[it].checked = true
                galleryAdapter.notifyItemChanged(it)
            }
        }

        viewModel.legacyPosition.observe(this) {
            if(it != null) {
                galleryAdapter.galleryData[it].checked = false
                galleryAdapter.notifyItemChanged(it)
            }
        }
    }

    private fun onImageClick(position : Int) {
        viewModel.changeImageClicked(position)
    }

    fun setResult() {
        if(viewModel.checkedPosition.value != null) {
            setResult(
                Activity.RESULT_OK,
                Intent().putExtra("Image", viewModel.getCheckedImageUriList())
            )
            finish()
        }
    }
}