package com.kgeun.countryexplorer.presentation.countrydetail

import androidx.lifecycle.*
import com.kgeun.countryexplorer.extension.liveDataScope
import com.kgeun.countryexplorer.model.response.CEResponseUtil.transformResponseToViewItem
import com.kgeun.countryexplorer.network.CEService
import com.kgeun.countryexplorer.network.NetworkState
import com.kgeun.countryexplorer.persistance.CEMainDao
import com.kgeun.countryexplorer.presentation.countrydetail.data.CECountryViewItem
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CECountryDetailViewModel @Inject constructor(
    private val mainDao: CEMainDao,
    private val CEService: CEService
) : ViewModel() {

    var countryDetailLivedata: LiveData<NetworkState<CECountryViewItem?>> = liveData {
        emit(NetworkState.Init)
    }

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
}