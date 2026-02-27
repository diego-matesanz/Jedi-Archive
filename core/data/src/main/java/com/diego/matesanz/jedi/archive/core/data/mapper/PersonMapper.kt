package com.diego.matesanz.jedi.archive.core.data.mapper

import com.diego.matesanz.jedi.archive.core.data.util.ImageUrlProvider
import com.diego.matesanz.jedi.archive.core.domain.model.EntityType
import com.diego.matesanz.jedi.archive.core.domain.model.Person
import com.diego.matesanz.jedi.archive.core.network.dto.PersonDto
import com.diego.matesanz.jedi.archive.core.network.util.UrlExtractor

/**
 * Mapper para convertir PersonDto a Person (domain entity)
 */
object PersonMapper {

    fun toDomain(dto: PersonDto): Person {
        val id = UrlExtractor.extractId(dto.url)
        return Person(
            id = id,
            name = dto.name,
            birthYear = dto.birthYear,
            eyeColor = dto.eyeColor,
            gender = dto.gender,
            hairColor = dto.hairColor,
            height = dto.height,
            mass = dto.mass,
            skinColor = dto.skinColor,
            homeworldId = dto.homeworld.takeIf { it.isNotBlank() }?.let { UrlExtractor.extractId(it) },
            filmIds = UrlExtractor.extractIds(dto.films),
            speciesIds = UrlExtractor.extractIds(dto.species),
            starshipIds = UrlExtractor.extractIds(dto.starships),
            vehicleIds = UrlExtractor.extractIds(dto.vehicles),
            imageUrl = ImageUrlProvider.getImageUrl(EntityType.PERSON, id)
        )
    }

    fun toDomainList(dtos: List<PersonDto>): List<Person> {
        return dtos.map { toDomain(it) }
    }
}
