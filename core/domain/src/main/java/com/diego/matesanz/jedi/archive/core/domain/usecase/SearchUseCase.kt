package com.diego.matesanz.jedi.archive.core.domain.usecase

import com.diego.matesanz.jedi.archive.core.domain.model.EntityType
import com.diego.matesanz.jedi.archive.core.domain.model.SearchResult
import com.diego.matesanz.jedi.archive.core.domain.repository.SwapiRepository

/**
 * Use Case para realizar búsquedas en SWAPI
 */
class SearchUseCase(
    private val repository: SwapiRepository
) {
    /**
     * Ejecuta una búsqueda
     * @param query Término de búsqueda
     * @param category Categoría específica o null para todas
     */
    suspend operator fun invoke(
        query: String,
        category: EntityType? = null
    ): Result<List<SearchResult>> {
        if (query.isBlank()) {
            return Result.success(emptyList())
        }

        return repository.search(query.trim(), category)
    }
}
