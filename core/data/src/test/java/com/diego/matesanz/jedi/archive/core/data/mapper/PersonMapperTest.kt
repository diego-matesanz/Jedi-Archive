package com.diego.matesanz.jedi.archive.core.data.mapper

import com.diego.matesanz.jedi.archive.core.network.dto.PersonDto
import org.junit.Assert.assertEquals
import org.junit.Test

class PersonMapperTest {

    private val mockPersonDto = PersonDto(
        name = "Luke Skywalker",
        birthYear = "19BBY",
        eyeColor = "blue",
        gender = "male",
        hairColor = "blond",
        height = "172",
        mass = "77",
        skinColor = "fair",
        homeworld = "https://swapi.dev/api/planets/1/",
        films = listOf(
            "https://swapi.dev/api/films/1/",
            "https://swapi.dev/api/films/2/"
        ),
        species = emptyList(),
        starships = listOf("https://swapi.dev/api/starships/12/"),
        vehicles = listOf("https://swapi.dev/api/vehicles/14/"),
        url = "https://swapi.dev/api/people/1/"
    )

    @Test
    fun `toDomain maps PersonDto correctly`() {
        // When
        val person = PersonMapper.toDomain(mockPersonDto)

        // Then
        assertEquals("1", person.id)
        assertEquals("Luke Skywalker", person.name)
        assertEquals("19BBY", person.birthYear)
        assertEquals("blue", person.eyeColor)
        assertEquals("male", person.gender)
        assertEquals("blond", person.hairColor)
        assertEquals("172", person.height)
        assertEquals("77", person.mass)
        assertEquals("fair", person.skinColor)
    }

    @Test
    fun `toDomain extracts IDs from URLs correctly`() {
        // When
        val person = PersonMapper.toDomain(mockPersonDto)

        // Then
        assertEquals("1", person.homeworldId)
        assertEquals(listOf("1", "2"), person.filmIds)
        assertEquals(listOf("12"), person.starshipIds)
        assertEquals(listOf("14"), person.vehicleIds)
        assertEquals(emptyList<String>(), person.speciesIds)
    }

    @Test
    fun `toDomain handles blank homeworld correctly`() {
        // Given
        val dtoWithBlankHomeworld = mockPersonDto.copy(homeworld = "")

        // When
        val person = PersonMapper.toDomain(dtoWithBlankHomeworld)

        // Then
        assertEquals(null, person.homeworldId)
    }

    @Test
    fun `toDomainList maps list of DTOs correctly`() {
        // Given
        val dtos = listOf(
            mockPersonDto,
            mockPersonDto.copy(name = "Leia", url = "https://swapi.dev/api/people/2/")
        )

        // When
        val people = PersonMapper.toDomainList(dtos)

        // Then
        assertEquals(2, people.size)
        assertEquals("Luke Skywalker", people[0].name)
        assertEquals("Leia", people[1].name)
        assertEquals("1", people[0].id)
        assertEquals("2", people[1].id)
    }

    @Test
    fun `toDomain handles empty lists correctly`() {
        // Given
        val dtoWithEmptyLists = mockPersonDto.copy(
            films = emptyList(),
            species = emptyList(),
            starships = emptyList(),
            vehicles = emptyList()
        )

        // When
        val person = PersonMapper.toDomain(dtoWithEmptyLists)

        // Then
        assertEquals(emptyList<String>(), person.filmIds)
        assertEquals(emptyList<String>(), person.speciesIds)
        assertEquals(emptyList<String>(), person.starshipIds)
        assertEquals(emptyList<String>(), person.vehicleIds)
    }
}
