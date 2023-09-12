package com.c7z.mappilogue_aos.data.remote.repository

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.c7z.mappilogue_aos.data.remote.response.ResponseKakaoLocation
import com.c7z.mappilogue_aos.data.remote.service.KakaoService
import com.c7z.mappilogue_aos.data.remote.source.LocationPagingSource
import com.c7z.mappilogue_aos.domain.repository.KakaoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class KakaoRepositoryImpl @Inject constructor(private val service: KakaoService) : KakaoRepository {
    override fun requestKakaoLocationData(query: String): Flow<PagingData<ResponseKakaoLocation.Document>> {
        return Pager(PagingConfig(pageSize = 15)) {
            LocationPagingSource(Dispatchers.IO, service, query)
        }.flow
    }
}