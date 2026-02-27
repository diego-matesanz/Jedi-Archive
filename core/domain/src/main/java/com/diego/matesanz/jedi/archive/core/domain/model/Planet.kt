package com.diego.matesanz.jedi.archive.core.domain.model

/**
 * Entidad de dominio para un Planeta de Star Wars
 */
data class Planet(
    override val id: String,
    override val name: String,
    val climate: String,
    val diameter: String,
    val gravity: String,
    val orbitalPeriod: String,
    val population: String,
    val rotationPeriod: String,
    val surfaceWater: String,
    val terrain: String,
    val residentIds: List<String>,
    val filmIds: List<String>,
    override val imageUrl: String? = null
) : SwapiEntity {
    override val type = EntityType.PLANET
}
