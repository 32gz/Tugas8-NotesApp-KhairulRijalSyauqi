package com.example.tugas8khairulrijalsyauqi.model

actual fun currentTimeMillis(): Long {
    return kotlinx.cinterop.CValue<kotlinx.cinterop.timespec>().let { ts ->
        kotlinx.cinterop.posix.clock_gettime?.invoke(kotlinx.cinterop.posix.CLOCK_REALTIME, ts.rawPtr)
        ts.timestamp * 1000
    }
}