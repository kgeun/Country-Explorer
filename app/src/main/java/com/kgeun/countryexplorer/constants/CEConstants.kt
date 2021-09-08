package com.kgeun.countryexplorer.constants

import com.kgeun.countryexplorer.presentation.countrylist.data.CEContinentViewItem

object CEConstants {

    val STATE_LOADING = 0
    val STATE_SUCCESS = 1
    val STATE_ERROR = -1

    val continentItems = arrayListOf(
        CEContinentViewItem(
            "Asia",
            "Asia",
            false
        ),
        CEContinentViewItem(
            "Europe",
            "Europe",
            false
        ),
        CEContinentViewItem(
            "Africa",
            "Africa",
            false
        ),
        CEContinentViewItem(
            "Oceania",
            "Oceania",
            false
        ),
        CEContinentViewItem(
            "Americas",
            "Americas",
            false
        ),
        CEContinentViewItem(
            "Polar",
            "Polar",
            false
        ),
        CEContinentViewItem(
            "etc",
            "",
            false
        )
    )
}