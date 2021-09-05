package com.kgeun.countryexplorer.constants

import com.kgeun.countryexplorer.data.model.ui.BBSeasonItem

object BBConstants {
    val seasonItems = hashMapOf<Int, BBSeasonItem>(
        1 to BBSeasonItem(
            "Season 1",
            1,
            false
        ),
        2 to BBSeasonItem(
            "Season 2",
            2,
            false
        ),
        3 to BBSeasonItem(
            "Season 3",
            3,
            false
        ),
        4 to BBSeasonItem(
            "Season 4",
            4,
            false
        ),
        5 to BBSeasonItem(
            "Season 5",
            5,
            false
        )
    )
}