package com.c7z.mappilogue_aos.presentation.di

import com.c7z.mappilogue_aos.data.remote.repository.ScheduleRepositoryImpl
import com.c7z.mappilogue_aos.domain.repository.ScheduleRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindScheduleRepository(scheduleRepositoryImpl: ScheduleRepositoryImpl) : ScheduleRepository
}