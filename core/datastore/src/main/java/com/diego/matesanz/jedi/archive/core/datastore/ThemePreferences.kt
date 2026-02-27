package com.diego.matesanz.jedi.archive.core.datastore

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

/**
 * DataStore extension para preferencias
 */
private val Context.dataStore by preferencesDataStore(name = "theme_preferences")

/**
 * Gestor simple de preferencias de tema
 */
class ThemePreferences(private val context: Context) {

    private companion object {
        val IS_DARK_THEME = booleanPreferencesKey("is_dark_theme")
    }

    /**
     * Flow que emite true si el tema oscuro está activado
     */
    val isDarkTheme: Flow<Boolean> = context.dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            preferences[IS_DARK_THEME] ?: false
        }

    /**
     * Establece si el tema oscuro está activado
     */
    suspend fun setDarkTheme(isDark: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[IS_DARK_THEME] = isDark
        }
    }
}
