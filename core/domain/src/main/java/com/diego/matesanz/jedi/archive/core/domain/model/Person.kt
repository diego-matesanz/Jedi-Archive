package com.diego.matesanz.jedi.archive.core.domain.model

/**
 * Entidad de dominio para un Personaje de Star Wars
 */
data class Person(
    override val id: String,
    override val name: String,
    val birthYear: String,
    val eyeColor: String,
    val gender: String,
    val hairColor: String,
    val height: String,
    val mass: String,
    val skinColor: String,
    val homeworldId: String?,
    val filmIds: List<String>,
    val speciesIds: List<String>,
    val starshipIds: List<String>,
    val vehicleIds: List<String>,
    override val imageUrl: String? = null
) : SwapiEntity {
    override val type = EntityType.PERSON
}
