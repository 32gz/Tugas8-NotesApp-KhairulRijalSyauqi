package com.example.tugas8khairulrijalsyauqi.database

import android.content.Context
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.example.tugas8khairulrijalsyauqi.NotesDatabase

/**
 * Android-specific database wrapper
 */
class DatabaseWrapper(private val context: Context) {
    val database: NotesDatabase by lazy {
        val driver = AndroidSqliteDriver(
            schema = NotesDatabase.Schema,
            context = context,
            name = "notes.db"
        )
        NotesDatabase(driver)
    }
}
