package com.kgeun.countryexplorer.data.persistance

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteQuery
import com.kgeun.countryexplorer.data.model.network.CECountryList

@Dao
interface CEMainDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCountries(countryItem: List<CECountryList>)

    @Query("SELECT * FROM country ORDER BY name ASC")
    fun getCountriesList(): LiveData<List<CECountryList>?>

    @Query("SELECT * FROM country ORDER BY name ASC")
    fun getCountryListSync(): List<CECountryList>?

    @Query("SELECT * FROM country WHERE alpha3Code = :code LIMIT 1")
    fun getCountryByCode(code: String): LiveData<CECountryList?>

    @Query("SELECT * FROM country WHERE name LIKE '%' || :keyword  || '%' ORDER BY name ASC")
    fun findCountryByKeywordSync(keyword: String): List<CECountryList>?

    @Query("SELECT * FROM country WHERE region IN (:continents) ORDER BY name ASC")
    fun findCountryByContinentListSync(continents: List<String>): List<CECountryList>?

    @Query("SELECT * FROM country WHERE region IN (:continents) AND name LIKE '%' || :keyword  || '%' ORDER BY name ASC")
    fun findCountryByKeywordAndSeasonListSync(keyword: String, continents: List<String>): List<CECountryList>?

    @Query("DELETE FROM country")
    fun truncateCountries()
}