package com.kgeun.countryexplorer.network

import com.kgeun.countryexplorer.model.entity.CECountryListEntity
import com.kgeun.countryexplorer.model.response.CECountryListResponse
import com.kgeun.countryexplorer.model.response.CECountryResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface CEService {

    @GET("/rest/v2/all?fields=name;capital;region;subregion;flag;alpha3Code;languages")
    suspend fun fetchCountriesList(): List<CECountryListResponse>

    @GET("/rest/v2/alpha/{code}")
    suspend fun fetchCountryDetail(
        @Path("code") alphaCode: String
    ): CECountryResponse

    companion object {
        const val REST_COUNTRIES_API_URL = "https://restcountries.eu/"
    }
}