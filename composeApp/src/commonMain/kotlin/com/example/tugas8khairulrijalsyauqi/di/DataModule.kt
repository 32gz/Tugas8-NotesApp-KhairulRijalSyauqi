package com.example.tugas8khairulrijalsyauqi.di

import com.example.tugas8khairulrijalsyauqi.NotesDatabaseQueries
import com.example.tugas8khairulrijalsyauqi.repository.NotesRepository
import org.koin.dsl.module

/**
 * Data module for Koin DI
 * Contains repository dependencies
 */
val dataModule = module {
    // Repository - depends on NotesDatabaseQueries
    single<NotesRepository> {
        NotesRepository(get<NotesDatabaseQueries>())
    }
}
