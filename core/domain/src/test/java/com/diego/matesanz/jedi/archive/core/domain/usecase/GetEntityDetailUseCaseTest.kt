package com.diego.matesanz.jedi.archive.core.domain.usecase

import com.diego.matesanz.jedi.archive.core.domain.model.EntityType
import com.diego.matesanz.jedi.archive.core.domain.model.Person
import com.diego.matesanz.jedi.archive.core.domain.model.Planet
import com.diego.matesanz.jedi.archive.core.domain.repository.SwapiRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class GetEntityDetailUseCaseTest {

    private lateinit var repository: SwapiRepository
    private lateinit var getEntityDetailUseCase: GetEntityDetailUseCase

    private val mockPerson = Person(
        id = "1",
        name = "Luke Skywalker",
        birthYear = "19BBY",
        eyeColor = "blue",
        gender = "male",
        hairColor = "blond",
        height = "172",
        mass = "77",
        skinColor = "fair",
        homeworldId = "1",
        filmIds = listOf("1", "2"),
        speciesIds = emptyList(),
        starshipIds = emptyList(),
        vehicleIds = emptyList()
    )

    private val mockPlanet = Planet(
        id = "1",
        name = "Tatooine",
        climate = "arid",
        diameter = "10465",
        gravity = "1 standard",
        orbitalPeriod = "304",
        population = "200000",
        rotationPeriod = "23",
        surfaceWater = "1",
        terrain = "desert",
        residentIds = listOf("1", "2"),
        filmIds = listOf("1")
    )

    @Before
    fun setup() {
        repository = mockk()
        getEntityDetailUseCase = GetEntityDetailUseCase(repository)
    }

    @Test
    fun `invoke with PERSON type calls repository getPerson`() = runTest {
        // Given
        coEvery { repository.getPerson("1") } returns Result.success(mockPerson)

        // When
        val result = getEntityDetailUseCase(EntityType.PERSON, "1")

        // Then
        assertTrue(result.isSuccess)
        assertEquals(mockPerson, result.getOrNull())
        coVerify { repository.getPerson("1") }
    }

    @Test
    fun `invoke with PLANET type calls repository getPlanet`() = runTest {
        // Given
        coEvery { repository.getPlanet("1") } returns Result.success(mockPlanet)

        // When
        val result = getEntityDetailUseCase(EntityType.PLANET, "1")

        // Then
        assertTrue(result.isSuccess)
        assertEquals(mockPlanet, result.getOrNull())
        coVerify { repository.getPlanet("1") }
    }

    @Test
    fun `invoke with repository error returns failure`() = runTest {
        // Given
        val exception = Exception("Network error")
        coEvery { repository.getPerson("999") } returns Result.failure(exception)

        // When
        val result = getEntityDetailUseCase(EntityType.PERSON, "999")

        // Then
        assertTrue(result.isFailure)
        assertEquals(exception, result.exceptionOrNull())
    }

    @Test
    fun `invoke calls correct repository method for each EntityType`() = runTest {
        // Setup mocks for all entity types
        coEvery { repository.getPerson(any()) } returns Result.success(mockPerson)
        coEvery { repository.getPlanet(any()) } returns Result.success(mockPlanet)
        coEvery { repository.getSingleSpecies(any()) } returns Result.success(mockk())
        coEvery { repository.getStarship(any()) } returns Result.success(mockk())
        coEvery { repository.getVehicle(any()) } returns Result.success(mockk())
        coEvery { repository.getFilm(any()) } returns Result.success(mockk())

        // Test each type
        getEntityDetailUseCase(EntityType.PERSON, "1")
        coVerify { repository.getPerson("1") }

        getEntityDetailUseCase(EntityType.PLANET, "1")
        coVerify { repository.getPlanet("1") }

        getEntityDetailUseCase(EntityType.SPECIES, "1")
        coVerify { repository.getSingleSpecies("1") }

        getEntityDetailUseCase(EntityType.STARSHIP, "1")
        coVerify { repository.getStarship("1") }

        getEntityDetailUseCase(EntityType.VEHICLE, "1")
        coVerify { repository.getVehicle("1") }

        getEntityDetailUseCase(EntityType.FILM, "1")
        coVerify { repository.getFilm("1") }
    }
}
