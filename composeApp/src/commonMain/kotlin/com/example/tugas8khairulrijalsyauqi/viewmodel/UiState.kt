package com.example.tugas8khairulrijalsyauqi.viewmodel

import com.example.tugas8khairulrijalsyauqi.model.Note
import com.example.tugas8khairulrijalsyauqi.model.SortOrder

sealed interface NotesUiState {
    data object Loading : NotesUiState
    data object Empty : NotesUiState
    data class Content(
        val notes: List<Note>,
        val searchQuery: String = "",
        val sortOrder: SortOrder = SortOrder.NEWEST_FIRST
    ) : NotesUiState
}

data class NoteDetailUiState(
    val note: Note? = null,
    val isLoading: Boolean = true
)

data class NoteEditorUiState(
    val id: Long? = null,
    val title: String = "",
    val content: String = "",
    val isSaving: Boolean = false,
    val titleError: Boolean = false,
    val contentError: Boolean = false
)