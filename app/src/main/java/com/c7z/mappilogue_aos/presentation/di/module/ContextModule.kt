package com.c7z.mappilogue_aos.presentation.di.module

import android.content.Context
import com.c7z.mappilogue_aos.data.local.source.SharedPreferenceLocalSource
import com.c7z.mappilogue_aos.domain.source.SharedPreferenceSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ContextModule {

    @Provides
    @Singleton
    fun provideContext(@ApplicationContext context : Context) : Context = context

    @Provides
    @Singleton
    fun provideSharedPrefSource() : SharedPreferenceSource = SharedPreferenceLocalSource()
}