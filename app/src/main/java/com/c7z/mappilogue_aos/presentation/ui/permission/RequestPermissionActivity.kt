package com.c7z.mappilogue_aos.presentation.ui.permission

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.c7z.mappilogue_aos.R
import com.c7z.mappilogue_aos.databinding.ActivityRequestPermissionBinding
import com.c7z.mappilogue_aos.presentation.ui.onboarding.OnBoardingActivity

class RequestPermissionActivity : AppCompatActivity() {
    private lateinit var binding : ActivityRequestPermissionBinding
    private val REQUEST_PERMISSION_CODE = 1001

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_request_permission)

        initBinding()
    }

    private fun initBinding() {
        binding.activity = this
    }

    fun requestPermission() {
        requestPermissions(arrayOf(requestNotificationPermission(), requestGalleryPermission(), requestCameraPermission(), requestCoarseLocationPermission(), requestFineLocationPermission()), REQUEST_PERMISSION_CODE)
        startActivity(Intent(this, OnBoardingActivity::class.java)).also { finish() }
    }

    private fun requestNotificationPermission() : String? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val permissionCheck = checkSelfPermission(android.Manifest.permission.POST_NOTIFICATIONS)
            if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
               android.Manifest.permission.POST_NOTIFICATIONS
            } else null
        } else null
    }

    private fun requestGalleryPermission() : String? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val permissionCheck = checkSelfPermission(android.Manifest.permission.READ_MEDIA_IMAGES)
            if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
                android.Manifest.permission.READ_MEDIA_IMAGES
            } else null
        } else {
            val permissionCheck = checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE)
            if(permissionCheck != PackageManager.PERMISSION_GRANTED) {
                android.Manifest.permission.READ_EXTERNAL_STORAGE
            } else null
        }
    }

    private fun requestCameraPermission() : String? {
        return if(checkSelfPermission(android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            android.Manifest.permission.CAMERA
        } else null
    }

    private fun requestCoarseLocationPermission() : String? {
        return if (checkSelfPermission(android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            android.Manifest.permission.ACCESS_COARSE_LOCATION
        } else null
    }

    private fun requestFineLocationPermission() : String? {
        return if (checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            android.Manifest.permission.ACCESS_FINE_LOCATION
        } else null
    }
}