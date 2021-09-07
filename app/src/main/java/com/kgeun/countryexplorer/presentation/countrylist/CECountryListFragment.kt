package com.kgeun.countryexplorer.presentation.countrylist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.kgeun.countryexplorer.persistance.CEMainDao
import com.kgeun.countryexplorer.databinding.FragmentCountryListBinding
import com.kgeun.countryexplorer.presentation.CEBaseFragment
import com.kgeun.countryexplorer.presentation.countrylist.adapter.CEContinentAdapter
import com.kgeun.countryexplorer.presentation.countrylist.adapter.CECountryAdapter
import com.kgeun.countryexplorer.presentation.countrylist.data.CEContinentViewItem
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CECountryListFragment : CEBaseFragment() {
    private lateinit var binding: FragmentCountryListBinding
    val countryListViewModel: CECountryListViewModel by viewModels()
    @Inject
    lateinit var mainDao: CEMainDao
    var countryAdapter: CECountryAdapter? = null

    var callback = { item: CEContinentViewItem ->
        countryListViewModel.continentLiveData.postValue(
            countryListViewModel.continentLiveData.value.also {
                it?.forEach checked@{
                    if (it.text == item.text) {
                        it.selected = item.selected
                        return@checked
                    }
                }
            }
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentCountryListBinding.inflate(inflater, container, false)

        subscribeUi()
        bindUi()
        return binding.root
    }

    private fun bindUi() {
        binding.viewModel = countryListViewModel
    }

    private fun subscribeUi() {
        countryListViewModel.countriesLiveData.observe(viewLifecycleOwner) {
            if (it != null) {
                if (binding.countryAdapter == null) {
                    binding.countryAdapter = CECountryAdapter(binding.root as ViewGroup, ArrayList(it))
                } else {
                    (binding.countryAdapter as CECountryAdapter).setList(it)
                }
            }
        }

        countryListViewModel.continentLiveData.observe(viewLifecycleOwner) {
            if (it != null) {
                if (binding.continentAdapter == null) {
                    binding.continentAdapter = CEContinentAdapter(binding.root as ViewGroup, it, callback)
                } else {
                    binding.continentAdapter!!.notifyDataSetChanged()
                }
            }
        }
    }
}