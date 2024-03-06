package com.c7z.mappilogue_aos.presentation.ui.sign_out_reason.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.c7z.mappilogue_aos.data.data.SignOutReasonItem
import com.c7z.mappilogue_aos.data.remote.request.RequestSignOut
import com.c7z.mappilogue_aos.domain.repository.SignInRepository
import com.c7z.mappilogue_aos.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignOutReasonViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val signInRepository: SignInRepository
) : ViewModel() {

    private val _liveCheckedItemPositions = MutableLiveData<List<Int>>()
    val liveCheckedItemPositions: LiveData<List<Int>> = _liveCheckedItemPositions

    private val _checkedItemPositions = mutableListOf<Int>()
    val checkedItemPositions: List<Int> = _checkedItemPositions

    private val _signOutStatus = MutableLiveData<Int>()
    val signOutStatus: LiveData<Int> = _signOutStatus

    fun manageCheckedPosition(position: Int, isChecked: Boolean) {
        if (isChecked) addCheckedItemPosition(position)
        else removeCheckedItemPosition(position)

        _checkedItemPositions.notificationLiveItem()
    }

    private fun addCheckedItemPosition(position: Int) = _checkedItemPositions.add(position)
    private fun removeCheckedItemPosition(position: Int) = _checkedItemPositions.remove(position)

    private fun List<Int>.notificationLiveItem() {
        _liveCheckedItemPositions.value = this
    }

    fun requestSignOut() {
        viewModelScope.launch {
            userRepository.requestSignOut(RequestSignOut(checkedItemPositions.setSignOutBody()))
                .onSuccess { _signOutStatus.value = it
                    Log.e("----", "requestSignOut: $it", )}
                .onFailure { Log.e("----", "requestSignOut: ${it.message}", ) }

        }
    }

    fun removeUserDataAtLocal() {
        viewModelScope.launch {
            signInRepository.deleteUserData()
        }
    }

    private fun List<Int>.setSignOutBody() : String {
        return if (this.isEmpty()) "" else this.mapWithContent().joinToString(" / ")
    }

    private fun List<Int>.mapWithContent(): List<String> {
        return mutableListOf<String>().apply {
            for (o in this@mapWithContent) {
                add(returnSignOutReasonItems()[o].content)
            }
        }
    }


    /** Dummy **/
    fun returnSignOutReasonItems(): List<SignOutReasonItem> = listOf(
        SignOutReasonItem("이제 이 서비스가 필요하지 않아요", false),
        SignOutReasonItem("어플이 사용하기 어려워요", false),
        SignOutReasonItem("어플에 오류가 있어요", false),
        SignOutReasonItem("재가입을 하고 싶어요", false),
        SignOutReasonItem("기능들이 마음에 들지 않거나 부족해요", false),
        SignOutReasonItem("기타", false)
    )
}