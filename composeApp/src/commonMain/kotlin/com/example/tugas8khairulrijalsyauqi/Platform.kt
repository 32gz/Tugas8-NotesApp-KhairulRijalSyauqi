package com.example.tugas8khairulrijalsyauqi

expect class Platform {
    val name: String
}

expect fun getPlatform(): Platform
expect fun getDeviceInfo(): DeviceInfo
