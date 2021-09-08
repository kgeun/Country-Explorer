package com.kgeun.countryexplorer.presentation.countrylist.data

data class CECountryListViewItem(
    val flag: String? = "",
    val name: String? = "",
    val alpha3Code: String? = "",
    val capital: String? = "",
    val region: String? = "",
    val subregion: String? = "",
    val languages: List<CELanguageViewItem>?
) {
    data class CELanguageViewItem(
        val iso639_1: String? = "",
        val iso639_2: String? = "",
        val name: String? = "",
        val nativeName: String? = ""
    )
}