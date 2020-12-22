package com.example.myapplication.util

import android.content.Context
import android.preference.PreferenceManager

class SharedPreferenceHelper(context: Context) {

    private val PREF_API_KEY = "Api key"

//    private val prefs = PreferenceManager.getDefaultSharedPreferences(context.applicationContext)
    private val prefs = androidx.preference.PreferenceManager.getDefaultSharedPreferences(context.applicationContext)

//    Save API Key in local storage ?
    fun saveApiKey(key: String?) {
        prefs.edit().putString(PREF_API_KEY, key).apply()
    }

//    Get key from local storage ?
    fun getApiKey() = prefs.getString(PREF_API_KEY, null)
}