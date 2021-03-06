package com.example.myapplication.di

import android.app.Activity
import android.app.Application
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.util.SharedPreferenceHelper
import dagger.Module
import dagger.Provides
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
open class PrefsModule {

    @Provides
    @Singleton
    @TypeOfContext(CONTEXT_APP)
    open fun provideSharedPreferences(app: Application): SharedPreferenceHelper {
        return SharedPreferenceHelper(app)
    }

    @Provides
    @Singleton
    @TypeOfContext(CONTEXT_ACTIVITY)
    fun provideActivitySharedPreferences(activity: AppCompatActivity): SharedPreferenceHelper {
        return SharedPreferenceHelper(activity)
    }
}

const val CONTEXT_APP = "Application context"
const val CONTEXT_ACTIVITY = "Activity context"

// Qualifies return type to determine which one is needed in which context
@Qualifier
annotation class TypeOfContext(val type: String)