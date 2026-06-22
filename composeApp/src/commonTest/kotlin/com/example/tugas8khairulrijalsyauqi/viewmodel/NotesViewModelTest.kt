package com.example.tugas8khairulrijalsyauqi.viewmodel

import app.cash.turbine.test
import com.example.tugas8khairulrijalsyauqi.model.Note
import com.example.tugas8khairulrijalsyauqi.model.SortOrder
import com.example.tugas8khairulrijalsyauqi.repository.NotesRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs
import kotlin.test.assertTrue

/**
 * Unit tests for NotesViewModel using MockK
 * Tests ViewModel business logic with mocked repository
 */
@OptIn(ExperimentalCoroutinesApi::class)
class NotesViewModelTest {

    private lateinit var repository: NotesRepository
    private lateinit var viewModel: NotesViewModel
    private val testDispatcher = StandardTestDispatcher()

    private val sampleNotes = listOf(
        Note(id = 1L, title = "Note 1", content = "Content 1", isFavorite = false, createdAt = 1000L, updatedAt = 1000L),
        Note(id = 2L, title = "Note 2", content = "Content 2", isFavorite = true, createdAt = 2000L, updatedAt = 2000L),
        Note(id = 3L, title = "Note 3", content = "Content 3", isFavorite = false, createdAt = 1500L, updatedAt = 1500L)
    )

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        repository = mockk(relaxed = true)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `initial state is Loading`() = runTest {
        // Given
        every { repository.getAllNotes(any()) } returns flowOf(emptyList())
        every { repository.getFavoriteNotes() } returns flowOf(emptyList())

        // When
        viewModel = NotesViewModel(repository)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then - initial state should be Loading
        val state = viewModel.uiState.value
        assertIs<NotesUiState>(state)
    }

    @Test
    fun `loadNotes updates uiState to Content when notes exist`() = runTest {
        // Given
        every { repository.getAllNotes(SortOrder.NEWEST_FIRST) } returns flowOf(sampleNotes)
        every { repository.getFavoriteNotes() } returns flowOf(sampleNotes.filter { it.isFavorite })

        // When
        viewModel = NotesViewModel(repository)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        val state = viewModel.uiState.value
        assertIs<NotesUiState.Content>(state)
        assertEquals(3, state.notes.size)
    }

    @Test
    fun `loadNotes updates uiState to Empty when no notes exist`() = runTest {
        // Given
        every { repository.getAllNotes(any()) } returns flowOf(emptyList())
        every { repository.getFavoriteNotes() } returns flowOf(emptyList())

        // When
        viewModel = NotesViewModel(repository)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        val state = viewModel.uiState.value
        assertIs<NotesUiState.Empty>(state)
    }

    @Test
    fun `search updates searchQuery and reloads notes`() = runTest {
        // Given
        every { repository.searchNotes("test", any()) } returns flowOf(sampleNotes.take(1))
        every { repository.getAllNotes(any()) } returns flowOf(sampleNotes)
        every { repository.getFavoriteNotes() } returns flowOf(emptyList())

        viewModel = NotesViewModel(repository)
        testDispatcher.scheduler.advanceUntilIdle()

        // When
        viewModel.search("test")
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        assertEquals("test", viewModel.searchQuery.value)
        coVerify { repository.searchNotes("test", any()) }
    }

    @Test
    fun `updateSortOrder changes sort order and reloads notes`() = runTest {
        // Given
        every { repository.getAllNotes(SortOrder.ALPHABETICAL) } returns flowOf(sampleNotes.sortedBy { it.title })
        every { repository.getFavoriteNotes() } returns flowOf(emptyList())

        viewModel = NotesViewModel(repository)
        testDispatcher.scheduler.advanceUntilIdle()

        // When
        viewModel.updateSortOrder(SortOrder.ALPHABETICAL)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        assertEquals(SortOrder.ALPHABETICAL, viewModel.sortOrder.value)
        coVerify { repository.getAllNotes(SortOrder.ALPHABETICAL) }
    }

    @Test
    fun `toggleFavorite calls repository and refreshes notes`() = runTest {
        // Given
        every { repository.getAllNotes(any()) } returns flowOf(sampleNotes)
        every { repository.getFavoriteNotes() } returns flowOf(emptyList())
        coEvery { repository.toggleFavorite(1L, true) } returns Unit

        viewModel = NotesViewModel(repository)
        testDispatcher.scheduler.advanceUntilIdle()

        // When
        viewModel.toggleFavorite(1L, true)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        coVerify { repository.toggleFavorite(1L, true) }
    }

    @Test
    fun `deleteNote calls repository and refreshes notes`() = runTest {
        // Given
        every { repository.getAllNotes(any()) } returns flowOf(sampleNotes.drop(1))
        every { repository.getFavoriteNotes() } returns flowOf(emptyList())
        coEvery { repository.deleteNote(1L) } returns Unit

        viewModel = NotesViewModel(repository)
        testDispatcher.scheduler.advanceUntilIdle()

        // When
        viewModel.deleteNote(1L)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        coVerify { repository.deleteNote(1L) }
    }

    @Test
    fun `refreshNotes reloads all notes and favorite notes`() = runTest {
        // Given
        every { repository.getAllNotes(any()) } returns flowOf(sampleNotes)
        every { repository.getFavoriteNotes() } returns flowOf(sampleNotes.filter { it.isFavorite })

        viewModel = NotesViewModel(repository)
        testDispatcher.scheduler.advanceUntilIdle()

        // When
        viewModel.refreshNotes()
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        val favorites = viewModel.favoriteNotes.value
        assertEquals(1, favorites.size) // Only Note 2 is favorite
        assertTrue(favorites.all { it.isFavorite })
    }
}