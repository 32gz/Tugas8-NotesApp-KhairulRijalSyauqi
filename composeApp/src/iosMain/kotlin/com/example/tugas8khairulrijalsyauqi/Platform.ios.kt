package com.example.tugas8khairulrijalsyauqi

import platform.UIKit.UIDevice

actual class Platform {
    actual val name: String = UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion
}

actual fun getPlatform(): Platform = Platform()

class IOSDeviceInfo : DeviceInfo {
    override val platformName: String = "iOS"
    override val osVersion: String = "${UIDevice.currentDevice.systemName()} ${UIDevice.currentDevice.systemVersion}"
    override val deviceModel: String = UIDevice.currentDevice.model
    override val appVersion: String = "1.0"
}

actual fun getDeviceInfo(): DeviceInfo = IOSDeviceInfo()
