package com.example.tugas8khairulrijalsyauqi.repository

import com.example.tugas8khairulrijalsyauqi.NotesDatabaseQueries
import com.example.tugas8khairulrijalsyauqi.model.Note
import com.example.tugas8khairulrijalsyauqi.model.SortOrder
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNull
import kotlin.test.assertTrue

/**
 * Unit tests for NotesRepository
 * Tests all repository operations including CRUD and search functionality
 */
class NotesRepositoryTest {

    private lateinit var repository: NotesRepository
    private lateinit var queries: NotesDatabaseQueries

    private val testNote = Note(
        id = 1L,
        title = "Test Note",
        content = "Test Content",
        isFavorite = false,
        createdAt = 1000L,
        updatedAt = 1000L
    )

    private val testNoteRow = object {
        val id = 1L
        val title = "Test Note"
        val content = "Test Content"
        val is_favorite = 0L
        val created_at = 1000L
        val updated_at = 1000L
    }

    @Before
    fun setup() {
        queries = mockk(relaxed = true)
        repository = NotesRepository(queries)
    }

    @Test
    fun `getAllNotes returns empty list when no notes exist`() = runBlocking {
        // Given
        every { queries.getAllNotesNewest().executeAsList() } returns emptyList()

        // When
        val notes = repository.getAllNotes(SortOrder.NEWEST_FIRST).first()

        // Then
        assertEquals(emptyList(), notes)
    }

    @Test
    fun `getAllNotes returns notes sorted by newest first`() = runBlocking {
        // Given
        val rows = listOf(
            createNoteRow(1L, "First", "Content 1", 0, 1000L, 1000L),
            createNoteRow(2L, "Second", "Content 2", 0, 2000L, 2000L)
        )
        every { queries.getAllNotesNewest().executeAsList() } returns rows

        // When
        val notes = repository.getAllNotes(SortOrder.NEWEST_FIRST).first()

        // Then
        assertEquals(2, notes.size)
        assertEquals("Second", notes[0].title)
        assertEquals("First", notes[1].title)
    }

    @Test
    fun `getAllNotes returns notes sorted by oldest first`() = runBlocking {
        // Given
        val rows = listOf(
            createNoteRow(1L, "Older", "Content", 0, 1000L, 1000L),
            createNoteRow(2L, "Newer", "Content", 0, 2000L, 2000L)
        )
        every { queries.getAllNotesOldest().executeAsList() } returns rows

        // When
        val notes = repository.getAllNotes(SortOrder.OLDEST_FIRST).first()

        // Then
        assertEquals(2, notes.size)
        assertEquals("Older", notes[0].title)
        assertEquals("Newer", notes[1].title)
    }

    @Test
    fun `getAllNotes returns notes sorted alphabetically`() = runBlocking {
        // Given
        val rows = listOf(
            createNoteRow(1L, "Zebra", "Content", 0, 1000L, 1000L),
            createNoteRow(2L, "Apple", "Content", 0, 2000L, 2000L),
            createNoteRow(3L, "Mango", "Content", 0, 1500L, 1500L)
        )
        every { queries.getAllNotesAlphabetical().executeAsList() } returns rows

        // When
        val notes = repository.getAllNotes(SortOrder.ALPHABETICAL).first()

        // Then
        assertEquals(3, notes.size)
        assertEquals("Apple", notes[0].title)
        assertEquals("Mango", notes[1].title)
        assertEquals("Zebra", notes[2].title)
    }

    @Test
    fun `getFavoriteNotes returns only favorite notes`() = runBlocking {
        // Given
        val rows = listOf(
            createNoteRow(1L, "Regular", "Content", 0, 1000L, 1000L),
            createNoteRow(2L, "Favorite", "Content", 1, 2000L, 2000L)
        )
        every { queries.getFavoriteNotes().executeAsList() } returns rows

        // When
        val favorites = repository.getFavoriteNotes().first()

        // Then
        assertEquals(2, favorites.size)
        assertTrue(favorites.all { it.isFavorite })
    }

