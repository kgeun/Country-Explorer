package com.kgeun.countryexplorer.model.entity

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize
import java.io.Serializable

data class CELanguageEntity(
    val iso639_1: String? = null,
    val iso639_2: String? = null,
    val name: String? = null,
    val nativeName: String? = null
)