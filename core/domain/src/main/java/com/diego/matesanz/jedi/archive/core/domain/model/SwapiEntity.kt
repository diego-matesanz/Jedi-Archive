package com.diego.matesanz.jedi.archive.core.domain.model

/**
 * Tipo de entidad en SWAPI
 */
enum class EntityType {
    PERSON,
    PLANET,
    SPECIES,
    STARSHIP,
    VEHICLE,
    FILM
}

/**
 * Interface base para todas las entidades de SWAPI
 */
interface SwapiEntity {
    val id: String
    val name: String
    val type: EntityType
}

/**
 * Resultado de búsqueda genérico
 */
data class SearchResult(
    override val id: String,
    override val name: String,
    override val type: EntityType,
    val subtitle: String? = null
) : SwapiEntity
