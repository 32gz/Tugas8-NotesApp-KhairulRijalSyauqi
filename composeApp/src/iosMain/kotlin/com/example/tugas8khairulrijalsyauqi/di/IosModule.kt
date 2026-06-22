package com.example.tugas8khairulrijalsyauqi.di

import com.example.tugas8khairulrijalsyauqi.DeviceInfo
import com.example.tugas8khairulrijalsyauqi.NetworkMonitor
import com.example.tugas8khairulrijalsyauqi.IOSDeviceInfo
import com.example.tugas8khairulrijalsyauqi.getNetworkMonitor
import org.koin.dsl.module

/**
 * iOS-specific Koin module
 * Contains iOS-specific dependencies
 */
val iosModule = module {
    // DeviceInfo - singleton for the app
    single<DeviceInfo> { IOSDeviceInfo() }

    // NetworkMonitor
    single<NetworkMonitor> { getNetworkMonitor() }
}
