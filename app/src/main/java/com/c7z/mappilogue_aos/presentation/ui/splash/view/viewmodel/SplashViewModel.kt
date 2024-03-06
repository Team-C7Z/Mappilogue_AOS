package com.c7z.mappilogue_aos.presentation.ui.splash.view.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.c7z.mappilogue_aos.domain.repository.UserRepository
import com.c7z.mappilogue_aos.presentation.util.FailureResponse
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(private val userRepository: UserRepository): ViewModel() {

    private val _signInState = MutableLiveData<Int>()
    val signInState : LiveData<Int> = _signInState
    init {
        requestAutoSignIn()
    }

    private fun requestAutoSignIn() {
        viewModelScope.launch {
            userRepository.requestAutoSignIn()
                .onSuccess { _signInState.value = it }
                .onFailure { _signInState.value = Gson().fromJson(it.message, FailureResponse::class.java).errorCode.toInt() }
        }
    }
}