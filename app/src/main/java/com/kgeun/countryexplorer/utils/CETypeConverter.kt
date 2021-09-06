package com.kgeun.countryexplorer.utils

import androidx.room.TypeConverter
import com.kgeun.countryexplorer.data.model.network.CELanguage

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
    fun gettingStringListFromString(items: String): List<CELanguage> {
        val list = mutableListOf<CELanguage>()

        val array = items.split(",".toRegex()).dropLastWhile {
            it.isEmpty()
        }.toTypedArray()

        for (s in array) {
            if (s.isNotEmpty()) {
                list.add(
                    CELanguage(
                        name=s
                    )
                )
            }
        }

        return list
    }

    @TypeConverter
    fun writingStringFromLanguageList(list: List<CELanguage>): String {
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