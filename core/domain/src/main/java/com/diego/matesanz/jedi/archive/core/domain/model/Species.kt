package com.diego.matesanz.jedi.archive.core.domain.model

/**
 * Entidad de dominio para una Especie de Star Wars
 */
data class Species(
    override val id: String,
    override val name: String,
    val classification: String,
    val designation: String,
    val averageHeight: String,
    val averageLifespan: String,
    val eyeColors: String,
    val hairColors: String,
    val skinColors: String,
    val language: String,
    val homeworldId: String?,
    val peopleIds: List<String>,
    val filmIds: List<String>,
    override val imageUrl: String? = null
) : SwapiEntity {
    override val type = EntityType.SPECIES
}
