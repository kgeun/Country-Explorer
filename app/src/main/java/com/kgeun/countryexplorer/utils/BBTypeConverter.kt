package com.kgeun.countryexplorer.utils

import androidx.room.TypeConverter

class BBTypeConverter {
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