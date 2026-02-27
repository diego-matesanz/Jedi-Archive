package com.diego.matesanz.jedi.archive.feature.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.diego.matesanz.jedi.archive.core.domain.usecase.SearchUseCase

/**
 * Factory para crear instancias de SearchViewModel
 */
class SearchViewModelFactory(
    private val searchUseCase: SearchUseCase
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SearchViewModel::class.java)) {
            return SearchViewModel(searchUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}
