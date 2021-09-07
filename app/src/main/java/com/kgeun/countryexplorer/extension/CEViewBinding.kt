package com.kgeun.countryexplorer.extension

import android.content.Intent
import android.net.Uri
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.kgeun.countryexplorer.data.model.network.CELanguage


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
    @BindingAdapter("textList")
    fun setTextList(view: TextView, list: List<String>?) {
        if (list == null) {
            return
        }

        var text = ""

        list.forEachIndexed { index, it ->
            text += it
            if (index < list.size - 1) {
                text += "\n"
            }
        }
        text.trim()
        view.text = text
    }

    @JvmStatic
    @BindingAdapter("languageList")
    fun setLanguageList(view: TextView, list: List<CELanguage>?) {
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
    @BindingAdapter("intList")
    fun setIntList(view: TextView, list: List<Int>?) {
        if (list == null) {
            return
        }

        var text = ""

        list.forEach {
            text += it
            text += " "
        }
        text.trim()
        view.text = text
    }

    @JvmStatic
    @BindingAdapter("seasonList")
    fun setSeasonList(view: TextView, list: List<Int>?) {
        if (list == null) {
            return
        }

        val strBfr = StringBuffer()
        list.forEach {
            strBfr.append("$it ")
        }
        view.text = strBfr.toString()
    }

    @JvmStatic
    @BindingAdapter("setClickInfo")
    fun setClickInfo(view: View, name: String?) {
        if (name == null) {
            return
        }

        view.setOnClickListener {
            view.context.startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://breakingbad.fandom.com/wiki/$name")
                )
            )
        }
    }
}