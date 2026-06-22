package com.example.tugas8khairulrijalsyauqi.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.tugas8khairulrijalsyauqi.DeviceInfo
import com.example.tugas8khairulrijalsyauqi.datastore.SettingsDataStore
import com.example.tugas8khairulrijalsyauqi.model.SortOrder
import com.example.tugas8khairulrijalsyauqi.model.UserSettings
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val settingsDataStore: SettingsDataStore,
    private val deviceInfo: DeviceInfo
) : ViewModel() {

    val settings: StateFlow<UserSettings> = settingsDataStore.settingsFlow
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = UserSettings()
        )

    val deviceInfoData: DeviceInfo = deviceInfo

    fun toggleDarkTheme() {
        viewModelScope.launch {
            settingsDataStore.updateDarkTheme(!settings.value.isDarkTheme)
        }
    }

    fun updateSortOrder(sortOrder: SortOrder) {
        viewModelScope.launch {
            settingsDataStore.updateSortOrder(sortOrder)
        }
    }

    class Factory(
        private val settingsDataStore: SettingsDataStore,
        private val deviceInfo: DeviceInfo
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return SettingsViewModel(settingsDataStore, deviceInfo) as T
        }
    }
}
