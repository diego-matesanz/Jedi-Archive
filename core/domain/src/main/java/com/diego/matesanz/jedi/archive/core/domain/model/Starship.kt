package com.diego.matesanz.jedi.archive.core.domain.model

/**
 * Entidad de dominio para una Nave Espacial de Star Wars
 */
data class Starship(
    override val id: String,
    override val name: String,
    val model: String,
    val starshipClass: String,
    val manufacturer: String,
    val costInCredits: String,
    val length: String,
    val crew: String,
    val passengers: String,
    val maxAtmospheringSpeed: String,
    val hyperdriveRating: String,
    val mglt: String,
    val cargoCapacity: String,
    val consumables: String,
    val pilotIds: List<String>,
    val filmIds: List<String>,
    override val imageUrl: String? = null
) : SwapiEntity {
    override val type = EntityType.STARSHIP
}
