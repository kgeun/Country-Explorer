package com.kgeun.countryexplorer

import com.kgeun.countryexplorer.presentation.countrylist.data.CEContinentViewItem
import com.kgeun.countryexplorer.utils.CEUtils
import org.junit.Assert
import org.junit.Test

class BBLogicTest {
    @Test
    fun checkNumberOfSelectedButtons() {
        val samples = arrayListOf(
            CEContinentViewItem(
                "Asia",
                "Asia",
                true
            ),
            CEContinentViewItem(
                "Europe",
                "Europe",
                false
            ),
            CEContinentViewItem(
                "Africa",
                "Africa",
                true
            ),
            CEContinentViewItem(
                "Oceania",
                "Oceania",
                false
            ),
            CEContinentViewItem(
                "Americas",
                "Americas",
                true
            )
        )
        Assert.assertEquals(3, CEUtils.numberOfSelectedButtons(samples))
    }

}