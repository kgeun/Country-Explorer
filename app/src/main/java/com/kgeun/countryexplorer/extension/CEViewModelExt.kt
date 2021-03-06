package com.kgeun.countryexplorer.extension

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.kgeun.countryexplorer.network.CENetworkState
import kotlinx.coroutines.Dispatchers

inline fun <T, V> ViewModel.liveDataScope(
    crossinline networkCall: suspend () -> T,
    crossinline map: (type: T) -> V
): LiveData<CENetworkState<V>> {
    return liveData(viewModelScope.coroutineContext + Dispatchers.IO) {
        emit(CENetworkState.Loading)
        runCatching {
            networkCall.invoke()
        }.mapCatching(map)
            .onSuccess { data: V ->
                emit(CENetworkState.Success(data))
            }.onFailure {
                emit(CENetworkState.Error(it))
            }
    }
}