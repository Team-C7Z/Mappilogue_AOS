package com.c7z.mappilogue_aos.presentation.di.module

import com.c7z.mappilogue_aos.data.remote.service.KakaoService
import com.c7z.mappilogue_aos.data.remote.service.ScheduleService
import com.c7z.mappilogue_aos.presentation.di.annotation.KakaoRetrofit
import com.c7z.mappilogue_aos.presentation.di.annotation.MappilogueRetrofit
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ServiceModule {

    @Provides
    @Singleton
    fun provideScheduleService(@MappilogueRetrofit retrofit: Retrofit) : ScheduleService = retrofit.create(ScheduleService::class.java)

    @Provides
    @Singleton
    fun provideKakaoService(@KakaoRetrofit retrofit: Retrofit) : KakaoService = retrofit.create(KakaoService::class.java)
}