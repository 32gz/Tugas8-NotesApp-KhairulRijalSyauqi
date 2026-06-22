package com.example.tugas8khairulrijalsyauqi.viewmodel

import app.cash.turbine.test
import com.example.tugas8khairulrijalsyauqi.model.Note
import com.example.tugas8khairulrijalsyauqi.model.SortOrder
import com.example.tugas8khairulrijalsyauqi.repository.NotesRepository
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
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

/**
 * Flow tests for NotesViewModel using Turbine
 * Tests reactive streams and state emissions
 */
@OptIn(ExperimentalCoroutinesApi::class)
class NotesViewModelFlowTest {

    private lateinit var repository: NotesRepository
    private lateinit var viewModel: NotesViewModel
    private val testDispatcher = StandardTestDispatcher()

    private val sampleNotes = listOf(
        Note(id = 1L, title = "First Note", content = "Content 1", isFavorite = false, createdAt = 1000L, updatedAt = 1000L),
        Note(id = 2L, title = "Second Note", content = "Content 2", isFavorite = true, createdAt = 2000L, updatedAt = 2000L)
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
    fun `uiState flow emits Loading then Content`() = runTest {
        // Given
        every { repository.getAllNotes(any()) } returns flowOf(sampleNotes)
        every { repository.getFavoriteNotes() } returns flowOf(sampleNotes.filter { it.isFavorite })

        // When
        viewModel = NotesViewModel(repository)

        // Then - check state flow emissions
        viewModel.uiState.test {
            // First emission should be Loading
            val first = awaitItem()
            assertIs<NotesUiState.Loading>(first)

            // Second emission should be Content with notes
            val second = awaitItem()
            assertIs<NotesUiState.Content>(second)
            assertEquals(2, second.notes.size)

            // No more emissions in this test
            awaitComplete()
        }
    }

    @Test
    fun `uiState flow emits Empty when no notes`() = runTest {
        // Given
        every { repository.getAllNotes(any()) } returns flowOf(emptyList())
        every { repository.getFavoriteNotes() } returns flowOf(emptyList())

        // When
        viewModel = NotesViewModel(repository)

        // Then
        viewModel.uiState.test {
            val first = awaitItem()
            assertIs<NotesUiState.Loading>(first)

            val second = awaitItem()
            assertIs<NotesUiState.Empty>(second)

            awaitComplete()
        }
    }

    @Test
    fun `searchQuery flow updates when search is called`() = runTest {
        // Given
        every { repository.getAllNotes(any()) } returns flowOf(emptyList())
        every { repository.getFavoriteNotes() } returns flowOf(emptyList())

        viewModel = NotesViewModel(repository)
        testDispatcher.scheduler.advanceUntilIdle()

        // When - search with a query
        viewModel.search("test query")

        // Then - check searchQuery flow
        viewModel.searchQuery.test {
            assertEquals("test query", awaitItem())
            awaitComplete()
        }
    }

    @Test
    fun `sortOrder flow updates when sort order is changed`() = runTest {
        // Given
        every { repository.getAllNotes(any()) } returns flowOf(emptyList())
        every { repository.getFavoriteNotes() } returns flowOf(emptyList())

        viewModel = NotesViewModel(repository)
        testDispatcher.scheduler.advanceUntilIdle()

        // When - change sort order
        viewModel.updateSortOrder(SortOrder.ALPHABETICAL)

        // Then - check sortOrder flow
        viewModel.sortOrder.test {
            assertEquals(SortOrder.ALPHABETICAL, awaitItem())
            awaitComplete()
        }
    }

    @Test
    fun `favoriteNotes flow updates with repository data`() = runTest {
        // Given
        every { repository.getAllNotes(any()) } returns flowOf(emptyList())
        every { repository.getFavoriteNotes() } returns flowOf(sampleNotes.filter { it.isFavorite })

        // When
        viewModel = NotesViewModel(repository)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then - check favoriteNotes flow
        viewModel.favoriteNotes.test {
            val favorites = awaitItem()
            assertEquals(1, favorites.size)
            assertEquals("Second Note", favorites[0].title)
            awaitComplete()
        }
    }

    @Test
    fun `loadNotes with search query returns matching notes`() = runTest {
        // Given
        val searchResults = listOf(sampleNotes[0])
        every { repository.searchNotes("First", SortOrder.NEWEST_FIRST) } returns flowOf(searchResults)
        every { repository.getFavoriteNotes() } returns flowOf(emptyList())

        viewModel = NotesViewModel(repository)
        testDispatcher.scheduler.advanceUntilIdle()

        // When - search
        viewModel.search("First")
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        viewModel.uiState.test {
            // Skip loading state
            skipItems(1)

            val state = awaitItem()
            assertIs<NotesUiState.Content>(state)
            assertEquals(1, state.notes.size)
            assertEquals("First Note", state.notes[0].title)
            assertEquals("First", state.searchQuery)
            awaitComplete()
        }
    }

    @Test
    fun `deleteNote updates favoriteNotes flow`() = runTest {
        // Given
        val favoriteNote = sampleNotes[1]
        every { repository.getAllNotes(any()) } returns flowOf(listOf(sampleNotes[0]))
        every { repository.getFavoriteNotes() } returns flowOf(listOf(favoriteNote))
        coEvery { repository.deleteNote(2L) } returns Unit

        viewModel = NotesViewModel(repository)
        testDispatcher.scheduler.advanceUntilIdle()

        // When
        viewModel.deleteNote(2L)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        viewModel.favoriteNotes.test {
            val favorites = awaitItem()
            assertEquals(1, favorites.size)
            assertEquals(2L, favorites[0].id)
            awaitComplete()
        }
    }
}