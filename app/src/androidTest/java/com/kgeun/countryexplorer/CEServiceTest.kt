package com.kgeun.countryexplorer

import androidx.lifecycle.viewModelScope
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.kgeun.countryexplorer.model.response.CECountryListResponse
import com.kgeun.countryexplorer.network.CEService
import com.kgeun.countryexplorer.persistance.CEMainDao
import com.kgeun.countryexplorer.presentation.countrylist.CECountryListViewModel
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.lang.reflect.Type
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AnalyticsAdapter @Inject constructor(
    val mainDao: CEMainDao,
    val ceService: CEService,
    val moshi: Moshi
)

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class CEServiceTest {
    private val context = InstrumentationRegistry.getInstrumentation().targetContext
    private lateinit var countryListViewModel: CECountryListViewModel

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var analyticsAdapter: AnalyticsAdapter

    var countryResponseList: List<CECountryListResponse>? = null

    @Before
    fun init() {
        hiltRule.inject()

        countryListViewModel =
            CECountryListViewModel(analyticsAdapter.mainDao, analyticsAdapter.ceService)
        countryListViewModel.viewModelScope.launch {
            withContext(Dispatchers.IO) {
                analyticsAdapter.mainDao.truncateCountries()
            }
        }

        val countriesInputStream: InputStream = context.assets.open("country_list_result.json")
        val countriesRawString =
            BufferedReader(InputStreamReader(countriesInputStream)).use { it.readText() }

        val listMyData: Type = Types.newParameterizedType(
            List::class.java,
            CECountryListResponse::class.java
        )

        val adapter: JsonAdapter<List<CECountryListResponse>> =
            analyticsAdapter.moshi.adapter<List<CECountryListResponse>>(listMyData)
        countryResponseList = adapter.fromJson(countriesRawString)

    }

    @Test
    fun testCountriesFetch() {
        countryListViewModel.viewModelScope.launch {
            try {
                val result = analyticsAdapter.ceService.fetchCountriesList()
                assertEquals(result[0], countryResponseList?.get(0))

            } catch (e: Exception) {
                throw e
            }
        }
    }

}
