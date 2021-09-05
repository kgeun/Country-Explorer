package com.kgeun.countryexplorer.di

import android.content.Context
import androidx.room.Room
import com.kgeun.countryexplorer.data.persistance.BBAppDatabase
import com.kgeun.countryexplorer.data.persistance.BBMainDao
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class BBDatabaseModule {
    @Provides
    @Singleton
    fun provideMainDao(appDatabase: BBAppDatabase): BBMainDao = appDatabase.BBMainDao()

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): BBAppDatabase {
        return Room.databaseBuilder(
            appContext,
            BBAppDatabase::class.java,
            "breaking_bad"
        ).build()
    }

    @Provides
    @Singleton
    fun provideMoshi(): Moshi {
        return Moshi.Builder()
            .addLast(KotlinJsonAdapterFactory())
            .build()
    }
}