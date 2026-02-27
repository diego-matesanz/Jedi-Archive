package com.diego.matesanz.jedi.archive.core.domain.repository

import com.diego.matesanz.jedi.archive.core.domain.model.*

/**
 * Repository para acceder a datos de SWAPI
 * Define contratos que serán implementados en la capa de datos
 */
interface SwapiRepository {

    /**
     * Busca en todas las categorías o en una específica
     * @param query Término de búsqueda
     * @param category Categoría específica o null para buscar en todas
     * @return Lista de resultados de búsqueda
     */
    suspend fun search(query: String, category: EntityType? = null): Result<List<SearchResult>>

    // ===== People =====
    suspend fun getPeople(page: Int = 1): Result<List<Person>>
    suspend fun getPerson(id: String): Result<Person>
    suspend fun searchPeople(query: String): Result<List<Person>>

    // ===== Planets =====
    suspend fun getPlanets(page: Int = 1): Result<List<Planet>>
    suspend fun getPlanet(id: String): Result<Planet>
    suspend fun searchPlanets(query: String): Result<List<Planet>>

    // ===== Species =====
    suspend fun getSpecies(page: Int = 1): Result<List<Species>>
    suspend fun getSingleSpecies(id: String): Result<Species>
    suspend fun searchSpecies(query: String): Result<List<Species>>

    // ===== Starships =====
    suspend fun getStarships(page: Int = 1): Result<List<Starship>>
    suspend fun getStarship(id: String): Result<Starship>
    suspend fun searchStarships(query: String): Result<List<Starship>>

    // ===== Vehicles =====
    suspend fun getVehicles(page: Int = 1): Result<List<Vehicle>>
    suspend fun getVehicle(id: String): Result<Vehicle>
    suspend fun searchVehicles(query: String): Result<List<Vehicle>>

    // ===== Films =====
    suspend fun getFilms(): Result<List<Film>>
    suspend fun getFilm(id: String): Result<Film>
}
