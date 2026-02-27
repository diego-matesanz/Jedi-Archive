package com.diego.matesanz.jedi.archive.core.domain.model

/**
 * Entidad de dominio para una Película de Star Wars
 */
data class Film(
    override val id: String,
    override val name: String,
    val title: String, // Alias de name para consistencia
    val episodeId: Int,
    val openingCrawl: String,
    val director: String,
    val producer: String,
    val releaseDate: String,
    val characterIds: List<String>,
    val planetIds: List<String>,
    val starshipIds: List<String>,
    val vehicleIds: List<String>,
    val speciesIds: List<String>
) : SwapiEntity {
    override val type = EntityType.FILM
}
