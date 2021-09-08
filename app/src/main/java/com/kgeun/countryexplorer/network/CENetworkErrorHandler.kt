package com.kgeun.countryexplorer.network

import android.content.Context
import android.widget.Toast
import com.kgeun.countryexplorer.R

class CENetworkHandler<T>(val context: Context) {

    var errorCallBack: (() -> Unit)? = {
        Toast.makeText(context, R.string.communication_error, Toast.LENGTH_SHORT).show()
    }

    var emptyCallBack: (() -> Unit)? = {
        Toast.makeText(context, R.string.internal_server_error, Toast.LENGTH_SHORT).show()
    }

    var loadingCallBack: (() -> Unit)? = null

    fun success(item: CENetworkState<T>, callback: (T) -> Unit) {
        when (item) {
            is CENetworkState.Success -> {
                callback(item.item)
            }
            is CENetworkState.Loading -> {
                loadingCallBack?.let { it() }
            }
            is CENetworkState.Error -> {
                errorCallBack?.let { it() }
            }
            is CENetworkState.Empty -> {
                loadingCallBack?.let { it() }
            }
        }
    }
}