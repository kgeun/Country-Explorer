package com.kgeun.countryexplorer.data.persistance

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kgeun.countryexplorer.data.model.network.CECountryListResponse

@Dao
interface CEMainDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCountries(countryItem: List<CECountryListResponse>)

    @Query("SELECT * FROM country ORDER BY name ASC")
    fun getCountriesList(): LiveData<List<CECountryListResponse>?>

    @Query("SELECT * FROM country ORDER BY name ASC")
    fun getCountryListSync(): List<CECountryListResponse>?

    @Query("SELECT * FROM country WHERE alpha3Code = :code LIMIT 1")
    fun getCountryByCode(code: String): LiveData<CECountryListResponse?>

    @Query("SELECT * FROM country WHERE name LIKE '%' || :keyword  || '%' ORDER BY name ASC")
    fun findCountryByKeywordSync(keyword: String): List<CECountryListResponse>?

    @Query("SELECT * FROM country WHERE region IN (:continents) ORDER BY name ASC")
    fun findCountryByContinentListSync(continents: List<String>): List<CECountryListResponse>?

    @Query("SELECT * FROM country WHERE region IN (:continents) AND name LIKE '%' || :keyword  || '%' ORDER BY name ASC")
    fun findCountryByKeywordAndSeasonListSync(keyword: String, continents: List<String>): List<CECountryListResponse>?

    @Query("DELETE FROM country")
    fun truncateCountries()
}