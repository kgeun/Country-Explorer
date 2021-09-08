package com.kgeun.countryexplorer.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.kgeun.countryexplorer.model.entity.CELanguageEntity

@Entity(tableName = "country")
data class CECountryListEntity(
    val flag: String = "",
    val name: String = "",
    @PrimaryKey
    val alpha3Code: String,
    val capital: String = "",
    val region: String = "",
    val subregion: String = "",
    val languages: ArrayList<CELanguageEntity> = arrayListOf()
)