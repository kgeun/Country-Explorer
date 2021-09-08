package com.kgeun.countryexplorer.persistance

import androidx.room.TypeConverter
import com.kgeun.countryexplorer.model.entity.CELanguageEntity

class CETypeConverter {

    @TypeConverter
    fun gettingStringListFromString(items: String): ArrayList<CELanguageEntity> {
        val list = arrayListOf<CELanguageEntity>()

        val array = items.split(",".toRegex()).dropLastWhile {
            it.isEmpty()
        }.toTypedArray()

        for (s in array) {
            if (s.isNotEmpty()) {
                list.add(
                    CELanguageEntity(
                        name = s
                    )
                )
            }
        }

        return list
    }

    @TypeConverter
    fun writingStringFromLanguageList(list: List<CELanguageEntity>): String {
        var items = ""
        for (i in list) items += ",${i.name}"
        return items
    }
}