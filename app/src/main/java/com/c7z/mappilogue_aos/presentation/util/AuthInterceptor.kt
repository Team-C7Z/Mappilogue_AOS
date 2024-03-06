package com.c7z.mappilogue_aos.presentation.util

import android.content.SharedPreferences
import com.c7z.mappilogue_aos.Mappilogue.Companion.mSharedPreferences
import com.c7z.mappilogue_aos.data.remote.response.ResponseSignIn
import com.google.gson.Gson
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import okhttp3.Route
import javax.inject.Inject


class AuthInterceptor : Authenticator {
    override fun authenticate(route: Route?, response: Response): Request? {
        val originRequest = response.request

        if(originRequest.header("Authorization").isNullOrEmpty()) {
            return null
        }

        val refreshToken = runBlocking {
            mSharedPreferences.getString("refresh", "")
        }

        val refreshRequest =
            Request.Builder()
                .url("https://api-dev.mappilogue.shop/api/v1/users/token-refresh")
                .post(refreshToken!!.toRequestBody())
                .build()

        val refreshResponse = OkHttpClient().newCall(refreshRequest).execute()

        if(refreshResponse.code == 201) {
            val refreshResponse =
                Gson().fromJson(refreshResponse.body?.string(), ResponseSignIn::class.java)
            val newAccessToken = refreshResponse.result.accessToken

            mSharedPreferences.edit().putString("jwt", newAccessToken).apply()
            return originRequest.newBuilder()
                .removeHeader("Authorization")
                .addHeader("Authorization", newAccessToken)
                .build()
        } else {
            mSharedPreferences.edit().remove("jwt").apply()
            mSharedPreferences.edit().remove("refresh").apply()
        }
        return null
    }

}