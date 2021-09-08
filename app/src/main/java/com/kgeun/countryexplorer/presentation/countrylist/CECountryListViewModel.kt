package com.kgeun.countryexplorer.presentation.countrylist

import android.util.Log
import androidx.lifecycle.*
import com.kgeun.countryexplorer.CEApplication
import com.kgeun.countryexplorer.R
import com.kgeun.countryexplorer.constants.CEConstants
import com.kgeun.countryexplorer.extension.callbacks
import com.kgeun.countryexplorer.model.entity.CECountryListEntity
import com.kgeun.countryexplorer.model.entity.CELanguageEntity
import com.kgeun.countryexplorer.model.response.CECountryListResponse
import com.kgeun.countryexplorer.network.CEService
import com.kgeun.countryexplorer.persistance.CEMainDao
import com.kgeun.countryexplorer.presentation.countrylist.data.CEContinentViewItem
import com.kgeun.countryexplorer.presentation.countrylist.data.CECountryListViewItem
import com.kgeun.countryexplorer.utils.CEUtils.numberOfSelectedButtons
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CECountryListViewModel @Inject constructor(
    private val mainDao: CEMainDao,
    private val CEService: CEService
) : ViewModel() {

    val countriesList = mainDao.getCountriesList()
    var searchKeywordLiveData = MutableLiveData<String>().apply { postValue("")}
    var continentLiveData = MutableLiveData<List<CEContinentViewItem>?>().apply { postValue(CEConstants.continentItems.clone() as ArrayList<CEContinentViewItem>) }
    var networkLiveData = MutableLiveData<Pair<Int, String>>().apply { postValue( CEConstants.STATE_LOADING to "Loading" ) }

    var countriesLiveData = MediatorLiveData<List<CECountryListViewItem>?>().apply {
        addSource(countriesList) { value -> setValue(value!!.map(::transformEntityToViewItem)) }

        addSource(searchKeywordLiveData) { value ->
            viewModelScope.launch {
                withContext(Dispatchers.Default) {
                    val continentList = continentLiveData.value?.let {
                        continentLiveData.value!!.filter { it.selected }.map { it.region }.toList()
                    } ?: listOf()

                    val list = if (value == "" && numberOfSelectedButtons(continentLiveData.value) == 0) {
                        mainDao.getCountryListSync()
                    } else if (value == "" && numberOfSelectedButtons(continentLiveData.value) > 0) {
                        mainDao.findCountryByContinentListSync(continentList)
                    } else if (value != "" && numberOfSelectedButtons(continentLiveData.value) == 0) {
                        mainDao.findCountryByKeywordSync(value)
                    } else {
                        mainDao.findCountryByKeywordAndSeasonListSync(value, continentList)
                    }

                    postValue(list!!.map(::transformEntityToViewItem))
                }
            }
        }

        addSource(continentLiveData) check@{ value ->
            if (value == null)
                return@check

            viewModelScope.launch {
                withContext(Dispatchers.Default) {
                    val continentList = value.filter { it.selected }.map { it.region }.toList()
                    val keyword = searchKeywordLiveData.value!!

                    val list = if (numberOfSelectedButtons(value) == 0 && keyword == "") {
                        mainDao.getCountryListSync()
                    } else if (numberOfSelectedButtons(value) == 0 && keyword != "") {
                        mainDao.findCountryByKeywordSync(keyword)
                    } else if (numberOfSelectedButtons(value) > 0 && keyword == "") {
                        mainDao.findCountryByContinentListSync(continentList)
                    } else {
                        mainDao.findCountryByKeywordAndSeasonListSync(keyword, continentList)
                    }

                    postValue(list!!.map(::transformEntityToViewItem))
                }
            }
        }
    }

    init {
        if (countriesList.value == null || countriesList.value!!.isEmpty() ) {
            try {
                viewModelScope.launch {
                    withContext(Dispatchers.Default) {
                        saveCountriesData(
                            CEService.fetchCountriesList().map(::transformResponseToEntity)
                        )
                    }
                }
            } catch (e: Exception) {
                networkLiveData.postValue( CEConstants.STATE_LOADED to "Loaded" )
            }
        }
    }

    fun getCountryByCode(code: String): LiveData<CECountryListEntity?> {
        return mainDao.getCountryByCode(code)
    }

    private fun saveCountriesData(result: List<CECountryListEntity>) {
        mainDao.insertCountries(result)
    }

    private fun transformResponseToEntity(response: CECountryListResponse): CECountryListEntity {
        return response.run {
            CECountryListEntity(
                flag = flag,
                name = name,
                alpha3Code = alpha3Code,
                capital = capital,
                region = region,
                subregion = subregion,
                languages = languages.map {
                    CELanguageEntity(
                        iso639_1 = it.iso639_1,
                        iso639_2 = it.iso639_2,
                        name = it.name,
                        nativeName = it.nativeName
                    )
                } as ArrayList<CELanguageEntity>
            )
        }
    }

    private fun transformEntityToViewItem(entity: CECountryListEntity): CECountryListViewItem {
        return entity.run {
            CECountryListViewItem(
                flag = flag,
                name = name,
                alpha3Code = alpha3Code,
                capital = capital,
                region = region,
                subregion = subregion,
                languages = languages?.map {
                    CECountryListViewItem.CELanguageViewItem(
                        iso639_1 = it.iso639_1,
                        iso639_2 = it.iso639_2,
                        name = it.name,
                        nativeName = it.nativeName
                    )
                }
            )
        }
    }

}