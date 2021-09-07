package com.kgeun.countryexplorer.utils

import androidx.room.TypeConverter
import com.kgeun.countryexplorer.data.response.network.CELanguageEntity

class CETypeConverter {
    @TypeConverter
    fun gettingListFromString(items: String): List<Int> {
        val list = mutableListOf<Int>()

        val array = items.split(",".toRegex()).dropLastWhile {
            it.isEmpty()
        }.toTypedArray()

        for (s in array) {
            if (s.isNotEmpty()) {
                list.add(s.toInt())
            }
        }
        return list
    }

    @TypeConverter
    fun writingStringFromList(list: List<Int>): String {
        var items=""
        for (i in list) items += ",$i"
        return items
    }

    @TypeConverter
    fun gettingStringListFromString(items: String): List<CELanguageEntity> {
        val list = mutableListOf<CELanguageEntity>()

        val array = items.split(",".toRegex()).dropLastWhile {
            it.isEmpty()
        }.toTypedArray()

        for (s in array) {
            if (s.isNotEmpty()) {
                list.add(
                    CELanguageEntity(
                        name=s
                    )
                )
            }
        }

        return list
    }

    @TypeConverter
    fun writingStringFromLanguageList(list: List<CELanguageEntity>): String {
        var items=""
        for (i in list) items += ",${i.name}"
        return items
    }

    @TypeConverter
    fun gettingListFromInt(items: String): List<String> {
        val list = mutableListOf<String>()

        val array = items.split(",".toRegex()).dropLastWhile {
            it.isEmpty()
        }.toTypedArray()

        for (s in array) {
            if (s.isNotEmpty()) {
                list.add(s)
            }
        }
        return list
    }

    @TypeConverter
    fun writingIntFromList(list: List<String>): String {
        var items=""
        for (i in list) items += ",$i"
        return items
    }
}