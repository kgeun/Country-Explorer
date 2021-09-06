package com.kgeun.countryexplorer.network

import com.kgeun.countryexplorer.data.model.network.CECountryList
import retrofit2.http.GET

interface CEService {

    @GET("/rest/v2/all?fields=name;capital;region;subregion;flag;alpha3Code;languages")
    suspend fun fetchCountriesList(): List<CECountryList>

    companion object {
        const val REST_COUNTRIES_API_URL = "https://restcountries.eu/"
    }
}