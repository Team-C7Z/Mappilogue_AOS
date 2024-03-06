package com.c7z.mappilogue_aos.data.local.source

import com.c7z.mappilogue_aos.Mappilogue.Companion.mSharedPreferences
import com.c7z.mappilogue_aos.domain.source.SharedPreferenceSource

class SharedPreferenceLocalSource : SharedPreferenceSource {
    override suspend fun saveSignInData(access: String, refresh: String) {
        mSharedPreferences.edit().putString("jwt", access).putString("refresh", refresh).apply()
    }

    override suspend fun deleteUserData() {
        mSharedPreferences.edit().clear().apply()
    }

    override suspend fun requestRefreshToken(): String {
        return mSharedPreferences.getString("refresh", "") ?: ""
    }

    override suspend fun requestAccessToken(): String {
        return mSharedPreferences.getString("jwt", "") ?: ""
    }


}