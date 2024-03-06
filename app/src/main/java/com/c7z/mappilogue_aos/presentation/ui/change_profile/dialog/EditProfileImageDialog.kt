package com.c7z.mappilogue_aos.presentation.ui.change_profile.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.c7z.mappilogue_aos.R
import com.c7z.mappilogue_aos.databinding.DialogBottomDeleteMarkScheduleBinding
import com.c7z.mappilogue_aos.databinding.DialogBottomDeleteSelectProfileImageBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class EditProfileImageDialog(
    private val onOpenGalleryClicked : () -> Unit,
    private val onDeleteClicked : () -> Unit) : BottomSheetDialogFragment() {
    private lateinit var binding : DialogBottomDeleteSelectProfileImageBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.dialog_bottom_delete_select_profile_image, container, false)

        initBinding()

        return binding.root
    }

    private fun initBinding() {
        binding.dialog = this
    }

    fun onOpenGalleryClicked() {
        onOpenGalleryClicked.invoke()
        dismiss()
    }

    fun onDeleteClicked() {
        onDeleteClicked.invoke()
        dismiss()
    }

    override fun getTheme(): Int = R.style.BottomSheet
}