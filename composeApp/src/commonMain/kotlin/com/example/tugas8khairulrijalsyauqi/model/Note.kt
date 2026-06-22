package com.example.tugas8khairulrijalsyauqi.model

expect fun currentTimeMillis(): Long

data class Note(
    val id: Long = 0,
    val title: String = "",
    val content: String = "",
    val isFavorite: Boolean = false,
    val createdAt: Long = currentTimeMillis(),
    val updatedAt: Long = currentTimeMillis()
)

enum class SortOrder {
    NEWEST_FIRST,
    OLDEST_FIRST,
    ALPHABETICAL,
    LAST_MODIFIED
}

data class UserSettings(
    val isDarkTheme: Boolean = false,
    val sortOrder: SortOrder = SortOrder.NEWEST_FIRST
)