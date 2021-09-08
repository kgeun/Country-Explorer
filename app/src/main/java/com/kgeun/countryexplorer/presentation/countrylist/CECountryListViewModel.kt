package com.kgeun.countryexplorer.presentation.countrylist

import android.util.Log
import androidx.lifecycle.*
import com.kgeun.countryexplorer.CEApplication
import com.kgeun.countryexplorer.R
import com.kgeun.countryexplorer.constants.CEConstants
import com.kgeun.countryexplorer.extension.callbacks
import com.kgeun.countryexplorer.extension.liveDataScope
import com.kgeun.countryexplorer.model.entity.CECountryListEntity
import com.kgeun.countryexplorer.model.entity.CELanguageEntity
import com.kgeun.countryexplorer.model.response.CECountryListResponse
import com.kgeun.countryexplorer.persistance.CEMainDao
import com.kgeun.countryexplorer.network.CEService
import com.kgeun.countryexplorer.network.NetworkState
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
    var searchKeywordLiveData = MutableLiveData<String>()
    var continentLiveData = MutableLiveData<List<CEContinentViewItem>?>()
    var searchKeyword = ""

    var countriesLiveData = MediatorLiveData<List<CECountryListViewItem>?>().apply {
        addSource(countriesList) { value -> setValue(value!!.map(::transformEntityToViewItem)) }

        addSource(searchKeywordLiveData) { value ->
            viewModelScope.launch {
                withContext(Dispatchers.Default) {
                    searchKeyword = value

                    val continentList = continentLiveData.value?.let {
                        continentLiveData.value!!.filter { it.selected }.map { it.region }.toList()
                    } ?: listOf()

                    val list = if (value == "" && numberOfSelectedButtons(continentLiveData.value) == 0) {
                        mainDao.getCountryListSync()
                    } else if (value == "" && numberOfSelectedButtons(continentLiveData.value) > 0) {
                        mainDao.findCountryByContinentListSync(continentList)
                    } else if (value != "" && numberOfSelectedButtons(continentLiveData.value) == 0) {
                        mainDao.findCountryByKeywordSync(searchKeyword)
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

                    val list = if (numberOfSelectedButtons(value) == 0 && searchKeyword == "") {
                        mainDao.getCountryListSync()
                    } else if (numberOfSelectedButtons(value) == 0 && searchKeyword != "") {
                        mainDao.findCountryByKeywordSync(searchKeyword)
                    } else if (numberOfSelectedButtons(value) > 0 && searchKeyword == "") {
                        mainDao.findCountryByContinentListSync(continentList)
                    } else {
                        mainDao.findCountryByKeywordAndSeasonListSync(searchKeyword, continentList)
                    }

                    postValue(list!!.map(::transformEntityToViewItem))
                }
            }
        }
    }

    init {
        val countriesListValue = countriesList.value
        if (countriesListValue == null || countriesListValue.isEmpty() ) {
            try {
                viewModelScope.launch {
                    withContext(Dispatchers.Default) {
                        saveCountriesData(
                            CEService.fetchCountriesList().map(::transformResponseToEntity)
                        )
                    }
                }
            } catch (e: retrofit2.HttpException) {
                error(CEApplication.instance.applicationContext.getString(R.string.communication_error))
            } catch (e: Exception) {
                error(CEApplication.instance.applicationContext.getString(R.string.unknown_error))
            }
        }

        searchKeywordLiveData.postValue("")
        continentLiveData.postValue(CEConstants.continentItems.clone() as ArrayList<CEContinentViewItem>)
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