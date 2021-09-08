package com.kgeun.countryexplorer.extension

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

inline fun <T, V> ViewModel.callbacks(
    crossinline networkCall: suspend () -> T,
    crossinline state: (state: Int) -> Unit,
    crossinline onSuccess: (type: T) -> Unit,
    crossinline onFail: (type: Throwable) -> Unit,
): LiveData<NetworkState<V>> {
    return liveData(viewModelScope.coroutineContext + Dispatchers.IO) {
        state(CEConstants.STATE_LOADING)
        runCatching {
            networkCall.invoke()
        }
        .onSuccess {
            state(CEConstants.STATE_SUCCESS)
            onSuccess(it)
        }.onFailure {
            state(CEConstants.STATE_ERROR)
            onFail(it)
        }
    }
}