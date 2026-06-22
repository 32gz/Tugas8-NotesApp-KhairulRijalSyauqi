package com.example.tugas8khairulrijalsyauqi

import com.example.tugas8khairulrijalsyauqi.di.AppModules
import com.example.tugas8khairulrijalsyauqi.di.iosModule
import org.koin.core.context.startKoin

object AppInitializer {
    fun init(context: Any) {
        initializeKoin()
    }

    fun getContext(): Any {
        error("Context not available on iOS")
    }

    private fun initializeKoin() {
        startKoin {
            modules(AppModules.getCommonModules() + iosModule)
        }
    }
}
