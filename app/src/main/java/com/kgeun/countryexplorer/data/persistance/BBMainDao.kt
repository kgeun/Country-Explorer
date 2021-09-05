package com.kgeun.countryexplorer.data.persistance

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteQuery
import com.kgeun.countryexplorer.data.model.network.BBCharacter

@Dao
interface BBMainDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCharacter(breedItem: List<BBCharacter>)

    @Query("SELECT * FROM character ORDER BY char_id ASC")
    fun getCharactersList(): LiveData<List<BBCharacter>?>

    @Query("SELECT * FROM character ORDER BY char_id ASC")
    fun getCharactersListSync(): List<BBCharacter>?

    @Query("SELECT * FROM character WHERE char_ID = :charId LIMIT 1")
    fun getCharacterByCharId(charId: Long): LiveData<BBCharacter?>

    @Query("SELECT * FROM character WHERE name LIKE '%' || :keyword  || '%' ORDER BY char_id ASC")
    fun findCharactersListByKeywordSync(keyword: String): List<BBCharacter>?

    @RawQuery
    fun findCharactersListBySeasonListSync(query: SupportSQLiteQuery): List<BBCharacter>?

    @RawQuery
    fun findCharactersListByKeywordAndSeasonListSync(query: SupportSQLiteQuery): List<BBCharacter>?

    @Query("DELETE FROM character")
    fun truncateCharacters()
}