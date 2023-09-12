package com.c7z.mappilogue_aos.data.remote.source

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.c7z.mappilogue_aos.BuildConfig
import com.c7z.mappilogue_aos.data.remote.response.ResponseKakaoLocation
import com.c7z.mappilogue_aos.data.remote.service.KakaoService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class LocationPagingSource(
    private val ioDispatcher: CoroutineDispatcher,
    private val service: KakaoService,
    private val query : String
) : PagingSource<Int, ResponseKakaoLocation.Document>() {
    override fun getRefreshKey(state: PagingState<Int, ResponseKakaoLocation.Document>): Int? {
        return state.anchorPosition?.let { it ->
            state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ResponseKakaoLocation.Document> {
        return try {
            val page = params.key ?: 1
            val response = withContext(ioDispatcher) {
                service.requestKakaoLocationData("KakaoAK ${BuildConfig.KAKAO_REST_KEY}", query, page)
            }

            val responseLocations = response.documents

            val prevKey = if (page == 1) null else page - 1
            val nextKey = if (response.meta.is_end) null else page + 1

            if(page == 1) (responseLocations as MutableList).add(0, ResponseKakaoLocation.Document("", query, "사용자 지정 위치", "",""))

            LoadResult.Page(
                data = responseLocations,
                prevKey = prevKey,
                nextKey = nextKey
            )
        } catch (exception: Exception) {
            return LoadResult.Error(exception)
        }
    }
}