    @Test
    fun `getFavoriteNotes returns empty list when no favorites`() = runBlocking {
        // Given
        every { queries.getFavoriteNotes().executeAsList() } returns emptyList()

        // When
        val favorites = repository.getFavoriteNotes().first()

        // Then
        assertEquals(emptyList(), favorites)
    }

    @Test
    fun `searchNotes finds notes by title`() = runBlocking {
        // Given
        val rows = listOf(
            createNoteRow(1L, "Kotlin Tutorial", "Learn Kotlin", 0, 1000L, 1000L),
            createNoteRow(2L, "Java Basics", "Learn Java", 0, 2000L, 2000L)
        )
        every { queries.searchNotesNewest("%Kotlin%").executeAsList() } returns rows

        // When
        val results = repository.searchNotes("Kotlin").first()

        // Then
        assertEquals(2, results.size)
        assertTrue(results.all { it.title.contains("Kotlin") || it.content.contains("Kotlin") })
    }

    @Test
    fun `searchNotes returns empty for non-matching query`() = runBlocking {
        // Given
        every { queries.searchNotesNewest("%Nonexistent%").executeAsList() } returns emptyList()

        // When
        val results = repository.searchNotes("Nonexistent").first()

        // Then
        assertEquals(emptyList(), results)
    }

    @Test
    fun `getNoteById returns correct note`() = runBlocking {
        // Given
        val row = createNoteRow(5L, "Specific Note", "Specific content", 0, 1000L, 1000L)
        every { queries.getNoteById(5L).executeAsOneOrNull() } returns row

        // When
        val note = repository.getNoteById(5L).first()

        // Then
        assertEquals(5L, note?.id)
        assertEquals("Specific Note", note?.title)
    }

    @Test
    fun `getNoteById returns null for non-existent id`() = runBlocking {
        // Given
        every { queries.getNoteById(999L).executeAsOneOrNull() } returns null

        // When
        val note = repository.getNoteById(999L).first()

        // Then
        assertNull(note)
    }

    @Test
    fun `insertNote calls queries insertNote`() = runBlocking {
        // Given
        coEvery { queries.insertNote(any(), any(), any(), any(), any()) } returns 1L

        // When
        val result = repository.insertNote("New Note", "New Content")

        // Then
        assertEquals(1L, result)
        coVerify { queries.insertNote("New Note", "New Content", 0, any(), any()) }
    }

    @Test
    fun `updateNote calls queries updateNote`() = runBlocking {
        // Given
        coEvery { queries.updateNote(any(), any(), any(), any()) } returns 1L

        // When
        repository.updateNote(1L, "Updated Title", "Updated Content")

        // Then
        coVerify { queries.updateNote("Updated Title", "Updated Content", any(), 1L) }
    }

    @Test
    fun `toggleFavorite calls queries updateFavorite with true`() = runBlocking {
        // Given
        coEvery { queries.updateFavorite(any(), any(), any()) } returns 1L

        // When
        repository.toggleFavorite(1L, true)

        // Then
        coVerify { queries.updateFavorite(1L, any(), 1L) }
    }

    @Test
    fun `toggleFavorite calls queries updateFavorite with false`() = runBlocking {
        // Given
        coEvery { queries.updateFavorite(any(), any(), any()) } returns 1L

        // When
        repository.toggleFavorite(1L, false)

        // Then
        coVerify { queries.updateFavorite(0L, any(), 1L) }
    }

    @Test
    fun `deleteNote calls queries deleteNote`() = runBlocking {
        // Given
        coEvery { queries.deleteNote(any()) } returns 1L

        // When
        repository.deleteNote(1L)

        // Then
        coVerify { queries.deleteNote(1L) }
    }

    // Helper function to create mock Note rows
    private fun createNoteRow(
        id: Long,
        title: String,
        content: String,
        isFavorite: Long,
        createdAt: Long,
        updatedAt: Long
    ): com.example.tugas8khairulrijalsyauqi.Note {
        return mockk {
            every { this@mockk.id } returns id
            every { this@mockk.title } returns title
            every { this@mockk.content } returns content
            every { this@mockk.is_favorite } returns isFavorite
            every { this@mockk.created_at } returns createdAt
            every { this@mockk.updated_at } returns updatedAt
        }
    }
}
