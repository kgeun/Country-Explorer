package com.kgeun.countryexplorer.persistance

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kgeun.countryexplorer.model.entity.CECountryListEntity

@Dao
interface CEMainDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCountries(countryItem: List<CECountryListEntity>)

    @Query("SELECT * FROM country ORDER BY name ASC")
    fun getCountriesList(): LiveData<List<CECountryListEntity>?>

    @Query("SELECT * FROM country ORDER BY name ASC")
    fun getCountryListSync(): List<CECountryListEntity>?

    @Query("SELECT * FROM country WHERE alpha3Code = :code LIMIT 1")
    fun getCountryByCode(code: String): LiveData<CECountryListEntity?>

    @Query("SELECT * FROM country WHERE name LIKE '%' || :keyword  || '%' ORDER BY name ASC")
    fun findCountryByKeywordSync(keyword: String): List<CECountryListEntity>?

    @Query("SELECT * FROM country WHERE region IN (:continents) ORDER BY name ASC")
    fun findCountryByContinentListSync(continents: List<String>): List<CECountryListEntity>?

    @Query("SELECT * FROM country WHERE region IN (:continents) AND name LIKE '%' || :keyword  || '%' ORDER BY name ASC")
    fun findCountryByKeywordAndSeasonListSync(keyword: String, continents: List<String>): List<CECountryListEntity>?

    @Query("DELETE FROM country")
    fun truncateCountries()
}