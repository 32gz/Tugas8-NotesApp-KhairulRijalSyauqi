package com.example.tugas8khairulrijalsyauqi

import android.os.Build

actual class Platform {
    actual val name: String = "Android ${Build.VERSION.SDK_INT}"
}

actual fun getPlatform(): Platform = Platform()

class AndroidDeviceInfo : DeviceInfo {
    override val platformName: String = "Android"
    override val osVersion: String = "Android ${Build.VERSION.SDK_INT} (API ${Build.VERSION.SDK_INT})"
    override val deviceModel: String = "${Build.MANUFACTURER} ${Build.MODEL}"
    override val appVersion: String = BuildConfig.VERSION_NAME
}

actual fun getDeviceInfo(): DeviceInfo = AndroidDeviceInfo()
