package com.kgeun.countryexplorer.model.response

import com.kgeun.countryexplorer.model.entity.CECountryListEntity
import com.kgeun.countryexplorer.model.entity.CELanguageEntity
import com.kgeun.countryexplorer.presentation.countrydetail.data.CECountryViewItem

object CEResponseUtil {
    fun transformResponseToEntity(response: CECountryListResponse): CECountryListEntity
        = response.run {
            CECountryListEntity(
                flag = flag,
                name = name,
                alpha3Code = alpha3Code,
                capital = capital,
                region = region,
                subregion = subregion,
                languages = languages.map {
                    CELanguageEntity(
                        iso639_1 = it.iso639_1,
                        iso639_2 = it.iso639_2,
                        name = it.name,
                        nativeName = it.nativeName
                    )
                } as ArrayList<CELanguageEntity>
            )
        }

    fun transformResponseToViewItem(country: CECountryResponse): CECountryViewItem
        = country.run {
            CECountryViewItem(
                flag = flag,
                name = name,
                alpha2Code = alpha2Code,
                alpha3Code = alpha3Code,
                altSpellings = altSpellings,
                capital = capital,
                region = region,
                population = population.toString(),
                latlng = latlng,
                area = area,
                borders = borders,
                nativeName = nativeName,
                subregion = subregion,
                languages = languages.map {
                    CECountryViewItem.CELanguageItem(
                        iso639_1 = it.iso639_1,
                        iso639_2 = it.iso639_2,
                        name = it.name,
                        nativeName = it.nativeName
                    )
                }
            )
        }
}