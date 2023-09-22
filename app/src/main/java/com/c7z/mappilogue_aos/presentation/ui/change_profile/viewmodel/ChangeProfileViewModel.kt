package com.c7z.mappilogue_aos.presentation.ui.change_profile.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.c7z.mappilogue_aos.data.remote.request.RequestModifyUserNickname
import com.c7z.mappilogue_aos.data.remote.response.ResponseUserProfileData
import com.c7z.mappilogue_aos.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChangeProfileViewModel @Inject constructor(private val userRepository: UserRepository): ViewModel() {

    private val _userProfileData = MutableLiveData<ResponseUserProfileData.ResultUserProfileData>()
    val userProfileData : LiveData<ResponseUserProfileData.ResultUserProfileData> = _userProfileData

    private val _userNicknameModifyStatus = MutableLiveData<Int>()
    val userNicknameModifyStatus : LiveData<Int> = _userNicknameModifyStatus

    init {
        requestUserData()
    }

    fun requestUserData() {
        viewModelScope.launch {
            userRepository.requestUserProfileData()
                .onSuccess { _userProfileData.value = it }
        }
    }

    fun requestModifyUserNickname(nickname : String) {
        viewModelScope.launch {
            userRepository.requestModifyUserNickname(RequestModifyUserNickname(nickname))
                .onSuccess { _userNicknameModifyStatus.value = it }
        }
    }
}