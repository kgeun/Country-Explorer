package com.kgeun.countryexplorer.view.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.NavOptions
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.kgeun.countryexplorer.R
import com.kgeun.countryexplorer.data.model.network.CECountryList
import com.kgeun.countryexplorer.databinding.ListitemCountryBinding
import com.kgeun.countryexplorer.view.fragment.CECountryListFragmentDirections

class CECountryAdapter(val parentView: ViewGroup, val countryList: List<CECountryList>?) :
        RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return CharacterHolder(
                ListitemCountryBinding.inflate(
                    LayoutInflater.from(parentView.context), parentView, false
                )
            )
    }

    override fun getItemCount(): Int = countryList?.size ?: 0

    inner class CharacterHolder(
        private val binding: ListitemCountryBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: CECountryList) {
            binding.apply {
                country = item

                cardView.isClickable = true
                cardView.isFocusable = true

                cardView.setOnClickListener {
                    val navBuilder = NavOptions.Builder()
                        .setEnterAnim(R.anim.slide_from_right)
                        .setExitAnim(R.anim.slide_to_left)
                        .setPopEnterAnim(R.anim.slide_from_left)
                        .setPopExitAnim(R.anim.slide_to_right)

                    findNavController(root)
                        .navigate(
                            CECountryListFragmentDirections.listToDetail(item.alpha3Code), navBuilder.build()
                        )
                }
                executePendingBindings()
            }
        }
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        countryList?.let {
            (holder as CharacterHolder).bind(countryList[position])
        }
    }
}