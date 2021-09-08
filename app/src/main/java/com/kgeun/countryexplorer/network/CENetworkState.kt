package com.kgeun.countryexplorer.network

sealed class CENetworkState<out T> {
    object Init : CENetworkState<Nothing>()
    object Loading : CENetworkState<Nothing>()
    object Loaded : CENetworkState<Nothing>()
    class Success<out T>(val item: T) : CENetworkState<T>()
    class Error(val throwable: Throwable?) : CENetworkState<Nothing>()
    object Empty : CENetworkState<Nothing>()
}