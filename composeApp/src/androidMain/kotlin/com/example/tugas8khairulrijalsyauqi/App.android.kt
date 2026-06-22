package com.example.tugas8khairulrijalsyauqi

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.tugas8khairulrijalsyauqi.di.AppModules
import com.example.tugas8khairulrijalsyauqi.di.androidModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

object AppInitializer {
    private var context: Context? = null

    fun init(context: Context) {
        this.context = context.applicationContext
        initializeKoin(context)
    }

    fun getContext(): Context {
        return context ?: throw IllegalStateException("App not initialized")
    }

    private fun initializeKoin(context: Context) {
        startKoin {
            androidContext(context)
            modules(AppModules.getCommonModules() + androidModule)
        }
    }
}
