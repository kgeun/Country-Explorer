package com.kgeun.countryexplorer.extension

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.kgeun.countryexplorer.constants.CEConstants
import com.kgeun.countryexplorer.network.NetworkState
import kotlinx.coroutines.Dispatchers

inline fun <T, V> ViewModel.liveDataScope(
    crossinline networkCall: suspend () -> T,
    crossinline map: (type: T) -> V
): LiveData<NetworkState<V>> {
    return liveData(viewModelScope.coroutineContext + Dispatchers.IO) {
        emit(NetworkState.Loading)
        runCatching {
            networkCall.invoke()
        }.mapCatching(map)
            .onSuccess { data: V ->
                emit(NetworkState.Success(data))
            }.onFailure {
                emit(NetworkState.Error(it))
            }
    }
}

inline fun <T> ViewModel.callbacks(
    crossinline networkCall: suspend () -> T,
    crossinline onSuccess: (type: T) -> Unit,
    crossinline onFail: (type: Throwable) -> Unit,
): LiveData<Pair<Int, String>> {
    return liveData(viewModelScope.coroutineContext + Dispatchers.IO) {
        Log.i("kglee", "loading 1")
        emit(CEConstants.STATE_LOADING to "Loading")
        runCatching {
            networkCall.invoke()
        }.onSuccess {
            onSuccess(it)
        }.onFailure {
            onFail(it)
        }
    }
}