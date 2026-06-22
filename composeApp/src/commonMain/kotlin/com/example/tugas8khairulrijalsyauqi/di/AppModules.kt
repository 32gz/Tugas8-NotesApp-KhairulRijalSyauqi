package com.example.tugas8khairulrijalsyauqi.di

/**
 * Common Koin modules for dependency injection
 */
object AppModules {
    /**
     * Get all common modules (data + viewModel)
     */
    fun getCommonModules() = listOf(dataModule, viewModelModule)
}
