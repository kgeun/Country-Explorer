package com.kgeun.countryexplorer.presentation.countrylist

import androidx.lifecycle.*
import com.kgeun.countryexplorer.constants.CEConstants
import com.kgeun.countryexplorer.model.entity.CECountryListEntity
import com.kgeun.countryexplorer.model.entity.CEEntityUtil.transformEntityToViewItem
import com.kgeun.countryexplorer.model.response.CEResponseUtil.transformResponseToEntity
import com.kgeun.countryexplorer.network.CEService
import com.kgeun.countryexplorer.network.NetworkState
import com.kgeun.countryexplorer.persistance.CEMainDao
import com.kgeun.countryexplorer.presentation.countrylist.data.CEContinentViewItem
import com.kgeun.countryexplorer.presentation.countrylist.data.CECountryListViewItem
import com.kgeun.countryexplorer.utils.CEUtils.numberOfSelectedButtons
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.net.UnknownHostException
import javax.inject.Inject

@HiltViewModel
class CECountryListViewModel @Inject constructor(
    private val mainDao: CEMainDao,
    private val CEService: CEService
) : ViewModel() {

    private val countriesList = mainDao.getCountriesList()
    var searchKeywordLiveData = MutableLiveData<String>().apply { postValue("") }
    var continentLiveData =
        MutableLiveData<List<CEContinentViewItem>?>().apply { postValue(CEConstants.continentItems.clone() as ArrayList<CEContinentViewItem>) }
    var networkLiveData = MutableLiveData<NetworkState<Nothing>>()

    var countriesLiveData = MediatorLiveData<List<CECountryListViewItem>?>().apply {
        addSource(countriesList) { value -> setValue(value!!.map(::transformEntityToViewItem)) }

        addSource(searchKeywordLiveData) { keyword ->
            viewModelScope.launch {
                withContext(Dispatchers.Default) {
                    val continentList = continentLiveData.value?.let {
                        continentLiveData.value!!.filter { it.selected }.map { it.region }.toList()
                    } ?: listOf()

                    postValue(
                        getSubListByCondition(
                            keyword,
                            continentLiveData.value!!,
                            continentList
                        )
                    )
                }
            }
        }

        addSource(continentLiveData) ctnt@{ continent ->
            if (continent == null) {
                return@ctnt
            }

            viewModelScope.launch {
                withContext(Dispatchers.Default) {
                    val continentList = continent.filter { it.selected }.map { it.region }.toList()
                    val keyword = searchKeywordLiveData.value!!

                    postValue(
                        getSubListByCondition(
                            keyword,
                            continentLiveData.value!!,
                            continentList
                        )
                    )
                }
            }
        }
    }

    init {
        if (countriesList.value == null || countriesList.value!!.isEmpty()) {
            refreshCountryData()
        }
    }

    private fun getSubListByCondition(
        keyword: String,
        continentViewList: List<CEContinentViewItem>,
        continentList: List<String>
    ): List<CECountryListViewItem> {

        val list = if (keyword == "" && numberOfSelectedButtons(continentViewList) == 0) {
            mainDao.getCountryListSync()
        } else if (keyword == "" && numberOfSelectedButtons(continentViewList) > 0) {
            mainDao.findCountryByContinentListSync(continentList)
        } else if (keyword != "" && numberOfSelectedButtons(continentViewList) == 0) {
            mainDao.findCountryByKeywordSync(keyword)
        } else {
            mainDao.findCountryByKeywordAndSeasonListSync(keyword, continentList)
        }

        return list!!.map(::transformEntityToViewItem)
    }

    fun getCountryByCode(code: String): LiveData<CECountryListEntity?> {
        return mainDao.getCountryByCode(code)
    }

    fun refreshCountryData() {
        networkLiveData.postValue(NetworkState.Loading)
        viewModelScope.launch {
            withContext(Dispatchers.Default) {
                if (countriesList.value == null || countriesList.value!!.isEmpty()) {
                    try {
                        mainDao.insertCountries(
                            CEService.fetchCountriesList().map(::transformResponseToEntity)
                        )
                    } catch (e: Exception) {
                        networkLiveData.postValue(NetworkState.Error(e))
                        e.printStackTrace()
                    }
                }
            }
        }
    }
}