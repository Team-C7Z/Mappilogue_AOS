package com.c7z.mappilogue_aos.presentation.ui.main.mypage.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.c7z.mappilogue_aos.R
import com.c7z.mappilogue_aos.data.remote.response.ResponseUserProfileData
import com.c7z.mappilogue_aos.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MypageViewModel @Inject constructor(private val userRepository: UserRepository): ViewModel() {

    private val _userProfileData = MutableLiveData<ResponseUserProfileData.ResultUserProfileData>()
    val userProfileData : LiveData<ResponseUserProfileData.ResultUserProfileData> = _userProfileData

    init {
        requestUserProfile()
    }

    fun requestUserProfile() {
        viewModelScope.launch {
            userRepository.requestUserProfileData()
                .onSuccess { _userProfileData.value = it.setImage() }
        }
    }

    private fun ResponseUserProfileData.ResultUserProfileData.setImage() : ResponseUserProfileData.ResultUserProfileData {
        return this.apply { this.profileImageUrl = R.drawable.ic_default_profile.toString() }
    }
}