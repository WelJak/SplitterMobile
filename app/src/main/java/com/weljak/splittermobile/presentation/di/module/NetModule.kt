package com.weljak.splittermobile.presentation.di.module

import com.weljak.splittermobile.BuildConfig
import com.weljak.splittermobile.data.api.SplitterApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetModule {
    @Singleton
    @Provides
    fun provideRetroFit(): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BuildConfig.BASE_URL)
            .build()
    }

    @Singleton
    @Provides
    fun provideSplitterApiService(retrofit: Retrofit): SplitterApiService {
        return retrofit.create(SplitterApiService::class.java)
    }
}