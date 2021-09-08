package com.kgeun.countryexplorer

import androidx.lifecycle.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.kgeun.countryexplorer.constants.CEConstants
import com.kgeun.countryexplorer.model.entity.CECountryListEntity
import com.kgeun.countryexplorer.model.entity.CEEntityUtil
import com.kgeun.countryexplorer.model.entity.CELanguageEntity
import com.kgeun.countryexplorer.presentation.countrylist.CECountryListViewModel
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Types
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.lang.reflect.Type
import javax.inject.Inject

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class DatabaseAndViewModelTest {

    private val context = InstrumentationRegistry.getInstrumentation().targetContext
    private lateinit var countryListViewModel: CECountryListViewModel

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var analyticsAdapter: AnalyticsAdapter

    var countryEntityList: List<CECountryListEntity>? = null

    @Before
    fun init() {
        hiltRule.inject()

        countryListViewModel =
            CECountryListViewModel(analyticsAdapter.mainDao, analyticsAdapter.ceService)


        val charactersInputStream: InputStream = context.assets.open("country_list_result.json")
        val charactersRawString =
            BufferedReader(InputStreamReader(charactersInputStream)).use { it.readText() }

        val listMyData: Type = Types.newParameterizedType(
            List::class.java,
            CECountryListEntity::class.java
        )
        val adapter: JsonAdapter<List<CECountryListEntity>> =
            analyticsAdapter.moshi.adapter<List<CECountryListEntity>>(listMyData)
        countryEntityList = adapter.fromJson(charactersRawString)
    }

    @Test
    fun databaseTest() {

        countryListViewModel.viewModelScope.launch {
            val data = CECountryListEntity(
                "A",
                "B",
                "ABC",
                "A",
                "B",
                "C",
                listOf(CELanguageEntity(name = "A"), CELanguageEntity(name = "B"))
            )
            withContext(Dispatchers.IO) {
                analyticsAdapter.mainDao.truncateCountries()
                analyticsAdapter.mainDao.insertCountries(listOf(data))
            }

            // DB, ViewModel Test (livedata)
            analyticsAdapter.mainDao.getCountriesList().observeOnce {
                if (it != null) {
                    assertEquals(data, it[0])
                }
            }
        }
    }


    @Test
    fun testGetCountryByCode() {
        countryListViewModel.viewModelScope.launch {
            countryListViewModel.getCountryByCode("KOR").observeOnce {
                if (it != null) {
                    assertEquals(
                        countryEntityList?.filter { it.alpha3Code == "KOR" }!!
                            .map(CEEntityUtil::transformEntityToResponse)[0],
                        CEEntityUtil.transformEntityToResponse(it)
                    )
                }
            }
        }
    }


    @Test
    fun testFilteringWithTypingKoreaAndSelectButtonsWithoutAsia() {
        countryListViewModel.viewModelScope.launch {
            countryListViewModel.refreshCountryData()

        }

        countryListViewModel.viewModelScope.launch {
            countryListViewModel.searchKeywordLiveData.postValue("Korea")
            countryListViewModel.continentLiveData.postValue(
                CEConstants.continentItems.also {
                    it.forEach {
                        if (it.region != "Asia") {
                            it.selected = true
                        }
                    }
                }
            )
            countryListViewModel.countriesLiveData.observeOnce {
                if (it != null) {
                    assertEquals(
                        0,
                        it.size
                    )
                }
            }
        }
    }

}

class CustomObserver<T>(private val handler: (T?) -> Unit) : Observer<T>, LifecycleOwner {
    private val lifecycle = LifecycleRegistry(this)

    init {
        lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_RESUME)
    }

    override fun getLifecycle(): Lifecycle = lifecycle

    override fun onChanged(t: T) {
        handler(t)
    }
}

fun <T> LiveData<T>.observeOnce(onChangeHandler: (T?) -> Unit) {
    val observer = CustomObserver(handler = onChangeHandler)
    observe(observer, observer)
}
