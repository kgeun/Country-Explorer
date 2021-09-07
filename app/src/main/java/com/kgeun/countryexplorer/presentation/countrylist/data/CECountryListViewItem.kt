package com.kgeun.countryexplorer.presentation.countrylist.data

import com.kgeun.countryexplorer.data.response.network.CELanguageEntity

data class CECountryListViewItem(
    val flag: String?,
    val name: String?,
    val alpha3Code: String?,
    val capital: String?,
    val region: String?,
    val subregion: String?,
    val languages: List<CELanguageEntity>?
)