package com.natanbrito.marvelheroesapp.utils

import android.content.Context
import com.natanbrito.marvelheroesapp.BuildConfig
import com.natanbrito.marvelheroesapp.extensions.md5
import java.util.Calendar
import java.util.TimeZone
import android.net.ConnectivityManager



class Utils {
    fun getTimeStamp(): String = (Calendar.getInstance(TimeZone.getTimeZone("UTC")).timeInMillis / 1000L).toString()
    fun getHash(ts: String): String = "$ts${BuildConfig.PRIVATE_API_KEY}${BuildConfig.PUBLIC_API_KEY}".md5()

    fun hasInternetConnection(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = cm.activeNetworkInfo

        return activeNetwork != null && activeNetwork.isConnectedOrConnecting
    }
}