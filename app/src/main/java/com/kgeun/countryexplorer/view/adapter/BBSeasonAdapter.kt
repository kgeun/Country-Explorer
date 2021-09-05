package com.kgeun.countryexplorer.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kgeun.countryexplorer.data.model.ui.BBSeasonItem
import com.kgeun.countryexplorer.databinding.ListitemSeasonSelectionBinding

class BBSeasonAdapter(val parentView: ViewGroup, val seasonList: HashMap<Int, BBSeasonItem>?, val buttonCallback: (BBSeasonItem) -> Unit) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        SeasonHolder(
            ListitemSeasonSelectionBinding.inflate(
                LayoutInflater.from(parentView.context), parentView, false
            )
        )

    override fun getItemCount(): Int = seasonList?.size ?: 0

    inner class SeasonHolder(
        private val binding: ListitemSeasonSelectionBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: BBSeasonItem) {
            binding.apply {
                seasonitem = item
                root.isClickable = true
                root.isFocusable = true
                root.isSelected = item.selected
                root.setOnClickListener {
                    root.isSelected = !item.selected
                    buttonCallback(
                        BBSeasonItem(
                            item.text,
                            item.season,
                            !item.selected
                        )
                    )
                }
                executePendingBindings()
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        seasonList?.let { list ->
            seasonList[position + 1]?.let { (holder as SeasonHolder).bind(it) }
        }
    }
}