package com.example.tugas8khairulrijalsyauqi

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class IOSNetworkMonitor : NetworkMonitor {
    // iOS network monitoring would typically use NWPathMonitor
    // For simplicity, we'll use a placeholder that always returns true
    // In a real app, you would use Apple's Network framework
    private val _isConnected = MutableStateFlow(true)
    override val isConnected: Flow<Boolean> = _isConnected.asStateFlow()
}

actual fun getNetworkMonitor(): NetworkMonitor {
    return IOSNetworkMonitor()
}
