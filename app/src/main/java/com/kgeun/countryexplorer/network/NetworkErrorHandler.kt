package com.kgeun.countryexplorer.network

import android.content.Context
import android.widget.Toast
import com.kgeun.countryexplorer.R

class CENetworkHandler<T> (val context: Context) {

    var errorCallBack: (() -> Unit)? = {
        Toast.makeText(context, R.string.communication_error, Toast.LENGTH_SHORT).show()
    }

    var emptyCallBack: (() -> Unit)? = {
        Toast.makeText(context, R.string.internal_server_error, Toast.LENGTH_SHORT).show()
    }

    var loadingCallBack: (() -> Unit)? = null

    fun success (item: NetworkState<T>, callback: (T) -> Unit) {
        when (item) {
            is NetworkState.Success -> {
                callback(item.item)
            }
            is NetworkState.Loading -> {
                loadingCallBack?.let { it() }
            }
            is NetworkState.Error -> {
                errorCallBack?.let { it() }
            }
            is NetworkState.Empty -> {
                loadingCallBack?.let { it() }
            }
        }
    }
}