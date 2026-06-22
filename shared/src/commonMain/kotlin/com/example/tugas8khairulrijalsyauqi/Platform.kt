package com.example.tugas8khairulrijalsyauqi

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform