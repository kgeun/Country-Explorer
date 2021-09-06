package com.kgeun.countryexplorer.di

import com.kgeun.countryexplorer.network.CEService
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
class CEApiModule {

    @Singleton
    @Provides
    fun provideRetrofitService(): CEService = Retrofit.Builder()
        .baseUrl(CEService.REST_COUNTRIES_API_URL)
        .addConverterFactory(
            MoshiConverterFactory.create(
                Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
            )
        )
        .build()
        .create(CEService::class.java)
}