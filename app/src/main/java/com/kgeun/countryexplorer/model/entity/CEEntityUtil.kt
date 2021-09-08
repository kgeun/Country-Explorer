package com.kgeun.countryexplorer.model.entity

import com.kgeun.countryexplorer.presentation.countrylist.data.CECountryListViewItem

object CEEntityUtil {
    fun transformEntityToViewItem(entity: CECountryListEntity): CECountryListViewItem =
        entity.run {
            CECountryListViewItem(
                flag = flag,
                name = name,
                alpha3Code = alpha3Code,
                capital = capital,
                region = region,
                subregion = subregion,
                languages = languages?.map {
                    CECountryListViewItem.CELanguageViewItem(
                        iso639_1 = it.iso639_1,
                        iso639_2 = it.iso639_2,
                        name = it.name,
                        nativeName = it.nativeName
                    )
                }
            )
        }
}