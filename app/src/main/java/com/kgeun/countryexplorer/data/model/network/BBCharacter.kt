package com.kgeun.countryexplorer.data.model.network

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize
import java.io.Serializable


@Entity(tableName = "character")
@Parcelize
@JsonClass(generateAdapter = true)
data class BBCharacter(
    @PrimaryKey
    val char_id: Long = 0L,
    val name: String = "",
    val birthday: String = "",
    val occupation: List<String> = listOf(),
    val img: String = "",
    val status: String = "",
    val nickname: String = "",
    val appearance: List<Int> = listOf(),
    val portrayed: String = "",
    val category: String,
    val better_call_saul_appearance: List<Int> = listOf()
): Serializable, Parcelable