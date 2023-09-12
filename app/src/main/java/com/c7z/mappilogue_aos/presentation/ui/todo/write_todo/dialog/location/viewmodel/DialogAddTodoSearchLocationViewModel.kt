package com.c7z.mappilogue_aos.presentation.ui.todo.write_todo.dialog.location.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import androidx.paging.PagingSource
import com.c7z.mappilogue_aos.data.remote.response.ResponseKakaoLocation
import com.c7z.mappilogue_aos.domain.repository.KakaoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class DialogAddTodoSearchLocationViewModel @Inject constructor(private val kakaoRepository: KakaoRepository): ViewModel() {

    var userInputContent = MutableLiveData<String>().apply { value = "" }

    fun requestSearch() : Flow<PagingData<ResponseKakaoLocation.Document>> {
        return kakaoRepository.requestKakaoLocationData(userInputContent.value.toString())
    }


}