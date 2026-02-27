package com.diego.matesanz.jedi.archive.feature.detail

import app.cash.turbine.test
import com.diego.matesanz.jedi.archive.core.domain.model.EntityType
import com.diego.matesanz.jedi.archive.core.domain.model.Person
import com.diego.matesanz.jedi.archive.core.domain.usecase.GetEntityDetailUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class DetailViewModelTest {

    private lateinit var getEntityDetailUseCase: GetEntityDetailUseCase
    private lateinit var viewModel: DetailViewModel
    private val testDispatcher = StandardTestDispatcher()

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
        filmIds = listOf("1", "2", "3"),
        speciesIds = emptyList(),
        starshipIds = listOf("12", "22"),
        vehicleIds = listOf("14", "30")
    )

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        getEntityDetailUseCase = mockk()
        viewModel = DetailViewModel(getEntityDetailUseCase)
    }

    @Test
    fun `initial state is Loading`() = runTest(testDispatcher) {
        viewModel.uiState.test {
            assertTrue(awaitItem() is DetailUiState.Loading)
        }
    }

    @Test
    fun `loadDetail with valid entity updates state to Success`() = runTest(testDispatcher) {
        // Given
        coEvery { getEntityDetailUseCase(EntityType.PERSON, "1") } returns Result.success(mockPerson)

        viewModel.uiState.test {
            // Initial Loading state
            assertTrue(awaitItem() is DetailUiState.Loading)

            // When
            viewModel.loadDetail(EntityType.PERSON, "1")

            // Then
            val successState = awaitItem() as DetailUiState.Success
            assertEquals(mockPerson, successState.entity)

            coVerify { getEntityDetailUseCase(EntityType.PERSON, "1") }
        }
    }

    @Test
    fun `loadDetail with error updates state to Error`() = runTest(testDispatcher) {
        // Given
        val errorMessage = "Entity not found"
        coEvery { getEntityDetailUseCase(EntityType.PERSON, "999") } returns
            Result.failure(Exception(errorMessage))

        viewModel.uiState.test {
            // Initial Loading state
            assertTrue(awaitItem() is DetailUiState.Loading)

            // When
            viewModel.loadDetail(EntityType.PERSON, "999")

            // Then
            val errorState = awaitItem() as DetailUiState.Error
            assertEquals(errorMessage, errorState.message)
        }
    }

    @Test
    fun `retry calls loadDetail again`() = runTest(testDispatcher) {
        // Given
        coEvery { getEntityDetailUseCase(EntityType.PERSON, "1") } returns Result.success(mockPerson)

        // When
        viewModel.retry(EntityType.PERSON, "1")
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        coVerify(exactly = 1) { getEntityDetailUseCase(EntityType.PERSON, "1") }
    }

    @Test
    fun `getDetailSections for Person returns correct sections`() {
        // When
        val sections = viewModel.getDetailSections(mockPerson)

        // Then
        assertTrue(sections.isNotEmpty())
        assertTrue(sections.any { it.title == "Basic Information" })
        assertTrue(sections.any { it.title == "Appearance" })
        assertTrue(sections.any { it.title == "Relations" })
    }

    @Test
    fun `loadDetail twice updates state correctly`() = runTest(testDispatcher) {
        // Given
        val person1 = mockPerson.copy(id = "1", name = "Luke")
        val person2 = mockPerson.copy(id = "2", name = "Leia")

        coEvery { getEntityDetailUseCase(EntityType.PERSON, "1") } returns Result.success(person1)
        coEvery { getEntityDetailUseCase(EntityType.PERSON, "2") } returns Result.success(person2)

        viewModel.uiState.test {
            // Initial
            assertTrue(awaitItem() is DetailUiState.Loading)

            // Load first person
            viewModel.loadDetail(EntityType.PERSON, "1")
            val state1 = awaitItem() as DetailUiState.Success
            assertEquals("Luke", state1.entity.name)

            // Load second person
            viewModel.loadDetail(EntityType.PERSON, "2")
            assertTrue(awaitItem() is DetailUiState.Loading)
            val state2 = awaitItem() as DetailUiState.Success
            assertEquals("Leia", state2.entity.name)
        }
    }
}
