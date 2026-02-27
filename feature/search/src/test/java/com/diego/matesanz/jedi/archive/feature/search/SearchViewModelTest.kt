package com.diego.matesanz.jedi.archive.feature.search

import app.cash.turbine.test
import com.diego.matesanz.jedi.archive.core.domain.model.EntityType
import com.diego.matesanz.jedi.archive.core.domain.model.SearchResult
import com.diego.matesanz.jedi.archive.core.domain.usecase.SearchUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SearchViewModelTest {

    private lateinit var searchUseCase: SearchUseCase
    private lateinit var viewModel: SearchViewModel
    private val testDispatcher = StandardTestDispatcher()

    private val mockSearchResults = listOf(
        SearchResult(
            id = "1",
            name = "Luke Skywalker",
            type = EntityType.PERSON,
            subtitle = "Birth Year: 19BBY"
        ),
        SearchResult(
            id = "2",
            name = "Tatooine",
            type = EntityType.PLANET,
            subtitle = "Climate: arid"
        )
    )

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        searchUseCase = mockk()
        viewModel = SearchViewModel(searchUseCase)
    }

    @Test
    fun `initial state is Initial`() = runTest(testDispatcher) {
        viewModel.uiState.test {
            assertEquals(SearchUiState.Initial, awaitItem())
        }
    }

    @Test
    fun `search with query triggers SearchUseCase and updates state to Success`() = runTest(testDispatcher) {
        // Given
        val query = "Luke"
        coEvery { searchUseCase(query, null) } returns Result.success(mockSearchResults)

        viewModel.uiState.test {
            // Initial state
            assertEquals(SearchUiState.Initial, awaitItem())

            // When
            viewModel.onSearchQueryChanged(query)

            // Advance time past debounce delay (500ms)
            advanceTimeBy(600)

            // Then - Loading state
            assertEquals(SearchUiState.Loading, awaitItem())

            // Then - Success state
            val successState = awaitItem() as SearchUiState.Success
            assertEquals(mockSearchResults, successState.results)

            coVerify { searchUseCase(query, null) }
        }
    }

    @Test
    fun `search with empty query does not trigger search`() = runTest(testDispatcher) {
        // When
        viewModel.onSearchQueryChanged("")
        advanceTimeBy(600)

        // Then - Should not call use case
        coVerify(exactly = 0) { searchUseCase(any(), any()) }

        // State should remain Initial
        viewModel.uiState.test {
            val currentState = awaitItem()
            assertTrue(currentState is SearchUiState.Initial || currentState is SearchUiState.Empty)
        }
    }

    @Test
    fun `search with error updates state to Error`() = runTest(testDispatcher) {
        // Given
        val query = "error"
        val errorMessage = "Network error"
        coEvery { searchUseCase(query, null) } returns Result.failure(Exception(errorMessage))

        viewModel.uiState.test {
            assertEquals(SearchUiState.Initial, awaitItem())

            // When
            viewModel.onSearchQueryChanged(query)
            advanceTimeBy(600)

            // Then
            assertEquals(SearchUiState.Loading, awaitItem())

            val errorState = awaitItem() as SearchUiState.Error
            assertEquals(errorMessage, errorState.message)
        }
    }

    @Test
    fun `category filter is applied correctly`() = runTest(testDispatcher) {
        // Given
        val query = "Luke"
        val category = EntityType.PERSON
        coEvery { searchUseCase(query, category) } returns Result.success(mockSearchResults)

        // When
        viewModel.onCategorySelected(CategoryFilter.PEOPLE)
        viewModel.onSearchQueryChanged(query)
        advanceTimeBy(600)

        // Then
        coVerify { searchUseCase(query, category) }
    }

    @Test
    fun `clear search resets query and state`() = runTest(testDispatcher) {
        // Given
        viewModel.onSearchQueryChanged("test")

        // When
        viewModel.onClearSearch()

        // Then
        viewModel.searchQuery.test {
            assertEquals("", awaitItem())
        }

        viewModel.uiState.test {
            val state = awaitItem()
            assertTrue(state is SearchUiState.Initial || state is SearchUiState.Empty)
        }
    }

    @Test
    fun `debounce prevents multiple rapid searches`() = runTest(testDispatcher) {
        // Given
        coEvery { searchUseCase(any(), any()) } returns Result.success(mockSearchResults)

        // When - Type multiple characters quickly
        viewModel.onSearchQueryChanged("L")
        advanceTimeBy(100)
        viewModel.onSearchQueryChanged("Lu")
        advanceTimeBy(100)
        viewModel.onSearchQueryChanged("Luk")
        advanceTimeBy(100)
        viewModel.onSearchQueryChanged("Luke")
        advanceTimeBy(600) // Wait for debounce

        // Then - Should only trigger one search
        coVerify(exactly = 1) { searchUseCase("Luke", any()) }
    }
}
