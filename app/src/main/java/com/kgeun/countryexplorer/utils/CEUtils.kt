package com.kgeun.countryexplorer.utils

import android.content.Context
import android.widget.Toast
import androidx.sqlite.db.SimpleSQLiteQuery
import androidx.sqlite.db.SupportSQLiteQuery
import com.kgeun.countryexplorer.data.model.ui.CEContinentItem

object CEUtils {
    fun errorHandler(context: Context): (String?) -> Unit = {
        Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
    }

    fun numberOfSelectedButtons(list: List<CEContinentItem>?) :Int =
        list?.let { list.filter { it.selected }.toList().size } ?: 0
}