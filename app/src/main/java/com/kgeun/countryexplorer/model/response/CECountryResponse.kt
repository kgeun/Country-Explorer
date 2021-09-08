package com.kgeun.countryexplorer.model.response

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize
import java.io.Serializable

@Parcelize
@JsonClass(generateAdapter = true)
data class CECountryResponse(
    val flag: String = "",
    val name: String = "",
    val alpha2Code: String = "",
    val alpha3Code: String = "",
    val altSpellings: List<String> = listOf(),
    val capital: String = "",
    val region: String = "",
    val population: Long = 0L,
    val latlng: List<Float> = listOf(0.0f, 0.0f),
    val area: Float = 0f,
    val borders: List<String> = listOf(),
    val nativeName: String = "",
    val subregion: String = "",
    val languages: List<CELanguage> = listOf()
) : Serializable, Parcelable