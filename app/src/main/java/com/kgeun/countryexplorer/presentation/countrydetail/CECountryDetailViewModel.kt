package com.kgeun.countryexplorer.presentation.countrydetail

import androidx.lifecycle.*
import androidx.room.PrimaryKey
import com.kgeun.countryexplorer.data.model.network.CECountryResponse
import com.kgeun.countryexplorer.data.model.network.CECountryViewItem
import com.kgeun.countryexplorer.data.model.network.CELanguage
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

    var detailLivedata: LiveData<NetworkState<CECountryViewItem?>>? = null

    suspend fun loadCountryDetail(code: String) {
        detailLivedata = liveDataScope(networkCall = {
            CEService.fetchCountryDetail(code)
        }, map = {
            transformResponseToViewItem(it)
        })
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