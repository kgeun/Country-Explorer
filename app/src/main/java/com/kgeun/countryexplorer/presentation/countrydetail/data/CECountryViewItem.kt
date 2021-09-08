package com.kgeun.countryexplorer.presentation.countrydetail.data

data class CECountryViewItem(
    val flag: String = "",
    val name: String = "",
    val alpha2Code: String = "",
    val alpha3Code: String = "",
    val altSpellings: List<String> = listOf(),
    val capital: String = "",
    val region: String = "",
    val population: String = "",
    val latlng: List<Float> = listOf(0.0f, 0.0f),
    val area: Float = 0f,
    val borders: List<String> = listOf(),
    val nativeName: String = "",
    val subregion: String = "",
    val languages: List<CELanguageItem> = listOf()
) {
    data class CELanguageItem(
        val iso639_1: String = "",
        val iso639_2: String = "",
        val name: String = "",
        val nativeName: String = ""
    )
}