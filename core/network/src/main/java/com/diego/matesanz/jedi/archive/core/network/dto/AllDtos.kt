package com.diego.matesanz.jedi.archive.core.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

// ===== Species =====
@Serializable
data class SpeciesDto(
    val name: String,
    val classification: String,
    val designation: String,
    @SerialName("average_height") val averageHeight: String,
    @SerialName("average_lifespan") val averageLifespan: String,
    @SerialName("eye_colors") val eyeColors: String,
    @SerialName("hair_colors") val hairColors: String,
    @SerialName("skin_colors") val skinColors: String,
    val language: String,
    val homeworld: String?,
    val people: List<String>,
    val films: List<String>,
    val url: String
)

@Serializable
data class SpeciesResponse(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<SpeciesDto>
)

// ===== Starship =====
@Serializable
data class StarshipDto(
    val name: String,
    val model: String,
    @SerialName("starship_class") val starshipClass: String,
    val manufacturer: String,
    @SerialName("cost_in_credits") val costInCredits: String,
    val length: String,
    val crew: String,
    val passengers: String,
    @SerialName("max_atmosphering_speed") val maxAtmospheringSpeed: String,
    @SerialName("hyperdrive_rating") val hyperdriveRating: String,
    @SerialName("MGLT") val mglt: String,
    @SerialName("cargo_capacity") val cargoCapacity: String,
    val consumables: String,
    val pilots: List<String>,
    val films: List<String>,
    val url: String
)

@Serializable
data class StarshipsResponse(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<StarshipDto>
)

// ===== Vehicle =====
@Serializable
data class VehicleDto(
    val name: String,
    val model: String,
    @SerialName("vehicle_class") val vehicleClass: String,
    val manufacturer: String,
    @SerialName("cost_in_credits") val costInCredits: String,
    val length: String,
    val crew: String,
    val passengers: String,
    @SerialName("max_atmosphering_speed") val maxAtmospheringSpeed: String,
    @SerialName("cargo_capacity") val cargoCapacity: String,
    val consumables: String,
    val pilots: List<String>,
    val films: List<String>,
    val url: String
)

@Serializable
data class VehiclesResponse(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<VehicleDto>
)

// ===== Film =====
@Serializable
data class FilmDto(
    val title: String,
    @SerialName("episode_id") val episodeId: Int,
    @SerialName("opening_crawl") val openingCrawl: String,
    val director: String,
    val producer: String,
    @SerialName("release_date") val releaseDate: String,
    val characters: List<String>,
    val planets: List<String>,
    val starships: List<String>,
    val vehicles: List<String>,
    val species: List<String>,
    val url: String
)

@Serializable
data class FilmsResponse(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<FilmDto>
)
