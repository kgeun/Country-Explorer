package com.kgeun.countryexplorer.presentation.countrydetail.data

data class CECountryViewItem(
    val flag: String = "",
    val name: String = "",
    val alpha3Code: String = "",
    val capital: String = "",
    val region: String = "",
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