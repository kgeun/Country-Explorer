package com.kgeun.countryexplorer.data.model.network

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize
import java.io.Serializable

@Entity(tableName = "country")
@Parcelize
@JsonClass(generateAdapter = true)
data class CECountryList(
    val flag: String = "",
    val name: String = "",
    @PrimaryKey
    val alpha3Code: String = "",
    val capital: String = "",
    val region: String = "",
    val subregion: String = "",
    val languages: List<CELanguage> = listOf()
): Serializable, Parcelable