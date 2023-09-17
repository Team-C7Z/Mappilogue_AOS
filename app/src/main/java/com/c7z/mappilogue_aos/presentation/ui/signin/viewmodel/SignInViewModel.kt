package com.c7z.mappilogue_aos.presentation.ui.signin.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.c7z.mappilogue_aos.data.remote.request.RequestSignIn
import com.c7z.mappilogue_aos.domain.repository.SignInRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(private val signInRepository: SignInRepository): ViewModel() {

    fun requestSignIn(socialToken : String, fcmToken : String, isAlarmAccept : String) {
        viewModelScope.launch {
            signInRepository.requestSignIn(RequestSignIn(socialToken, "KAKAO", fcmToken, isAlarmAccept))
                .onSuccess { Log.e("----", "requestSignIn: $it", ) }
        }
    }
}