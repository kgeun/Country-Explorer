package com.kgeun.countryexplorer.data.persistance

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.kgeun.countryexplorer.data.model.network.BBCharacter
import com.kgeun.countryexplorer.utils.BBTypeConverter

@Database(
    entities = [BBCharacter::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(BBTypeConverter::class)
abstract class BBAppDatabase : RoomDatabase() {
    abstract fun BBMainDao(): BBMainDao
}