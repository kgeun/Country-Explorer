package com.kgeun.countryexplorer.extension

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.kgeun.countryexplorer.network.NetworkState
import kotlinx.coroutines.Dispatchers

inline fun <T, V> ViewModel.liveDataScope(
    crossinline networkCall: suspend () -> T,
    crossinline map: (type: T) -> V
): LiveData<NetworkState<V>> {

    Log.i("kglee", "livedatascope-1")

    return liveData(viewModelScope.coroutineContext + Dispatchers.IO) {
        Log.i("kglee", "livedatascope0")
        emit(NetworkState.Loading)
        Log.i("kglee", "livedatascope1")
        runCatching {
            Log.i("kglee", "livedatascope2")
            networkCall.invoke()
        }.mapCatching(map)
            .onSuccess { data: V ->
                emit(NetworkState.Success(data))
            }.onFailure {
                emit(NetworkState.Error(it))
            }
    }
}