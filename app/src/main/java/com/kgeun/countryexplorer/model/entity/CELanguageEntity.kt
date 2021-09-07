package com.kgeun.countryexplorer.data.response.network

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize
import java.io.Serializable

data class CELanguageEntity(
    val iso639_1: String?,
    val iso639_2: String?,
    val name: String?,
    val nativeName: String?
)