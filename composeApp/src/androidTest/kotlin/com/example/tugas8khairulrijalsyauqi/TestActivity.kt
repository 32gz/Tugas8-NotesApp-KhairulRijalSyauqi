package com.example.tugas8khairulrijalsyauqi

import android.app.Activity
import android.os.Bundle
import androidx.compose.material3.Text
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.example.tugas8khairulrijalsyauqi.ui.theme.NotesAppTheme

/**
 * Empty Activity for UI testing purposes
 * Provides a minimal activity context for Compose UI tests
 */
class TestActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // This activity is used solely for UI testing
        // Compose content is set directly in tests via setContent
    }
}