package com.streamliners.base.ext

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.streamliners.base.BaseActivity

fun BaseActivity.isConnected(): Boolean {
    return (getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).run {
        getNetworkCapabilities(activeNetwork)?.run {
            hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
                    || hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
                    || hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
        } ?: false
    }
}