package com.diego.matesanz.jedi.archive

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.diego.matesanz.jedi.archive.core.datastore.ThemePreferences
import com.diego.matesanz.jedi.archive.core.designsystem.JediArchiveTheme
import com.diego.matesanz.jedi.archive.navigation.JediArchiveNavHost

/**
 * Activity principal de Jedi Archive
 */
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val themePreferences = ThemePreferences(this@MainActivity)
            val isDarkTheme by themePreferences.isDarkTheme.collectAsState(initial = false)

            JediArchiveTheme(darkTheme = isDarkTheme) {
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    val navController = rememberNavController()

                    JediArchiveNavHost(
                        navController = navController,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }
    }
}
