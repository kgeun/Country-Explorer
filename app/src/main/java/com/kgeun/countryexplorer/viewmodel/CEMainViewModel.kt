package com.kgeun.countryexplorer.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.kgeun.countryexplorer.CEApplication
import com.kgeun.countryexplorer.R
import com.kgeun.countryexplorer.constants.CEConstants
import com.kgeun.countryexplorer.data.model.network.CECountryList
import com.kgeun.countryexplorer.data.model.ui.CEContinentItem
import com.kgeun.countryexplorer.data.persistance.CEMainDao
import com.kgeun.countryexplorer.network.CEService
import com.kgeun.countryexplorer.utils.CEUtils.createDynamicQueryForKeywordAndContinentSearch
import com.kgeun.countryexplorer.utils.CEUtils.numberOfSelectedButtons
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CEMainViewModel @Inject constructor(
    private val mainDao: CEMainDao,
    private val CEService: CEService
) : ViewModel() {

    var defaultCountriesList = mainDao.getCountriesList()
    var errorLiveData = MutableLiveData<(String?) -> Unit> {}
    var searchKeywordLiveData = MutableLiveData<String>()
    var continentLiveData = MutableLiveData<List<CEContinentItem>?>()
    var searchKeyword = ""
    var countryList: List<CECountryList> = listOf()

    var countriesLiveData = MediatorLiveData<List<CECountryList>?>().apply {

        addSource(defaultCountriesList) { value ->
            setValue(value)
        }

        addSource(searchKeywordLiveData) { value ->
            viewModelScope.launch {
                withContext(Dispatchers.Default) {
                    searchKeyword = value

                    val continentList = continentLiveData.value?.let {
                        continentLiveData.value!!.filter { it.selected }.map { it.region }.toList()
                    } ?: listOf()

                    postValue(
                        if (value == "" && numberOfSelectedButtons(continentLiveData.value) == 0) {
                            mainDao.getCountryListSync()
                        } else if (value == "" && numberOfSelectedButtons(continentLiveData.value) > 0) {
                            mainDao.findCountryByContinentListSync(continentList)
                        } else if (value != "" && numberOfSelectedButtons(continentLiveData.value) == 0) {
                            mainDao.findCountryByKeywordSync(searchKeyword)
                        } else {
                            mainDao.findCountryByKeywordAndSeasonListSync(value, continentList)
                        }
                    )
                }
            }
        }

        addSource(continentLiveData) check@{ value ->
            if (value == null)
                return@check

            viewModelScope.launch {
                withContext(Dispatchers.Default) {
                    val continentList = value.filter { it.selected }.map { it.region }.toList()

                    postValue(
                        if (numberOfSelectedButtons(value) == 0 && searchKeyword == "") {
                            mainDao.getCountryListSync()
                        } else if (numberOfSelectedButtons(value) == 0 && searchKeyword != "") {
                            mainDao.findCountryByKeywordSync(searchKeyword)
                        } else if (numberOfSelectedButtons(value) > 0 && searchKeyword == "") {
                            Log.i("kglee", "mainDao.findCountryByContinentListSync(continentList)")
                            mainDao.findCountryByContinentListSync(continentList)
                        } else {
                            Log.i("kglee", "mainDao.findCountryByKeywordAndSeasonListSync(searchKeyword, continentList)")
                            Log.i("kglee", "numberOfSelectedButtons(value) : ${numberOfSelectedButtons(value)}")
                            Log.i("kglee", "continentList size : ${continentList}")
                            mainDao.findCountryByKeywordAndSeasonListSync(searchKeyword, continentList)
                        }
                    )
                }
            }
        }
    }

    init {
        viewModelScope.launch {
            loadCountriesList {
                (errorLiveData.value)?.let { error -> error(it) }
            }
        }
        searchKeywordLiveData.postValue("")
        continentLiveData.postValue(CEConstants.continentItems.clone() as ArrayList<CEContinentItem>)
    }

    fun getCountryByCode(code: String): LiveData<CECountryList?> {
        return mainDao.getCountryByCode(code)
    }

    suspend fun loadCountriesList(error: (String?) -> Unit) = withContext(Dispatchers.IO) {
        val countriesList = defaultCountriesList.value
        if (countriesList == null || countriesList.isEmpty() ) {
            try {
                saveCountriesData(CEService.fetchCountriesList())
            } catch (e: retrofit2.HttpException) {
                error(CEApplication.instance.applicationContext.getString(R.string.communication_error))
            } catch (e: Exception) {
                error(CEApplication.instance.applicationContext.getString(R.string.unknown_error))
            }
        }
    }

    private fun saveCountriesData(result: List<CECountryList>) {
        mainDao.insertCountries(result)
    }
}