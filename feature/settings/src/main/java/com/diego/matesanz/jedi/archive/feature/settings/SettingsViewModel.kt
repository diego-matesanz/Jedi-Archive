package com.diego.matesanz.jedi.archive.feature.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.diego.matesanz.jedi.archive.core.datastore.ThemePreferences
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel para la pantalla de Settings
 */
class SettingsViewModel(
    private val themePreferences: ThemePreferences
) : ViewModel() {

    private val _uiState = MutableStateFlow(SettingsUiState())
    val uiState: StateFlow<SettingsUiState> = _uiState.asStateFlow()

    init {
        loadThemePreference()
    }

    /**
     * Carga la preferencia de tema actual
     */
    private fun loadThemePreference() {
        viewModelScope.launch {
            themePreferences.isDarkTheme.collect { isDark ->
                _uiState.value = SettingsUiState(
                    isDarkTheme = isDark,
                    isLoading = false
                )
            }
        }
    }

    /**
     * Cambia el tema de la aplicación
     */
    fun toggleTheme(isDark: Boolean) {
        viewModelScope.launch {
            themePreferences.setDarkTheme(isDark)
        }
    }
}
