package com.c7z.mappilogue_aos.domain.source

interface SharedPreferenceSource {

    suspend fun saveSignInData(access : String, refresh : String)
}