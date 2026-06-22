package com.example.tugas8khairulrijalsyauqi

import kotlinx.coroutines.flow.Flow

/**
 * Common interface for network connectivity monitoring
 * Uses expect/actual pattern for platform-specific implementations
 */
interface NetworkMonitor {
    /**
     * Flow that emits true when network is available, false otherwise
     */
    val isConnected: Flow<Boolean>
}

expect fun getNetworkMonitor(): NetworkMonitor
