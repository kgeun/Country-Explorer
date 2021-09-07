package com.kgeun.countryexplorer.extension

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView

object CERecyclerViewBinding {
    @JvmStatic
    @BindingAdapter("rvAdapter")
    fun bindAdapter(view: RecyclerView, adapter: RecyclerView.Adapter<*>?) {
        if (adapter == null) {
            return
        }

        view.adapter = adapter
    }
}