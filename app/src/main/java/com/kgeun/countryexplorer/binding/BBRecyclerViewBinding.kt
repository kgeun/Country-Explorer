package com.kgeun.countryexplorer.binding

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView

object BBRecyclerViewBinding {
    @JvmStatic
    @BindingAdapter("rvAdapter")
    fun bindBreedAdapter(view: RecyclerView, adapter: RecyclerView.Adapter<*>?) {
        if (adapter == null) {
            return
        }

        view.adapter = adapter
    }
}