package com.kgeun.countryexplorer.model.response

import android.os.Parcelable
import androidx.room.PrimaryKey
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize
import java.io.Serializable

@Parcelize
@JsonClass(generateAdapter = true)
data class CECountryListResponse(
    val flag: String = "",
    val name: String = "",
    @PrimaryKey
    val alpha3Code: String = "",
    val capital: String = "",
    val region: String = "",
    val subregion: String = "",
    val languages: List<CELanguage> = listOf()
): Serializable, Parcelable