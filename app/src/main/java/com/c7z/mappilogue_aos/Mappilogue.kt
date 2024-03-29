package com.c7z.mappilogue_aos

import android.app.Application
import android.content.SharedPreferences
import androidx.lifecycle.ProcessLifecycleOwner
import com.google.firebase.analytics.FirebaseAnalytics
import com.kakao.sdk.common.KakaoSdk
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class Mappilogue : Application() {

    companion object {
        lateinit var mSharedPreferences: SharedPreferences
    }

    override fun onCreate() {
        super.onCreate()
        mSharedPreferences = applicationContext.getSharedPreferences("Mappilogue", MODE_PRIVATE)

        initKakao()
    }

    private fun initKakao() {
        KakaoSdk.init(this, BuildConfig.KAKAO_NATIVE_KEY)
    }
}