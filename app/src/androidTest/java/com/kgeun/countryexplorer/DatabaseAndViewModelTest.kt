package com.kgeun.bbcharacterexplorer

import androidx.lifecycle.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.kgeun.bbcharacterexplorer.data.model.network.BBCharacter
import com.kgeun.countryexplorer.viewmodel.CEMainViewModel
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
    fun databaseTest() {

        mainViewModel.viewModelScope.launch {
            val data = BBCharacter(
                0,
                "TEST",
                "test",
                listOf("1"),
                "A",
                "B",
                "C",
                listOf(1,2,3),
                "1",
                "2",
                listOf()
            )
            withContext(Dispatchers.IO) {
                analyticsAdapter.mainDao.truncateCharacters()
                analyticsAdapter.mainDao.insertCharacter(listOf(data))
            }

            // DB, ViewModel Test (livedata)
            analyticsAdapter.mainDao.getCharactersList().observeOnce {
                if (it != null) {
                    assertEquals(data, it[0])
                }
            }
        }
    }

    @Test
    fun testGetCharacterByCharId() {
        mainViewModel.viewModelScope.launch {
            mainViewModel.getCharacterByCharId(1).observeOnce {
                if (it != null) {
                    assertEquals(charactersList?.get(1)!!, it)
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
