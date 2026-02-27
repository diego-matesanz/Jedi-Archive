package com.diego.matesanz.jedi.archive.feature.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.diego.matesanz.jedi.archive.core.domain.usecase.SearchUseCase
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

/**
 * ViewModel para la pantalla de búsqueda
 */
@OptIn(FlowPreview::class)
class SearchViewModel(
    private val searchUseCase: SearchUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<SearchUiState>(SearchUiState.Initial)
    val uiState: StateFlow<SearchUiState> = _uiState.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _selectedCategory = MutableStateFlow(CategoryFilter.ALL)
    val selectedCategory: StateFlow<CategoryFilter> = _selectedCategory.asStateFlow()

    init {
        // Debounce de búsqueda automática
        viewModelScope.launch {
            searchQuery
                .debounce(500) // Espera 500ms después del último cambio
                .filter { it.length >= 2 } // Mínimo 2 caracteres
                .collect { query ->
                    performSearch(query)
                }
        }
    }

    /**
     * Actualiza el query de búsqueda
     */
    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query
        if (query.isEmpty()) {
            _uiState.value = SearchUiState.Initial
        }
    }

    /**
     * Cambia la categoría de filtro
     */
    fun onCategorySelected(category: CategoryFilter) {
        _selectedCategory.value = category
        val currentQuery = _searchQuery.value
        if (currentQuery.isNotEmpty()) {
            performSearch(currentQuery)
        }
    }

    /**
     * Ejecuta la búsqueda manualmente (cuando se presiona el botón)
     */
    fun onSearchClicked() {
        val query = _searchQuery.value
        if (query.isNotEmpty()) {
            performSearch(query)
        }
    }

    /**
     * Limpia la búsqueda
     */
    fun onClearSearch() {
        _searchQuery.value = ""
        _uiState.value = SearchUiState.Initial
    }

    private fun performSearch(query: String) {
        _uiState.value = SearchUiState.Loading

        viewModelScope.launch {
            val result = searchUseCase(
                query = query,
                category = _selectedCategory.value.type
            )

            _uiState.value = result.fold(
                onSuccess = { results ->
                    if (results.isEmpty()) {
                        SearchUiState.Empty(query)
                    } else {
                        SearchUiState.Success(results, query)
                    }
                },
                onFailure = { exception ->
                    SearchUiState.Error(
                        exception.message ?: "Unknown error occurred"
                    )
                }
            )
        }
    }
}
