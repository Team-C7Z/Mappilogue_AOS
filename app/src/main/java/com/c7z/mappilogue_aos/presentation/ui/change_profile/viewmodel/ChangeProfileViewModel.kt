package com.c7z.mappilogue_aos.presentation.ui.change_profile.viewmodel

import android.util.Log
import androidx.core.net.toUri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.c7z.mappilogue_aos.data.remote.request.RequestModifyUserNickname
import com.c7z.mappilogue_aos.data.remote.request.RequestRefreshToken
import com.c7z.mappilogue_aos.data.remote.response.ResponseUserProfileData
import com.c7z.mappilogue_aos.domain.repository.SignInRepository
import com.c7z.mappilogue_aos.domain.repository.UserRepository
import com.c7z.mappilogue_aos.presentation.util.BitmapHelper.deletePic
import com.c7z.mappilogue_aos.presentation.util.FailureResponse
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.yield
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import javax.inject.Inject

@HiltViewModel
class ChangeProfileViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val signInRepository: SignInRepository
) : ViewModel() {

    private val _userProfileData = MutableLiveData<ResponseUserProfileData.ResultUserProfileData>()
    val userProfileData: LiveData<ResponseUserProfileData.ResultUserProfileData> = _userProfileData

    private val _userNicknameModifyStatus = MutableLiveData<Int>()
    val userNicknameModifyStatus: LiveData<Int> = _userNicknameModifyStatus

    private val _userPhotoModifyStatus = MutableLiveData<Int>()
    val userPhotoModifyStatus: LiveData<Int> = _userPhotoModifyStatus

    var deletePath: String = ""

    init {
        requestUserData()
    }

    fun requestUserData() {
        viewModelScope.launch {
            userRepository.requestUserProfileData()
                .onSuccess { _userProfileData.value = it }
                .onFailure {
                    Gson().fromJson(
                        it.message,
                        FailureResponse::class.java
                    ).errorCode.toInt().validateErrorCode("REQUEST_USER_DATA")
                }
        }
    }

    fun requestModifyUserNickname(nickname: String) {
        viewModelScope.launch {
            userRepository.requestModifyUserNickname(RequestModifyUserNickname(nickname))
                .onSuccess { _userNicknameModifyStatus.value = it }
                .onFailure { Log.e("----", "requestModifyUserNickname: ${it.message}") }
        }
    }

    private fun Int.validateErrorCode(job: String) {
        if (this == 1009) {
            viewModelScope.launch {
                requestRefreshToken()
            }

        }
    }

    private fun requestRefreshToken() {
        viewModelScope.launch {
            userRepository.requestRefreshToken()
                .onSuccess { signInRepository.saveSignInData(it.accessToken, it.refreshToken) }
        }
    }

    fun requestChangeProfileImage(path: String) {
        viewModelScope.launch {
            userRepository.requestModifyUserProfileImage(path.mapToMultipartWhenPost())
                .onSuccess { _userPhotoModifyStatus.value = it }
                .onFailure {
                    _userPhotoModifyStatus.value = Gson().fromJson(
                        it.message,
                        FailureResponse::class.java
                    ).errorCode.toInt()
                    Log.e("----", "requestChangeProfileImage: ${it.message}")
                }
        }
    }

    private fun String.mapToMultipartWhenPost(): MultipartBody.Part {
        val file = File(this)
        val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
        return MultipartBody.Part.createFormData("image", file.name, requestFile)
    }
}