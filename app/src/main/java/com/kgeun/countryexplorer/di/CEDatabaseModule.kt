package com.kgeun.countryexplorer.di

import android.content.Context
import androidx.room.Room
import com.kgeun.countryexplorer.data.persistance.CEAppDatabase
import com.kgeun.countryexplorer.data.persistance.CEMainDao
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
class CEDatabaseModule {
    @Provides
    @Singleton
    fun provideMainDao(appDatabase: CEAppDatabase): CEMainDao = appDatabase.CEMainDao()

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): CEAppDatabase {
        return Room.databaseBuilder(
            appContext,
            CEAppDatabase::class.java,
            "Country_Explorer"
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