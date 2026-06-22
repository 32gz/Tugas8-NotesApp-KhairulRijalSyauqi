package com.example.tugas8khairulrijalsyauqi.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tugas8khairulrijalsyauqi.model.Note
import com.example.tugas8khairulrijalsyauqi.model.SortOrder
import com.example.tugas8khairulrijalsyauqi.ui.components.EmptyContent
import com.example.tugas8khairulrijalsyauqi.ui.components.LoadingContent
import com.example.tugas8khairulrijalsyauqi.ui.components.NetworkStatusIndicator
import com.example.tugas8khairulrijalsyauqi.ui.components.NoteCard
import com.example.tugas8khairulrijalsyauqi.ui.components.SearchBar
import com.example.tugas8khairulrijalsyauqi.viewmodel.NotesUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotesListScreen(
    uiState: NotesUiState,
    searchQuery: String,
    sortOrder: SortOrder,
    isConnected: Boolean,
    onSearch: (String) -> Unit,
    onSortOrderChange: (SortOrder) -> Unit,
    onNoteClick: (Long) -> Unit,
    onFavoriteToggle: (Long, Boolean) -> Unit,
    onDelete: (Long) -> Unit,
    onSettingsClick: () -> Unit,
    onAddNote: () -> Unit,
    modifier: Modifier = Modifier
) {
    var showSortMenu by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Catatan",
                        fontWeight = FontWeight.Bold
                    )
                },
                actions = {
                    // Sort/Filter Button
                    TextButton(onClick = { showSortMenu = true }) {
                        Icon(
                            imageVector = Icons.Default.FilterList,
                            contentDescription = "Filter"
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(getSortOrderLabel(sortOrder))
                    }

                    DropdownMenu(
                        expanded = showSortMenu,
                        onDismissRequest = { showSortMenu = false }
                    ) {
                        SortOrder.entries.forEach { order ->
                            DropdownMenuItem(
                                text = { Text(getSortOrderLabel(order)) },
                                onClick = {
                                    onSortOrderChange(order)
                                    showSortMenu = false
                                },
                                leadingIcon = if (order == sortOrder) {
                                    { Text("✓") }
                                } else null
                            )
                        }
                    }

                    NetworkStatusIndicator(
                        isConnected = isConnected,
                        modifier = Modifier.padding(end = 8.dp)
                    )
                    IconButton(onClick = onSettingsClick) {
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = "Settings"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        },
        modifier = modifier
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            SearchBar(
                query = searchQuery,
                onQueryChange = onSearch,
                onSearch = { }
            )

            when (uiState) {
                is NotesUiState.Loading -> {
                    LoadingContent()
                }
                is NotesUiState.Empty -> {
                    EmptyContent(
                        title = if (searchQuery.isNotEmpty()) "Tidak ada hasil" else "Belum ada catatan",
                        subtitle = if (searchQuery.isNotEmpty())
                            "Coba kata kunci lain"
                        else
                            "Tekan + untuk menambah catatan",
                        emoji = if (searchQuery.isNotEmpty()) "🔍" else "📝"
                    )
                }
                is NotesUiState.Content -> {
                    NotesListContent(
                        notes = uiState.notes,
                        onNoteClick = onNoteClick,
                        onFavoriteToggle = onFavoriteToggle,
                        onDelete = onDelete,
                        onAddNote = onAddNote
                    )
                }
            }
        }
    }
}

@Composable
private fun getSortOrderLabel(sortOrder: SortOrder): String {
    return when (sortOrder) {
        SortOrder.NEWEST_FIRST -> "Terbaru"
        SortOrder.OLDEST_FIRST -> "Terlama"
        SortOrder.ALPHABETICAL -> "A-Z"
        SortOrder.LAST_MODIFIED -> "Diubah Terakhir"
    }
}

@Composable
private fun NotesListContent(
    notes: List<Note>,
    onNoteClick: (Long) -> Unit,
    onFavoriteToggle: (Long, Boolean) -> Unit,
    onDelete: (Long) -> Unit,
    onAddNote: () -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        items(notes, key = { it.id }) { note ->
            NoteCard(
                note = note,
                onClick = { onNoteClick(note.id) },
                onFavoriteToggle = { onFavoriteToggle(note.id, !note.isFavorite) },
                onDelete = { onDelete(note.id) }
            )
        }
    }
}