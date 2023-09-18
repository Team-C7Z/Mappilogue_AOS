package com.c7z.mappilogue_aos.presentation.ui.signin

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Build.VERSION
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.c7z.mappilogue_aos.R
import com.c7z.mappilogue_aos.databinding.ActivitySignInBinding
import com.c7z.mappilogue_aos.presentation.ui.main.MainActivity
import com.c7z.mappilogue_aos.presentation.ui.signin.viewmodel.SignInViewModel
import com.google.firebase.messaging.FirebaseMessaging
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignInActivity : AppCompatActivity() {
    private lateinit var binding : ActivitySignInBinding
    private val viewModel : SignInViewModel by viewModels()

    private var fcmToken : String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_in)

        initBinding()
        initObserve()
        requestFcmToken()
    }

    private fun initBinding() {
        binding.activity = this
    }

    private fun initObserve() {
        observeSignInStatus()
    }

    private fun requestFcmToken() {
        FirebaseMessaging.getInstance().token.addOnSuccessListener {
            fcmToken = it
        }
    }

    fun onKakaoSignInClicked() {
        if (UserApiClient.instance.isKakaoTalkLoginAvailable(this)) {
            signInWithKakaoTalk()
        } else {
            signInWithKakaoAccount()
        }
    }

    private fun signInWithKakaoTalk() {
        UserApiClient.instance.loginWithKakaoTalk(this) { token, error ->
            if (error != null) {
                if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                    finish()
                    return@loginWithKakaoTalk
                }
                signInWithKakaoAccount()
            } else if (token != null) {
                requestSignIn(token.accessToken)
            }
        }
    }

    private fun signInWithKakaoAccount() {
        UserApiClient.instance.loginWithKakaoAccount(this, callback = kakaoAccountSignInCallback)
    }

    private val kakaoAccountSignInCallback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
        if (error != null) {
            finish()
        } else if (token != null) {
            requestSignIn(token.accessToken)
        }
    }

    private fun requestSignIn(token : String) {
        fcmToken?.let { it2 ->
            viewModel.requestSignIn(token, it2, if(checkAlarmPermission()) "ACTIVE" else "INACTIVE")
        }
    }

    private fun observeSignInStatus() {
        viewModel.signInStatus.observe(this) {
            when(it) {
                200 -> startActivity(Intent(this, MainActivity::class.java)).also { finish() }
                else -> Toast.makeText(this, "로그인에 실패하였습니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun checkAlarmPermission() : Boolean {
        return if(VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) checkSelfPermission(android.Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED else true
    }
}