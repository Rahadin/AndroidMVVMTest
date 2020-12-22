package com.example.myapplication

import android.app.Application
import com.example.myapplication.di.PrefsModule
import com.example.myapplication.util.SharedPreferenceHelper

class PrefsModuleTest(val mockPrefs: SharedPreferenceHelper): PrefsModule() {

    override fun provideSharedPreferences(app: Application): SharedPreferenceHelper {
        return mockPrefs
    }
}