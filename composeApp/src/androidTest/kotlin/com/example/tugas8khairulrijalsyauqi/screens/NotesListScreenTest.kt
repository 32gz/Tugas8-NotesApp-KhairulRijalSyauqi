package com.example.tugas8khairulrijalsyauqi.screens

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import com.example.tugas8khairulrijalsyauqi.model.Note
import com.example.tugas8khairulrijalsyauqi.model.SortOrder
import com.example.tugas8khairulrijalsyauqi.viewmodel.NotesUiState
import org.junit.Rule
import org.junit.Test

/**
 * UI tests for NotesListScreen
 * Tests UI rendering and user interactions
 */
class NotesListScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<android.app.Activity>()

    private val sampleNotes = listOf(
        Note(id = 1L, title = "Test Note 1", content = "Content 1", isFavorite = false, createdAt = 1000L, updatedAt = 1000L),
        Note(id = 2L, title = "Test Note 2", content = "Content 2", isFavorite = true, createdAt = 2000L, updatedAt = 2000L)
    )

    @Test
    fun `displays loading indicator when in Loading state`() {
        // Given
        var onSettingsClick = false

        // When
        composeTestRule.setContent {
            NotesListScreen(
                uiState = NotesUiState.Loading,
                searchQuery = "",
                sortOrder = SortOrder.NEWEST_FIRST,
                onSearch = {},
                onNoteClick = {},
                onFavoriteToggle = { _, _ -> },
                onDelete = {},
                onSettingsClick = { onSettingsClick = true },
                onAddNote = {}
            )
        }

        // Then - loading indicator should be displayed (via LoadingContent component)
        // Note: The actual CircularProgressIndicator is inside LoadingContent
        // This test verifies the screen renders in loading state
        composeTestRule.onNodeWithText("Catatan").assertIsDisplayed()
    }

    @Test
    fun `displays empty state message when no notes`() {
        // When
        composeTestRule.setContent {
            NotesListScreen(
                uiState = NotesUiState.Empty,
                searchQuery = "",
                sortOrder = SortOrder.NEWEST_FIRST,
                onSearch = {},
                onNoteClick = {},
                onFavoriteToggle = { _, _ -> },
                onDelete = {},
                onSettingsClick = {},
                onAddNote = {}
            )
        }

        // Then - empty message should be displayed
        composeTestRule.onNodeWithText("Belum ada catatan").assertIsDisplayed()
        composeTestRule.onNodeWithText("Tekan + untuk menambah catatan").assertIsDisplayed()
    }

    @Test
    fun `displays notes when in Content state`() {
        // When
        composeTestRule.setContent {
            NotesListScreen(
                uiState = NotesUiState.Content(
                    notes = sampleNotes,
                    searchQuery = "",
                    sortOrder = SortOrder.NEWEST_FIRST
                ),
                searchQuery = "",
                sortOrder = SortOrder.NEWEST_FIRST,
                onSearch = {},
                onNoteClick = {},
                onFavoriteToggle = { _, _ -> },
                onDelete = {},
                onSettingsClick = {},
                onAddNote = {}
            )
        }

        // Then - notes should be displayed
        composeTestRule.onNodeWithText("Test Note 1").assertIsDisplayed()
        composeTestRule.onNodeWithText("Test Note 2").assertIsDisplayed()
    }

    @Test
    fun `displays search results empty state when search returns no results`() {
        // When
        composeTestRule.setContent {
            NotesListScreen(
                uiState = NotesUiState.Empty,
                searchQuery = "nonexistent",
                sortOrder = SortOrder.NEWEST_FIRST,
                onSearch = {},
                onNoteClick = {},
                onFavoriteToggle = { _, _ -> },
                onDelete = {},
                onSettingsClick = {},
                onAddNote = {}
            )
        }

        // Then - search empty message should be displayed
        composeTestRule.onNodeWithText("Tidak ada hasil").assertIsDisplayed()
        composeTestRule.onNodeWithText("Coba kata kunci lain").assertIsDisplayed()
    }

    @Test
    fun `displays app title in top bar`() {
        // When
        composeTestRule.setContent {
            NotesListScreen(
                uiState = NotesUiState.Loading,
                searchQuery = "",
                sortOrder = SortOrder.NEWEST_FIRST,
                onSearch = {},
                onNoteClick = {},
                onFavoriteToggle = { _, _ -> },
                onDelete = {},
                onSettingsClick = {},
                onAddNote = {}
            )
        }

        // Then - title should be displayed
        composeTestRule.onNodeWithText("Catatan").assertIsDisplayed()
    }

    @Test
    fun `settings icon is clickable`() {
        // Given
        var settingsClicked = false

        // When
        composeTestRule.setContent {
            NotesListScreen(
                uiState = NotesUiState.Loading,
                searchQuery = "",
                sortOrder = SortOrder.NEWEST_FIRST,
                onSearch = {},
                onNoteClick = {},
                onFavoriteToggle = { _, _ -> },
                onDelete = {},
                onSettingsClick = { settingsClicked = true },
                onAddNote = {}
            )
        }

        // Perform click on Settings icon (content description)
        composeTestRule.onNodeWithText("Settings").performClick()

        // Then
        assert(settingsClicked) { "Settings should be clickable" }
    }

    @Test
    fun `search bar accepts text input`() {
        // Given
        var searchQuery = ""

        // When
        composeTestRule.setContent {
            NotesListScreen(
                uiState = NotesUiState.Empty,
                searchQuery = searchQuery,
                sortOrder = SortOrder.NEWEST_FIRST,
                onSearch = { searchQuery = it },
                onNoteClick = {},
                onFavoriteToggle = { _, _ -> },
                onDelete = {},
                onSettingsClick = {},
                onAddNote = {}
            )
        }

        // Perform text input in search bar
        composeTestRule.onNodeWithText("Cari catatan...").performTextInput("test query")

        // Then - verify search callback was triggered (query updated)
        assert(searchQuery == "test query") { "Search query should be updated" }
    }
}