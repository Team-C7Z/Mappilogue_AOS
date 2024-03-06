package com.c7z.mappilogue_aos.presentation.ui.main.mypage.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.c7z.mappilogue_aos.R
import com.c7z.mappilogue_aos.data.data.MyPageMenuItem
import com.c7z.mappilogue_aos.data.remote.response.ResponseUserProfileData
import com.c7z.mappilogue_aos.domain.repository.SignInRepository
import com.c7z.mappilogue_aos.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MypageViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val signInRepository: SignInRepository
) : ViewModel() {

    private val _userProfileData = MutableLiveData<ResponseUserProfileData.ResultUserProfileData>()
    val userProfileData: LiveData<ResponseUserProfileData.ResultUserProfileData> = _userProfileData

    private val _logOutStatus = MutableLiveData<Int>()
    val logOutStatus: LiveData<Int> = _logOutStatus

    fun requestUserProfile() {
        viewModelScope.launch {
            userRepository.requestUserProfileData()
                .onSuccess { _userProfileData.value = it
                    Log.e("----", "requestUserProfile: $it", )}
        }
    }

    fun requestLogOut() {
        viewModelScope.launch {
            userRepository.requestLogOut()
                .onSuccess { _logOutStatus.value = it }
        }
    }

    fun removeUserDataAtLocal() {
        viewModelScope.launch {
            signInRepository.deleteUserData()
        }
    }

    private fun ResponseUserProfileData.ResultUserProfileData.setImage(): ResponseUserProfileData.ResultUserProfileData {
        return this.apply { this.profileImageUrl = R.drawable.ic_default_profile.toString() }
    }

    /** Dummy **/
    fun upperMenuData(): List<MyPageMenuItem> = listOf(
        MyPageMenuItem(R.drawable.ic_my_page_set_alarm, "알림 설정"),
        MyPageMenuItem(R.drawable.ic_my_page_show_term, "이용약관"),
        MyPageMenuItem(R.drawable.ic_my_page_send_report, "문의하기")
    )

    fun lowerMenuData(): List<MyPageMenuItem> = listOf(
        MyPageMenuItem(R.drawable.ic_my_page_log_out, "로그아웃"),
        MyPageMenuItem(R.drawable.ic_my_page_sign_out, "탈퇴하기")
    )
}