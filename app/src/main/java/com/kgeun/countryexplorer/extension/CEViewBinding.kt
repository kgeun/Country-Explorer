package com.kgeun.countryexplorer.extension

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
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

}