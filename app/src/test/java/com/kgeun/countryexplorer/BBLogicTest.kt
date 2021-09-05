package com.kgeun.bbcharacterexplorer

import com.kgeun.bbcharacterexplorer.constants.BBConstants
import com.kgeun.bbcharacterexplorer.utils.BBUtils
import org.junit.Assert
import org.junit.Test

class BBLogicTest {
    @Test
    fun testNumberOfSelectedSeasons() {
        Assert.assertEquals(0, BBUtils.numberOfSelectedSeasons(BBConstants.seasonItems))
    }

    @Test
    fun testCreatedDynamicQuery1() {
        val query = BBUtils.createDynamicQueryForSeasonSearch(listOf(1,2,3))

        Assert.assertEquals(
            "SELECT * FROM character WHERE appearance LIKE ? AND appearance LIKE ? AND appearance LIKE ?"
                    + " ORDER BY char_id ASC;", query.sql
        )
    }

    @Test
    fun testCreatedDynamicQuery2() {
        val query = BBUtils.createDynamicQueryForKeywordAndSeasonSearch("value", listOf(1,2,3))

        Assert.assertEquals(
            "SELECT * FROM character WHERE appearance LIKE ? AND appearance LIKE ? AND appearance LIKE ?"
                    + " AND name LIKE ? ORDER BY char_id ASC;", query.sql
        )
    }
}