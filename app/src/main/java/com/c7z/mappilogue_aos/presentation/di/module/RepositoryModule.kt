package com.c7z.mappilogue_aos.presentation.di.module

import com.c7z.mappilogue_aos.data.remote.repository.KakaoRepositoryImpl
import com.c7z.mappilogue_aos.data.remote.repository.ScheduleRepositoryImpl
import com.c7z.mappilogue_aos.data.remote.repository.SignInRepositoryImpl
import com.c7z.mappilogue_aos.domain.repository.KakaoRepository
import com.c7z.mappilogue_aos.domain.repository.ScheduleRepository
import com.c7z.mappilogue_aos.domain.repository.SignInRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindScheduleRepository(scheduleRepositoryImpl: ScheduleRepositoryImpl) : ScheduleRepository

    @Binds
    abstract fun bindKakaoRepository(kakaoRepositoryImpl: KakaoRepositoryImpl) : KakaoRepository

    @Binds
    abstract fun bindSignInRepository(signInRepositoryImpl: SignInRepositoryImpl) : SignInRepository
}