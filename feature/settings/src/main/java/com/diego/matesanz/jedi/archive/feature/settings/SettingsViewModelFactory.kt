package com.diego.matesanz.jedi.archive.feature.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.diego.matesanz.jedi.archive.core.datastore.ThemePreferences

/**
 * Factory para crear instancias de SettingsViewModel
 */
class SettingsViewModelFactory(
    private val themePreferences: ThemePreferences
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SettingsViewModel::class.java)) {
            return SettingsViewModel(themePreferences) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}
