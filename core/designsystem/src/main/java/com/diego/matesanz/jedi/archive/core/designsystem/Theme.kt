package com.diego.matesanz.jedi.archive.core.designsystem

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

/**
 * Color Scheme Light Mode - Era Dorada de la Alta República
 */
private val LightColorScheme = lightColorScheme(
    primary = GoldenLight,
    onPrimary = OnPrimaryLight,
    primaryContainer = ParchmentLight,
    onPrimaryContainer = OnSurfaceLight,

    secondary = BronzeLight,
    onSecondary = OnPrimaryLight,
    secondaryContainer = ParchmentLight,
    onSecondaryContainer = OnSurfaceLight,

    tertiary = ParchmentLight,
    onTertiary = OnSurfaceLight,

    error = ErrorLight,
    onError = Color(0xFFFFFFFF),
    errorContainer = Color(0xFFFFDAD6),
    onErrorContainer = Color(0xFF410002),

    background = BackgroundLight,
    onBackground = OnBackgroundLight,

    surface = SurfaceLight,
    onSurface = OnSurfaceLight,
    surfaceVariant = ParchmentLight,
    onSurfaceVariant = OnSurfaceLight,

    outline = OutlineLight,
    outlineVariant = DividerLight,

    scrim = ScrimLight
)

/**
 * Color Scheme Dark Mode - Archivo Nocturno
 */
private val DarkColorScheme = darkColorScheme(
    primary = GoldenDark,
    onPrimary = OnPrimaryDark,
    primaryContainer = ParchmentDark,
    onPrimaryContainer = OnSurfaceDark,

    secondary = BronzeDark,
    onSecondary = OnPrimaryDark,
    secondaryContainer = ParchmentDark,
    onSecondaryContainer = OnSurfaceDark,

    tertiary = ParchmentDark,
    onTertiary = OnSurfaceDark,

    error = ErrorDark,
    onError = Color(0xFF690005),
    errorContainer = Color(0xFF93000A),
    onErrorContainer = Color(0xFFFFDAD6),

    background = BackgroundDark,
    onBackground = OnBackgroundDark,

    surface = SurfaceDark,
    onSurface = OnSurfaceDark,
    surfaceVariant = ParchmentDark,
    onSurfaceVariant = OnSurfaceDark,

    outline = OutlineDark,
    outlineVariant = DividerDark,

    scrim = ScrimDark
)

/**
 * Theme principal de Jedi Archive
 *
 * @param darkTheme Si debe usar el tema oscuro (Archivo Nocturno)
 * @param dynamicColor Si debe usar colores dinámicos del sistema (deshabilitado para mantener estética Alta República)
 * @param content Contenido de la aplicación
 */
@Composable
fun JediArchiveTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false, // Deshabilitado para mantener paleta Alta República
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.background.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = JediArchiveTypography,
        shapes = JediArchiveShapes,
        content = content
    )
}
