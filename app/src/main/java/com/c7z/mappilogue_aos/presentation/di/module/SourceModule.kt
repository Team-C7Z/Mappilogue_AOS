package com.c7z.mappilogue_aos.presentation.di.module

import com.c7z.mappilogue_aos.data.remote.source.ScheduleRemoteSource
import com.c7z.mappilogue_aos.data.remote.source.SignInRemoteSource
import com.c7z.mappilogue_aos.domain.source.ScheduleSource
import com.c7z.mappilogue_aos.domain.source.SignInSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class SourceModule {

    @Binds
    abstract fun bindScheduleRemoteSource(scheduleRemoteSource: ScheduleRemoteSource) : ScheduleSource

    @Binds
    abstract fun bindSignInRemoteSource(signInRemoteSource: SignInRemoteSource) : SignInSource
}