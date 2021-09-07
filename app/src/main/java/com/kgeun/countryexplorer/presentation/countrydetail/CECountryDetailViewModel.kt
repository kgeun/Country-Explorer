package com.kgeun.countryexplorer.presentation.countrydetail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.kgeun.countryexplorer.data.model.network.CECountryResponse
import com.kgeun.countryexplorer.data.model.network.CECountryViewItem
import com.kgeun.countryexplorer.data.persistance.CEMainDao
import com.kgeun.countryexplorer.extension.liveDataScope
import com.kgeun.countryexplorer.network.CEService
import com.kgeun.countryexplorer.network.NetworkState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CECountryDetailViewModel @Inject constructor(
    private val mainDao: CEMainDao,
    private val CEService: CEService
) : ViewModel() {

    var countryDetailLivedata: LiveData<NetworkState<CECountryViewItem?>>
    private val _countryDetailLiveData = MutableLiveData<String>()

    init {
        countryDetailLivedata = _countryDetailLiveData.switchMap { code ->
            liveDataScope(networkCall = {
                CEService.fetchCountryDetail(code)
            }, map = {
                transformResponseToViewItem(it)
            })
        }
    }

    fun getCountryDetail(code: String) {
        _countryDetailLiveData.value = code
    }

    private fun transformResponseToViewItem(country: CECountryResponse): CECountryViewItem  {
        return country.run {
            CECountryViewItem(
                flag = flag,
                name = name,
                alpha3Code = alpha3Code,
                capital = capital,
                region = region,
                subregion = subregion,
                languages = languages
            )
        }
    }
}