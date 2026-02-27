package com.diego.matesanz.jedi.archive.core.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PlanetDto(
    val name: String,
    val climate: String,
    val diameter: String,
    val gravity: String,
    @SerialName("orbital_period") val orbitalPeriod: String,
    val population: String,
    @SerialName("rotation_period") val rotationPeriod: String,
    @SerialName("surface_water") val surfaceWater: String,
    val terrain: String,
    val residents: List<String>,
    val films: List<String>,
    val url: String
)

@Serializable
data class PlanetsResponse(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<PlanetDto>
)
