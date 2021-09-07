package com.kgeun.countryexplorer.data.response.network

import android.os.Parcelable
import androidx.room.PrimaryKey
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize
import java.io.Serializable

@Parcelize
@JsonClass(generateAdapter = true)
data class CECountryResponse(
    val flag: String = "",
    val name: String = "",
    val alpha3Code: String = "",
    val capital: String = "",
    val region: String = "",
    val subregion: String = "",
    val languages: List<CELanguage> = listOf()
): Serializable, Parcelable