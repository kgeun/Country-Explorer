package com.kgeun.countryexplorer.view.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.kgeun.countryexplorer.constants.CEConstants
import com.kgeun.countryexplorer.data.model.network.CECountryList
import com.kgeun.countryexplorer.data.model.ui.CEContinentItem
import com.kgeun.countryexplorer.data.persistance.CEMainDao
import com.kgeun.countryexplorer.databinding.FragmentCountryListBinding
import com.kgeun.countryexplorer.view.CEBaseFragment
import com.kgeun.countryexplorer.view.adapter.CECountryAdapter
import com.kgeun.countryexplorer.view.adapter.CEContinentAdapter
import com.kgeun.countryexplorer.viewmodel.CEMainViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CECountryListFragment : CEBaseFragment() {
    private lateinit var binding: FragmentCountryListBinding
    val mainViewModel: CEMainViewModel by viewModels()
    @Inject
    lateinit var mainDao: CEMainDao
    var countryAdapter: CECountryAdapter? = null

    var callback = { item: CEContinentItem ->
        mainViewModel.continentLiveData.postValue(
            mainViewModel.continentLiveData.value.also {
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

    override fun onPause() {
        super.onPause()
//        mainViewModel.searchKeywordLiveData.postValue("")
//        mainViewModel.continentLiveData.value = CEConstants.continentItems.also{ it.map { it.selected = false } }
    }

    private fun bindUi() {
        binding.viewModel = mainViewModel
    }

    private fun subscribeUi() {
        mainViewModel.countriesLiveData.observe(viewLifecycleOwner) {
            if (it != null) {
                if (binding.countryAdapter == null) {
                    binding.countryAdapter = CECountryAdapter(binding.root as ViewGroup, ArrayList(it))
                } else {
                    (binding.countryAdapter as CECountryAdapter).setList(it)
                }
            }
        }

        mainViewModel.continentLiveData.observe(viewLifecycleOwner) {
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