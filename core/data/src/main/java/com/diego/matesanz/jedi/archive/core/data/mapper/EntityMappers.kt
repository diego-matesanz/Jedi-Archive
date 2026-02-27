package com.diego.matesanz.jedi.archive.core.data.mapper

import com.diego.matesanz.jedi.archive.core.data.util.ImageUrlProvider
import com.diego.matesanz.jedi.archive.core.domain.model.*
import com.diego.matesanz.jedi.archive.core.network.dto.*
import com.diego.matesanz.jedi.archive.core.network.util.UrlExtractor

/**
 * Mappers para convertir DTOs a entidades de dominio
 */

// ===== Planet Mapper =====
object PlanetMapper {
    fun toDomain(dto: PlanetDto): Planet {
        val id = UrlExtractor.extractId(dto.url)
        return Planet(
            id = id,
            name = dto.name,
            climate = dto.climate,
            diameter = dto.diameter,
            gravity = dto.gravity,
            orbitalPeriod = dto.orbitalPeriod,
            population = dto.population,
            rotationPeriod = dto.rotationPeriod,
            surfaceWater = dto.surfaceWater,
            terrain = dto.terrain,
            residentIds = UrlExtractor.extractIds(dto.residents),
            filmIds = UrlExtractor.extractIds(dto.films),
            imageUrl = ImageUrlProvider.getImageUrl(EntityType.PLANET, id)
        )
    }

    fun toDomainList(dtos: List<PlanetDto>): List<Planet> = dtos.map { toDomain(it) }
}

// ===== Species Mapper =====
object SpeciesMapper {
    fun toDomain(dto: SpeciesDto): Species {
        val id = UrlExtractor.extractId(dto.url)
        return Species(
            id = id,
            name = dto.name,
            classification = dto.classification,
            designation = dto.designation,
            averageHeight = dto.averageHeight,
            averageLifespan = dto.averageLifespan,
            eyeColors = dto.eyeColors,
            hairColors = dto.hairColors,
            skinColors = dto.skinColors,
            language = dto.language,
            homeworldId = dto.homeworld?.takeIf { it.isNotBlank() }?.let { UrlExtractor.extractId(it) },
            peopleIds = UrlExtractor.extractIds(dto.people),
            filmIds = UrlExtractor.extractIds(dto.films),
            imageUrl = ImageUrlProvider.getImageUrl(EntityType.SPECIES, id)
        )
    }

    fun toDomainList(dtos: List<SpeciesDto>): List<Species> = dtos.map { toDomain(it) }
}

// ===== Starship Mapper =====
object StarshipMapper {
    fun toDomain(dto: StarshipDto): Starship {
        val id = UrlExtractor.extractId(dto.url)
        return Starship(
            id = id,
            name = dto.name,
            model = dto.model,
            starshipClass = dto.starshipClass,
            manufacturer = dto.manufacturer,
            costInCredits = dto.costInCredits,
            length = dto.length,
            crew = dto.crew,
            passengers = dto.passengers,
            maxAtmospheringSpeed = dto.maxAtmospheringSpeed,
            hyperdriveRating = dto.hyperdriveRating,
            mglt = dto.mglt,
            cargoCapacity = dto.cargoCapacity,
            consumables = dto.consumables,
            pilotIds = UrlExtractor.extractIds(dto.pilots),
            filmIds = UrlExtractor.extractIds(dto.films),
            imageUrl = ImageUrlProvider.getImageUrl(EntityType.STARSHIP, id)
        )
    }

    fun toDomainList(dtos: List<StarshipDto>): List<Starship> = dtos.map { toDomain(it) }
}

// ===== Vehicle Mapper =====
object VehicleMapper {
    fun toDomain(dto: VehicleDto): Vehicle {
        val id = UrlExtractor.extractId(dto.url)
        return Vehicle(
            id = id,
            name = dto.name,
            model = dto.model,
            vehicleClass = dto.vehicleClass,
            manufacturer = dto.manufacturer,
            costInCredits = dto.costInCredits,
            length = dto.length,
            crew = dto.crew,
            passengers = dto.passengers,
            maxAtmospheringSpeed = dto.maxAtmospheringSpeed,
            cargoCapacity = dto.cargoCapacity,
            consumables = dto.consumables,
            pilotIds = UrlExtractor.extractIds(dto.pilots),
            filmIds = UrlExtractor.extractIds(dto.films),
            imageUrl = ImageUrlProvider.getImageUrl(EntityType.VEHICLE, id)
        )
    }

    fun toDomainList(dtos: List<VehicleDto>): List<Vehicle> = dtos.map { toDomain(it) }
}

// ===== Film Mapper =====
object FilmMapper {
    fun toDomain(dto: FilmDto): Film {
        val id = UrlExtractor.extractId(dto.url)
        return Film(
            id = id,
            name = dto.title,
            title = dto.title,
            episodeId = dto.episodeId,
            openingCrawl = dto.openingCrawl,
            director = dto.director,
            producer = dto.producer,
            releaseDate = dto.releaseDate,
            characterIds = UrlExtractor.extractIds(dto.characters),
            planetIds = UrlExtractor.extractIds(dto.planets),
            starshipIds = UrlExtractor.extractIds(dto.starships),
            vehicleIds = UrlExtractor.extractIds(dto.vehicles),
            speciesIds = UrlExtractor.extractIds(dto.species),
            imageUrl = ImageUrlProvider.getImageUrl(EntityType.FILM, id)
        )
    }

    fun toDomainList(dtos: List<FilmDto>): List<Film> = dtos.map { toDomain(it) }
}

// ===== SearchResult Mapper =====
object SearchResultMapper {
    fun personToSearchResult(person: Person): SearchResult {
        return SearchResult(
            id = person.id,
            name = person.name,
            type = EntityType.PERSON,
            subtitle = "Birth Year: ${person.birthYear}",
            imageUrl = person.imageUrl
        )
    }

    fun planetToSearchResult(planet: Planet): SearchResult {
        return SearchResult(
            id = planet.id,
            name = planet.name,
            type = EntityType.PLANET,
            subtitle = "Climate: ${planet.climate}",
            imageUrl = planet.imageUrl
        )
    }

    fun speciesToSearchResult(species: Species): SearchResult {
        return SearchResult(
            id = species.id,
            name = species.name,
            type = EntityType.SPECIES,
            subtitle = "Classification: ${species.classification}",
            imageUrl = species.imageUrl
        )
    }

    fun starshipToSearchResult(starship: Starship): SearchResult {
        return SearchResult(
            id = starship.id,
            name = starship.name,
            type = EntityType.STARSHIP,
            subtitle = "Model: ${starship.model}",
            imageUrl = starship.imageUrl
        )
    }

    fun vehicleToSearchResult(vehicle: Vehicle): SearchResult {
        return SearchResult(
            id = vehicle.id,
            name = vehicle.name,
            type = EntityType.VEHICLE,
            subtitle = "Model: ${vehicle.model}",
            imageUrl = vehicle.imageUrl
        )
    }

    fun filmToSearchResult(film: Film): SearchResult {
        return SearchResult(
            id = film.id,
            name = film.title,
            type = EntityType.FILM,
            subtitle = "Episode ${film.episodeId} - ${film.releaseDate}",
            imageUrl = film.imageUrl
        )
    }
}
