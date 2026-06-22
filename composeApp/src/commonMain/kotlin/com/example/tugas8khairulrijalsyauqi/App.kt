package com.example.tugas8khairulrijalsyauqi

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.example.tugas8khairulrijalsyauqi.datastore.SettingsDataStore
import com.example.tugas8khairulrijalsyauqi.model.UserSettings
import com.example.tugas8khairulrijalsyauqi.navigation.NotesNavHost
import com.example.tugas8khairulrijalsyauqi.repository.NotesRepository
import com.example.tugas8khairulrijalsyauqi.ui.theme.NotesAppTheme
import com.example.tugas8khairulrijalsyauqi.viewmodel.NotesUiState
import kotlinx.coroutines.launch
import org.koin.compose.koinInject

@Composable
fun App(
    repository: NotesRepository = koinInject(),
    settingsDataStore: SettingsDataStore = koinInject(),
    deviceInfo: DeviceInfo = koinInject(),
    networkMonitor: NetworkMonitor = koinInject()
) {
    val settings by settingsDataStore.settingsFlow.collectAsState(initial = UserSettings())
    val notesViewModel: com.example.tugas8khairulrijalsyauqi.viewmodel.NotesViewModel = koinInject()
    val notesUiState by notesViewModel.uiState.collectAsState()
    val searchQuery by notesViewModel.searchQuery.collectAsState()
    val sortOrder by notesViewModel.sortOrder.collectAsState()

    val favoriteNotes by notesViewModel.favoriteNotes.collectAsState()
    val scope = rememberCoroutineScope()

    val isConnected by networkMonitor.isConnected.collectAsState(initial = true)

    NotesAppTheme(darkTheme = settings.isDarkTheme) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            NotesNavHost(
                notesUiState = notesUiState,
                favoriteNotes = favoriteNotes,
                searchQuery = searchQuery,
                sortOrder = sortOrder,
                settings = settings,
                repository = repository,
                deviceInfo = deviceInfo,
                isConnected = isConnected,
                onSearch = { notesViewModel.search(it) },
                onToggleFavorite = { id, isFavorite ->
                    notesViewModel.toggleFavorite(id, isFavorite)
                },
                onDeleteNote = { notesViewModel.deleteNote(it) },
                onToggleDarkTheme = {
                    scope.launch {
                        settingsDataStore.updateDarkTheme(!settings.isDarkTheme)
                    }
                },
                onSortOrderChange = { notesViewModel.updateSortOrder(it) },
                onRefreshNotes = { notesViewModel.refreshNotes() }
            )
        }
    }
}
