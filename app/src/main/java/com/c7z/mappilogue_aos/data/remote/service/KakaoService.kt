package com.c7z.mappilogue_aos.data.remote.service

import com.c7z.mappilogue_aos.BuildConfig
import com.c7z.mappilogue_aos.data.remote.response.ResponseKakaoLocation
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface KakaoService {
    @GET("/v2/local/search/keyword.json")
    suspend fun requestKakaoLocationData(
        @Header("Authorization") Authorization : String,
        @Query("query", encoded = true) query : String,
        @Query("page") page : Int
    ) : ResponseKakaoLocation
}