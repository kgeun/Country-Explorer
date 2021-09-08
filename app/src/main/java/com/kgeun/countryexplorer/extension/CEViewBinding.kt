package com.kgeun.countryexplorer.extension

import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.google.common.reflect.Reflection.getPackageName
import com.kgeun.countryexplorer.R
import com.kgeun.countryexplorer.presentation.countrydetail.data.CECountryViewItem
import com.kgeun.countryexplorer.presentation.countrylist.data.CECountryListViewItem


object CEViewBinding {

    @JvmStatic
    @BindingAdapter("setThumbnailUrl")
    fun setThumbnailUrl(view: ImageView, url: String?) {
        if (url == null) {
            return
        }

        Glide.with(view.context)
            .load(url)
            .into(view)
    }

    @JvmStatic
    @BindingAdapter("languageList")
    fun setLanguageList(view: TextView, list: List<CECountryListViewItem.CELanguageViewItem>?) {
        if (list == null) {
            return
        }

        var text = ""

        list.forEachIndexed { index, it ->
            text += it.name
            if (index < list.size - 1) {
                text += "\n"
            }
        }
        text.trim()
        view.text = text
    }

    @JvmStatic
    @BindingAdapter("languageList")
    fun setLanguageList2(view: TextView, list: List<CECountryViewItem.CELanguageItem>?) {
        if (list == null) {
            return
        }

        var text = ""

        list.forEachIndexed { index, it ->
            text += it.name
            if (index < list.size - 1) {
                text += "\n"
            }
        }
        text.trim()
        view.text = text
    }

    @JvmStatic
    @BindingAdapter("mapthumbnail")
    fun setMapThumbnail(view: ImageView, latlng: List<Float>?) {
        if (latlng == null) {
            return
        }

        view.context.apply {
            val applicationInfo: ApplicationInfo =
                this.packageManager.getApplicationInfo(packageName, PackageManager.GET_META_DATA)

            val key = applicationInfo.metaData.getString("mapquestKey")
            val mapUrl =
                "https://www.mapquestapi.com/staticmap/v5/map?key=${key}&center=${latlng[0]},${latlng[1]}" +
                        "&size=800,400&format=jpg70&zoom=4"

            Glide.with(this)
                .load(mapUrl)
                .into(view)
        }
    }

}