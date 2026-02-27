package com.diego.matesanz.jedi.archive.feature.search

import com.diego.matesanz.jedi.archive.core.domain.model.EntityType
import com.diego.matesanz.jedi.archive.core.domain.model.SearchResult

/**
 * Estados de UI para la pantalla de búsqueda
 */
sealed interface SearchUiState {
    /**
     * Estado inicial, sin búsqueda realizada
     */
    data object Initial : SearchUiState

    /**
     * Buscando...
     */
    data object Loading : SearchUiState

    /**
     * Resultados encontrados
     */
    data class Success(
        val results: List<SearchResult>,
        val query: String
    ) : SearchUiState

    /**
     * Sin resultados
     */
    data class Empty(val query: String) : SearchUiState

    /**
     * Error en la búsqueda
     */
    data class Error(val message: String) : SearchUiState
}

/**
 * Filtro de categoría seleccionada
 */
data class CategoryFilter(
    val type: EntityType?,
    val displayName: String
) {
    companion object {
        val ALL = CategoryFilter(null, "All")
        val PEOPLE = CategoryFilter(EntityType.PERSON, "People")
        val PLANETS = CategoryFilter(EntityType.PLANET, "Planets")
        val SPECIES = CategoryFilter(EntityType.SPECIES, "Species")
        val STARSHIPS = CategoryFilter(EntityType.STARSHIP, "Starships")
        val VEHICLES = CategoryFilter(EntityType.VEHICLE, "Vehicles")
        val FILMS = CategoryFilter(EntityType.FILM, "Films")

        fun all() = listOf(ALL, PEOPLE, PLANETS, SPECIES, STARSHIPS, VEHICLES, FILMS)
    }
}
