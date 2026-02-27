package com.diego.matesanz.jedi.archive.core.data.repository

import com.diego.matesanz.jedi.archive.core.data.mapper.*
import com.diego.matesanz.jedi.archive.core.domain.model.*
import com.diego.matesanz.jedi.archive.core.domain.repository.SwapiRepository
import com.diego.matesanz.jedi.archive.core.network.api.SwapiService
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

/**
 * Implementación del repositorio SWAPI
 */
class SwapiRepositoryImpl(
    private val apiService: SwapiService
) : SwapiRepository {

    override suspend fun search(query: String, category: EntityType?): Result<List<SearchResult>> =
        runCatching {
            if (category != null) {
                // Buscar en categoría específica
                searchInCategory(query, category)
            } else {
                // Buscar en todas las categorías en paralelo
                searchInAllCategories(query)
            }
        }

    private suspend fun searchInCategory(query: String, category: EntityType): List<SearchResult> {
        return when (category) {
            EntityType.PERSON -> {
                val people = apiService.getPeople(search = query).results
                PersonMapper.toDomainList(people).map { SearchResultMapper.personToSearchResult(it) }
            }
            EntityType.PLANET -> {
                val planets = apiService.getPlanets(search = query).results
                PlanetMapper.toDomainList(planets).map { SearchResultMapper.planetToSearchResult(it) }
            }
            EntityType.SPECIES -> {
                val species = apiService.getSpecies(search = query).results
                SpeciesMapper.toDomainList(species).map { SearchResultMapper.speciesToSearchResult(it) }
            }
            EntityType.STARSHIP -> {
                val starships = apiService.getStarships(search = query).results
                StarshipMapper.toDomainList(starships).map { SearchResultMapper.starshipToSearchResult(it) }
            }
            EntityType.VEHICLE -> {
                val vehicles = apiService.getVehicles(search = query).results
                VehicleMapper.toDomainList(vehicles).map { SearchResultMapper.vehicleToSearchResult(it) }
            }
            EntityType.FILM -> {
                // Films no tiene búsqueda en SWAPI, buscamos localmente
                val films = apiService.getFilms().results
                FilmMapper.toDomainList(films)
                    .filter { it.title.contains(query, ignoreCase = true) }
                    .map { SearchResultMapper.filmToSearchResult(it) }
            }
        }
    }

    private suspend fun searchInAllCategories(query: String): List<SearchResult> = coroutineScope {
        val peopleDeferred = async { searchPeople(query).getOrElse { emptyList() } }
        val planetsDeferred = async { searchPlanets(query).getOrElse { emptyList() } }
        val speciesDeferred = async { searchSpecies(query).getOrElse { emptyList() } }
        val starshipsDeferred = async { searchStarships(query).getOrElse { emptyList() } }
        val vehiclesDeferred = async { searchVehicles(query).getOrElse { emptyList() } }

        val people = peopleDeferred.await().map { SearchResultMapper.personToSearchResult(it) }
        val planets = planetsDeferred.await().map { SearchResultMapper.planetToSearchResult(it) }
        val species = speciesDeferred.await().map { SearchResultMapper.speciesToSearchResult(it) }
        val starships = starshipsDeferred.await().map { SearchResultMapper.starshipToSearchResult(it) }
        val vehicles = vehiclesDeferred.await().map { SearchResultMapper.vehicleToSearchResult(it) }

        people + planets + species + starships + vehicles
    }

    // ===== People =====
    override suspend fun getPeople(page: Int): Result<List<Person>> = runCatching {
        val response = apiService.getPeople(page)
        PersonMapper.toDomainList(response.results)
    }

    override suspend fun getPerson(id: String): Result<Person> = runCatching {
        val dto = apiService.getPerson(id)
        PersonMapper.toDomain(dto)
    }

    override suspend fun searchPeople(query: String): Result<List<Person>> = runCatching {
        val response = apiService.getPeople(search = query)
        PersonMapper.toDomainList(response.results)
    }

    // ===== Planets =====
    override suspend fun getPlanets(page: Int): Result<List<Planet>> = runCatching {
        val response = apiService.getPlanets(page)
        PlanetMapper.toDomainList(response.results)
    }

    override suspend fun getPlanet(id: String): Result<Planet> = runCatching {
        val dto = apiService.getPlanet(id)
        PlanetMapper.toDomain(dto)
    }

    override suspend fun searchPlanets(query: String): Result<List<Planet>> = runCatching {
        val response = apiService.getPlanets(search = query)
        PlanetMapper.toDomainList(response.results)
    }

    // ===== Species =====
    override suspend fun getSpecies(page: Int): Result<List<Species>> = runCatching {
        val response = apiService.getSpecies(page)
        SpeciesMapper.toDomainList(response.results)
    }

    override suspend fun getSingleSpecies(id: String): Result<Species> = runCatching {
        val dto = apiService.getSingleSpecies(id)
        SpeciesMapper.toDomain(dto)
    }

    override suspend fun searchSpecies(query: String): Result<List<Species>> = runCatching {
        val response = apiService.getSpecies(search = query)
        SpeciesMapper.toDomainList(response.results)
    }

    // ===== Starships =====
    override suspend fun getStarships(page: Int): Result<List<Starship>> = runCatching {
        val response = apiService.getStarships(page)
        StarshipMapper.toDomainList(response.results)
    }

    override suspend fun getStarship(id: String): Result<Starship> = runCatching {
        val dto = apiService.getStarship(id)
        StarshipMapper.toDomain(dto)
    }

    override suspend fun searchStarships(query: String): Result<List<Starship>> = runCatching {
        val response = apiService.getStarships(search = query)
        StarshipMapper.toDomainList(response.results)
    }

    // ===== Vehicles =====
    override suspend fun getVehicles(page: Int): Result<List<Vehicle>> = runCatching {
        val response = apiService.getVehicles(page)
        VehicleMapper.toDomainList(response.results)
    }

    override suspend fun getVehicle(id: String): Result<Vehicle> = runCatching {
        val dto = apiService.getVehicle(id)
        VehicleMapper.toDomain(dto)
    }

    override suspend fun searchVehicles(query: String): Result<List<Vehicle>> = runCatching {
        val response = apiService.getVehicles(search = query)
        VehicleMapper.toDomainList(response.results)
    }

    // ===== Films =====
    override suspend fun getFilms(): Result<List<Film>> = runCatching {
        val response = apiService.getFilms()
        FilmMapper.toDomainList(response.results)
    }

    override suspend fun getFilm(id: String): Result<Film> = runCatching {
        val dto = apiService.getFilm(id)
        FilmMapper.toDomain(dto)
    }
}
