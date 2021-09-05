package com.kgeun.countryexplorer.utils

import android.content.Context
import android.widget.Toast
import androidx.sqlite.db.SimpleSQLiteQuery
import androidx.sqlite.db.SupportSQLiteQuery
import com.kgeun.countryexplorer.data.model.ui.BBSeasonItem

object BBUtils {
    fun errorHandler(context: Context): (String?) -> Unit = {
        Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
    }

    fun numberOfSelectedSeasons(list :HashMap<Int, BBSeasonItem>?) :Int =
        list?.let { list.values.filter { it.selected }.map { it.season }.toList().size } ?: 0

    fun createDynamicQueryForSeasonSearch(list: List<Int>): SupportSQLiteQuery {
        val args: ArrayList<Any> = ArrayList()

        val sb = StringBuffer("SELECT * FROM character WHERE appearance LIKE ?")
        args.add("%${list[0]}%")

        if (list.size >= 2) {
            for (i in 1 until list.size) {
                sb.append(" AND appearance LIKE ?")
                args.add("%${list[i]}%")
            }
        }

        sb.append(" ORDER BY char_id ASC;")

        return SimpleSQLiteQuery(sb.toString(), args.toArray())
    }

    fun createDynamicQueryForKeywordAndSeasonSearch(value: String, list: List<Int>): SupportSQLiteQuery {
        val args = ArrayList<Any>()

        val sb = StringBuffer("SELECT * FROM character WHERE appearance LIKE ?")
        args.add("%${list[0]}%")

        if (list.size >= 2) {
            for (i in 1 until list.size) {
                sb.append(" AND appearance LIKE ?")
                args.add("%${list[i]}%")
            }
        }

        sb.append(" AND name LIKE ?")
        args.add("%$value%")

        sb.append(" ORDER BY char_id ASC;")

        return SimpleSQLiteQuery(sb.toString(), args.toArray())
    }
}