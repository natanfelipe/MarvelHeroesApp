package com.natanbrito.marvelheroesapp.utils

import java.util.Calendar
import java.util.TimeZone

class Utils {
    fun getTimeStamp(): String = (Calendar.getInstance(TimeZone.getTimeZone("UTC")).timeInMillis / 1000L).toString()
}