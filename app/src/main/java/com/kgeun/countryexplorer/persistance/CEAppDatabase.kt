package com.kgeun.countryexplorer.persistance

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.kgeun.countryexplorer.model.entity.CECountryListEntity

@Database(
    entities = [CECountryListEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(CETypeConverter::class)
abstract class CEAppDatabase : RoomDatabase() {
    abstract fun CEMainDao(): CEMainDao
}