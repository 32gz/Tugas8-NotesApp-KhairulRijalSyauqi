package com.example.tugas8khairulrijalsyauqi.database

import android.content.Context
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.example.tugas8khairulrijalsyauqi.NotesDatabase

fun createDatabase(context: Context): NotesDatabase {
    val driver = AndroidSqliteDriver(
        schema = NotesDatabase.Schema,
        context = context,
        name = "notes.db"
    )
    return NotesDatabase(driver)
}