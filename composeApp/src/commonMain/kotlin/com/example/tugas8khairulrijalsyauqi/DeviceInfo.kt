package com.example.tugas8khairulrijalsyauqi

/**
 * Common interface for device information
 * Uses expect/actual pattern for platform-specific implementations
 */
interface DeviceInfo {
    val platformName: String
    val osVersion: String
    val deviceModel: String
    val appVersion: String
}
