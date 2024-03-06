package com.c7z.mappilogue_aos.presentation.ui.change_profile

import android.app.Activity
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.databinding.DataBindingUtil
import com.c7z.mappilogue_aos.R
import com.c7z.mappilogue_aos.databinding.ActivityChangeProfileBinding
import com.c7z.mappilogue_aos.presentation.ui.change_profile.dialog.EditProfileImageDialog
import com.c7z.mappilogue_aos.presentation.ui.change_profile.viewmodel.ChangeProfileViewModel
import com.c7z.mappilogue_aos.presentation.ui.component.dialog.ComponentDialogEditText
import com.c7z.mappilogue_aos.presentation.ui.gallery.single_select.CustomGalleryActivity
import com.c7z.mappilogue_aos.presentation.util.BitmapHelper.convertWhenSingle
import com.c7z.mappilogue_aos.presentation.util.BitmapHelper.deletePic
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChangeProfileActivity : AppCompatActivity() {
    private lateinit var binding : ActivityChangeProfileBinding
    private val viewModel : ChangeProfileViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_change_profile)

        initBinding()
        initObserve()
    }

    private fun initBinding() {
        binding.activity = this
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
    }

    private fun initObserve() {
        observeModifyNickNameStatus()
        observeModifyPhotoStatus()
    }

    fun onNicknameChangeClicked(userNickname : String) {
        ComponentDialogEditText(::onSaveClicked, userNickname).show(supportFragmentManager, "EDIT_NICKNAME")
    }

    fun onProfileImageChangeClicked() {
        EditProfileImageDialog(::onOpenGallery, ::onDeleteProfileImage).show(supportFragmentManager, null)
    }

    private fun onOpenGallery() {
        val intent = Intent(this, CustomGalleryActivity::class.java)
        this.requestGalleryActivity.launch(intent)
    }

    private fun onDeleteProfileImage() {
        //viewModel.requestChangeProfileImage(null)

        viewModel.deletePath = ""
    }

    private fun onSaveClicked(nickname : String) {
        viewModel.requestModifyUserNickname(nickname)
    }

    private fun observeModifyNickNameStatus() {
        viewModel.userNicknameModifyStatus.observe(this) {
            if(it == 204) {
                viewModel.requestUserData()
            }
        }
    }

    private fun observeModifyPhotoStatus() {
        viewModel.userPhotoModifyStatus.observe(this) {
            if(it == 200) viewModel.requestUserData()

            if(viewModel.deletePath.isNotEmpty()) viewModel.deletePath.toUri().deletePic(this)
        }
    }

    private fun String.requestChangeProfileImage() {
        viewModel.requestChangeProfileImage(this.replace("file://", "")
            .convertWhenSingle(this@ChangeProfileActivity)
            .Convert()
            .also { viewModel.deletePath = it }
            .toUri()
            .getAbsolutePath())
    }

    private fun String.Convert() : String {
        return "content://${this.substring(9)}"
    }

    private fun Uri.getAbsolutePath(): String {
        val proj: Array<String> = arrayOf(MediaStore.Images.Media.DATA)
        val c: Cursor = contentResolver.query(this, proj, null, null, null)!!
        val index = c.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        c.moveToFirst()
        return c.getString(index)
    }

    fun finishActivity() {
        finish()
    }

    private val requestGalleryActivity =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { it ->
            if (it.resultCode == Activity.RESULT_OK) {
                it.data?.extras?.getString("Image")?.requestChangeProfileImage()
            }
        }
}