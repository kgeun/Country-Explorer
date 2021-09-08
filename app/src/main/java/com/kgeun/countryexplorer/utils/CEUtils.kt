package com.kgeun.countryexplorer.utils

import android.content.Context
import android.widget.Toast
import com.kgeun.countryexplorer.presentation.countrylist.data.CEContinentViewItem

object CEUtils {
    fun errorHandler(context: Context): (String?) -> Unit = {
        Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
    }

    fun numberOfSelectedButtons(list: List<CEContinentViewItem>?) :Int =
        list?.let { list.filter { it.selected }.toList().size } ?: 0

}