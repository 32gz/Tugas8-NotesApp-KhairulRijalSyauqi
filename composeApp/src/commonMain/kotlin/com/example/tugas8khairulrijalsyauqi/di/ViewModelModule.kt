package com.example.tugas8khairulrijalsyauqi.di

import com.example.tugas8khairulrijalsyauqi.DeviceInfo
import com.example.tugas8khairulrijalsyauqi.repository.NotesRepository
import com.example.tugas8khairulrijalsyauqi.viewmodel.NoteDetailViewModel
import com.example.tugas8khairulrijalsyauqi.viewmodel.NoteEditorViewModel
import com.example.tugas8khairulrijalsyauqi.viewmodel.NotesViewModel
import com.example.tugas8khairulrijalsyauqi.viewmodel.ProfileViewModel
import com.example.tugas8khairulrijalsyauqi.viewmodel.SettingsViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

/**
 * ViewModel module for Koin DI
 * Contains ViewModel factories
 */
val viewModelModule = module {
    // NotesViewModel
    factoryOf(::NotesViewModel)

    // NoteDetailViewModel with parameters
    factory { params ->
        NoteDetailViewModel(
            repository = params.get(),
            noteId = params.get()
        )
    }

    // NoteEditorViewModel with parameters
    factory { params ->
        NoteEditorViewModel(
            repository = params.get(),
            noteId = params.getOrNull()
        )
    }

    // SettingsViewModel with parameters
    factory { params ->
        SettingsViewModel(
            settingsDataStore = params.get(),
            deviceInfo = params.get()
        )
    }

    // ProfileViewModel
    factoryOf(::ProfileViewModel)
}
