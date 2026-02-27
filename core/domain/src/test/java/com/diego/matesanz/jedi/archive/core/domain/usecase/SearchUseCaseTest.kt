package com.diego.matesanz.jedi.archive.core.domain.usecase

import com.diego.matesanz.jedi.archive.core.domain.model.EntityType
import com.diego.matesanz.jedi.archive.core.domain.model.SearchResult
import com.diego.matesanz.jedi.archive.core.domain.repository.SwapiRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class SearchUseCaseTest {

    private lateinit var repository: SwapiRepository
    private lateinit var searchUseCase: SearchUseCase

    private val mockResults = listOf(
        SearchResult(
            id = "1",
            name = "Luke Skywalker",
            type = EntityType.PERSON,
            subtitle = "Birth Year: 19BBY"
        ),
        SearchResult(
            id = "2",
            name = "Leia Organa",
            type = EntityType.PERSON,
            subtitle = "Birth Year: 19BBY"
        )
    )

    @Before
    fun setup() {
        repository = mockk()
        searchUseCase = SearchUseCase(repository)
    }

    @Test
    fun `invoke with query and no category calls repository search`() = runTest {
        // Given
        val query = "Luke"
        coEvery { repository.search(query, null) } returns Result.success(mockResults)

        // When
        val result = searchUseCase(query, null)

        // Then
        assertTrue(result.isSuccess)
        assertEquals(mockResults, result.getOrNull())
        coVerify { repository.search(query, null) }
    }

    @Test
    fun `invoke with query and category filters correctly`() = runTest {
        // Given
        val query = "Luke"
        val category = EntityType.PERSON
        val filteredResults = mockResults.filter { it.type == category }

        coEvery { repository.search(query, category) } returns Result.success(filteredResults)

        // When
        val result = searchUseCase(query, category)

        // Then
        assertTrue(result.isSuccess)
        assertEquals(filteredResults, result.getOrNull())
        assertTrue(result.getOrNull()?.all { it.type == category } == true)
        coVerify { repository.search(query, category) }
    }

    @Test
    fun `invoke with empty query returns success with empty list`() = runTest {
        // Given
        val query = ""

        // When
        val result = searchUseCase(query, null)

        // Then
        assertTrue(result.isSuccess)
        assertEquals(emptyList<SearchResult>(), result.getOrNull())
    }

    @Test
    fun `invoke with repository error returns failure`() = runTest {
        // Given
        val query = "error"
        val exception = Exception("Network error")
        coEvery { repository.search(query, null) } returns Result.failure(exception)

        // When
        val result = searchUseCase(query, null)

        // Then
        assertTrue(result.isFailure)
        assertEquals(exception, result.exceptionOrNull())
    }

    @Test
    fun `invoke with blank query returns success with empty list`() = runTest {
        // Given
        val query = "   "

        // When
        val result = searchUseCase(query, null)

        // Then
        assertTrue(result.isSuccess)
        assertEquals(emptyList<SearchResult>(), result.getOrNull())
    }
}
