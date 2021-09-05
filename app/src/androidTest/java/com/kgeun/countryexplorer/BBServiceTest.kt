package com.kgeun.bbcharacterexplorer

import androidx.lifecycle.viewModelScope
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.kgeun.bbcharacterexplorer.data.model.network.BBCharacter
import com.kgeun.bbcharacterexplorer.data.persistance.BBMainDao
import com.kgeun.bbcharacterexplorer.network.BBService
import com.kgeun.countryexplorer.viewmodel.CEMainViewModel
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
    val mainDao: BBMainDao,
    val bbService: BBService,
    val moshi: Moshi
)

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class BBServiceTest {
    private val context = InstrumentationRegistry.getInstrumentation().targetContext
    private lateinit var mainViewModel: CEMainViewModel

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var analyticsAdapter: AnalyticsAdapter

    var charactersList: List<BBCharacter>? = null

    @Before
    fun init() {
        hiltRule.inject()

        mainViewModel = CEMainViewModel(analyticsAdapter.mainDao, analyticsAdapter.bbService)
        mainViewModel.viewModelScope.launch {
            withContext(Dispatchers.IO) {
                analyticsAdapter.mainDao.truncateCharacters()
            }
        }

        val charactersInputStream: InputStream = context.assets.open("character_result.json")
        val charactersRawString =
            BufferedReader(InputStreamReader(charactersInputStream)).use { it.readText() }

        val listMyData: Type = Types.newParameterizedType(
            List::class.java,
            BBCharacter::class.java
        )
        val adapter: JsonAdapter<List<BBCharacter>> = analyticsAdapter.moshi.adapter<List<BBCharacter>>(listMyData)
        charactersList = adapter.fromJson(charactersRawString)

    }

    @Test
    fun testCharacterFetch() {
        mainViewModel.viewModelScope.launch {
            try {
                val result = analyticsAdapter.bbService.fetchCharacters()
                assertEquals(result[0], charactersList?.get(0))

            } catch (e: Exception) {
                throw e
            }
        }
    }
}
