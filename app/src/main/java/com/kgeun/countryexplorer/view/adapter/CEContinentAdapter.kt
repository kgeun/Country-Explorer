package com.kgeun.countryexplorer.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kgeun.countryexplorer.data.model.ui.CEContinentItem
import com.kgeun.countryexplorer.databinding.ListitemContinentSelectionBinding

class CEContinentAdapter(val parentView: ViewGroup, val continentList: List<CEContinentItem>?, val buttonCallback: (CEContinentItem) -> Unit) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        ContinentHolder(
            ListitemContinentSelectionBinding.inflate(
                LayoutInflater.from(parentView.context), parentView, false
            )
        )

    override fun getItemCount(): Int = continentList?.size ?: 0

    inner class ContinentHolder(
        private val binding: ListitemContinentSelectionBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: CEContinentItem) {
            binding.apply {
                continent = item
                root.isClickable = true
                root.isFocusable = true
                root.isSelected = item.selected
                root.setOnClickListener {
                    root.isSelected = !item.selected
                    buttonCallback(
                        CEContinentItem(
                            item.text,
                            item.region,
                            !item.selected
                        )
                    )
                }
                executePendingBindings()
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        continentList?.get(position).let {
            if (it != null) {
                (holder as ContinentHolder).bind(it)
            }
        }
    }
}