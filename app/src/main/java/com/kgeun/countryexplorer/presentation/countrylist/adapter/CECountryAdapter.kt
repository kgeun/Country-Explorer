package com.kgeun.countryexplorer.presentation.countrylist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.NavOptions
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.kgeun.countryexplorer.R
import com.kgeun.countryexplorer.databinding.ListitemCountryBinding
import com.kgeun.countryexplorer.presentation.countrylist.CECountryListFragmentDirections
import com.kgeun.countryexplorer.presentation.countrylist.data.CECountryListViewItem

class CECountryAdapter(
    val parentView: ViewGroup,
    val countryList: ArrayList<CECountryListViewItem>?
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return CountryHolder(
            ListitemCountryBinding.inflate(
                LayoutInflater.from(parentView.context), parentView, false
            )
        )
    }

    override fun getItemCount(): Int {
        return countryList?.size ?: 0
    }

    fun setList(list: List<CECountryListViewItem>) {
        countryList!!.clear()
        countryList!!.addAll(list)
        notifyDataSetChanged()
    }

    inner class CountryHolder(
        private val binding: ListitemCountryBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: CECountryListViewItem) {
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
                            CECountryListFragmentDirections.listToDetail(item.alpha3Code!!),
                            navBuilder.build()
                        )
                }
                executePendingBindings()
            }
        }
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        countryList?.let {
            (holder as CountryHolder).bind(it[position])
        }
    }
}