package com.c7z.mappilogue_aos.domain.repository

import androidx.paging.PagingData
import com.c7z.mappilogue_aos.data.remote.response.ResponseKakaoLocation
import kotlinx.coroutines.flow.Flow

interface KakaoRepository {

    fun requestKakaoLocationData(query : String) : Flow<PagingData<ResponseKakaoLocation.Document>>
}