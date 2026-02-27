package com.diego.matesanz.jedi.archive.core.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PersonDto(
    val name: String,
    @SerialName("birth_year") val birthYear: String,
    @SerialName("eye_color") val eyeColor: String,
    val gender: String,
    @SerialName("hair_color") val hairColor: String,
    val height: String,
    val mass: String,
    @SerialName("skin_color") val skinColor: String,
    val homeworld: String,
    val films: List<String>,
    val species: List<String>,
    val starships: List<String>,
    val vehicles: List<String>,
    val url: String
)

@Serializable
data class PeopleResponse(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<PersonDto>
)
