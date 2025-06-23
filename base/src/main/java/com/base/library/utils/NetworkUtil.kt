package com.base.library.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

object NetworkUtil {

    // 获取网络类型
    fun getNetworkType(): NetworkType {
        try {
            val connectivityManager = Global.cxt.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val network = connectivityManager.activeNetwork ?: return NetworkType.None
            val networkCapabilities = connectivityManager.getNetworkCapabilities(network) ?: return NetworkType.None

            return when {
                networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> NetworkType.Wifi
                networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> NetworkType.MobileData
                networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> NetworkType.Ethernet
                else -> NetworkType.Unknown
            }
        } catch (e: Exception) {
            return NetworkType.Unknown
        }
    }
}

enum class NetworkType {
    Wifi,
    MobileData,
    Ethernet,
    Unknown,
    None
}