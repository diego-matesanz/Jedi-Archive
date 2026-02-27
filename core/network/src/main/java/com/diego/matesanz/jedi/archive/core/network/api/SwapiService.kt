package com.diego.matesanz.jedi.archive.core.network.api

import com.diego.matesanz.jedi.archive.core.network.dto.*
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Servicio API para SWAPI (Star Wars API)
 * Base URL: https://swapi.dev/api/
 */
interface SwapiService {

    // ===== People =====
    @GET("people/")
    suspend fun getPeople(
        @Query("page") page: Int? = null,
        @Query("search") search: String? = null
    ): PeopleResponse

    @GET("people/{id}/")
    suspend fun getPerson(@Path("id") id: String): PersonDto

    // ===== Planets =====
    @GET("planets/")
    suspend fun getPlanets(
        @Query("page") page: Int? = null,
        @Query("search") search: String? = null
    ): PlanetsResponse

    @GET("planets/{id}/")
    suspend fun getPlanet(@Path("id") id: String): PlanetDto

    // ===== Species =====
    @GET("species/")
    suspend fun getSpecies(
        @Query("page") page: Int? = null,
        @Query("search") search: String? = null
    ): SpeciesResponse

    @GET("species/{id}/")
    suspend fun getSingleSpecies(@Path("id") id: String): SpeciesDto

    // ===== Starships =====
    @GET("starships/")
    suspend fun getStarships(
        @Query("page") page: Int? = null,
        @Query("search") search: String? = null
    ): StarshipsResponse

    @GET("starships/{id}/")
    suspend fun getStarship(@Path("id") id: String): StarshipDto

    // ===== Vehicles =====
    @GET("vehicles/")
    suspend fun getVehicles(
        @Query("page") page: Int? = null,
        @Query("search") search: String? = null
    ): VehiclesResponse

    @GET("vehicles/{id}/")
    suspend fun getVehicle(@Path("id") id: String): VehicleDto

    // ===== Films =====
    @GET("films/")
    suspend fun getFilms(): FilmsResponse

    @GET("films/{id}/")
    suspend fun getFilm(@Path("id") id: String): FilmDto
}
