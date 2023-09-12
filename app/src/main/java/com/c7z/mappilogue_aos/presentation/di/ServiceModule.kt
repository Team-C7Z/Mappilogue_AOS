package com.c7z.mappilogue_aos.presentation.di

import com.c7z.mappilogue_aos.data.remote.service.ScheduleService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ServiceModule {

    @Provides
    @Singleton
    fun provideScheduleService(retrofit: Retrofit) : ScheduleService = retrofit.create(ScheduleService::class.java)
}