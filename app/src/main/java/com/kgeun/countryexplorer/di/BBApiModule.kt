package com.kgeun.countryexplorer.di

import com.kgeun.countryexplorer.network.BBService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class BBApiModule {

    @Singleton
    @Provides
    fun provideRetrofitService(): BBService = Retrofit.Builder()
        .baseUrl(BBService.BREAKING_BAD_API_URL)
        .addConverterFactory(
            MoshiConverterFactory.create(
                Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
            )
        )
        .build()
        .create(BBService::class.java)
}