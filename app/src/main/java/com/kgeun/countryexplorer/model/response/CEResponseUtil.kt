package com.kgeun.countryexplorer.model.response

import com.kgeun.countryexplorer.model.entity.CECountryListEntity
import com.kgeun.countryexplorer.model.entity.CELanguageEntity

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
}