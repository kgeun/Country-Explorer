package com.kgeun.countryexplorer.binding

import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kgeun.countryexplorer.view.adapter.CECountryAdapter

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