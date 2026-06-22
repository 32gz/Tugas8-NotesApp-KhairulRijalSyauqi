package com.example.tugas8khairulrijalsyauqi

import kotlin.test.Test
import kotlin.test.assertEquals

/**
 * Placeholder test class for common module
 * Actual tests are in sub-packages:
 * - repository.NotesRepositoryTest
 * - viewmodel.NotesViewModelTest
 * - viewmodel.NotesViewModelFlowTest
 * - screens.NotesListScreenTest (androidTest)
 */
class ComposeAppCommonTest {

    @Test
    fun `basic test placeholder`() {
        assertEquals(3, 1 + 2)
    }

    @Test
    fun `kotlin test framework works`() {
        val list = listOf(1, 2, 3)
        assertEquals(3, list.size)
    }
}