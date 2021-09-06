package com.kgeun.countryexplorer.data.persistance

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.kgeun.countryexplorer.data.model.network.CECountryList
import com.kgeun.countryexplorer.utils.CETypeConverter

@Database(
    entities = [CECountryList::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(CETypeConverter::class)
abstract class CEAppDatabase : RoomDatabase() {
    abstract fun CEMainDao(): CEMainDao
}