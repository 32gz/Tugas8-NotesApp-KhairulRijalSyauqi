package com.example.tugas8khairulrijalsyauqi.di

import android.content.Context
import com.example.tugas8khairulrijalsyauqi.DeviceInfo
import com.example.tugas8khairulrijalsyauqi.NetworkMonitor
import com.example.tugas8khairulrijalsyauqi.AndroidNetworkMonitor
import com.example.tugas8khairulrijalsyauqi.AndroidDeviceInfo
import com.example.tugas8khairulrijalsyauqi.NotesDatabase
import com.example.tugas8khairulrijalsyauqi.NotesDatabaseQueries
import com.example.tugas8khairulrijalsyauqi.database.DatabaseWrapper
import com.example.tugas8khairulrijalsyauqi.datastore.SettingsDataStore
import com.example.tugas8khairulrijalsyauqi.dataStore
import org.koin.dsl.module

/**
 * Android-specific Koin module
 * Contains Android-specific dependencies
 */
val androidModule = module {
    // Database wrapper
    single { DatabaseWrapper(get<Context>()) }

    // NotesDatabase instance
    single<NotesDatabase> { get<DatabaseWrapper>().database }

    // Database queries
    single<NotesDatabaseQueries> { get<NotesDatabase>().notesDatabaseQueries }

    // Settings DataStore
    single { SettingsDataStore(get<Context>().dataStore) }

    // DeviceInfo - singleton for the app
    single<DeviceInfo> { AndroidDeviceInfo() }

    // NetworkMonitor - scoped to the application
    single<NetworkMonitor> { AndroidNetworkMonitor(get<Context>()) }
}
