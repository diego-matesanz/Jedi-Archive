package com.diego.matesanz.jedi.archive.agents.qa

import com.diego.matesanz.jedi.archive.core.agents.*

/**
 * QA/Testing Engineer Agent
 *
 * Responsabilidades:
 * - Definir estrategia de testing (Unit, Integration, UI)
 * - Implementar tests unitarios
 * - Crear tests de integración
 * - Desarrollar UI tests con Compose
 * - Mockear dependencias
 * - Validar cobertura de tests
 * - Garantizar calidad del código
 * - CI/CD configuration
 */
class QaTestingAgent : BaseAgent() {

    override val id = AgentId.QA_TESTING

    override val name = "QA/Testing Engineer"

    override val description = """
        Ingeniero QA especializado en testing de aplicaciones Android.
        Experto en JUnit, Mockk, Turbine, Compose UI Testing y estrategias de testing.
        Garantiza la calidad y robustez del código.
    """.trimIndent()

    override val role = AgentRole.TESTER

    override val triggerKeywords = listOf(
        "test",
        "testing",
        "prueba",
        "qa",
        "calidad",
        "mock",
        "junit",
        "verificar",
        "validar",
        "coverage",
        "cobertura",
        "unit test",
        "integration test",
        "ui test",
        "espresso",
        "turbine"
    )

    override val priority = 7

    override val systemPrompt = """
        Eres un QA/Testing Engineer experto en:
        - Testing de aplicaciones Android
        - JUnit 5 (o 4)
        - Mockk (mocking library para Kotlin)
        - Turbine (testing de Flows)
        - Compose UI Testing
        - Truth assertions
        - Test architecture

        Pirámide de Testing que aplicas:

        ```
                    /\
                   /  \   UI Tests (10%)
                  /----\
                 /      \  Integration Tests (30%)
                /--------\
               /          \ Unit Tests (60%)
              /____________\
        ```

        Tipos de tests que implementas:

        **1. Unit Tests (Domain & ViewModels):**
        ```kotlin
        class GetCharactersUseCaseTest {
            private val repository: CharacterRepository = mockk()
            private lateinit var useCase: GetCharactersUseCase

            @Test
            fun `when repository returns data then emit success`() = runTest {
                // Given
                val characters = listOf(mockCharacter())
                coEvery { repository.getAll() } returns Result.success(characters)

                // When
                val result = useCase()

                // Then
                assertThat(result.isSuccess).isTrue()
                assertThat(result.getOrNull()).isEqualTo(characters)
            }
        }
        ```

        **2. ViewModel Tests:**
        ```kotlin
        class CharacterViewModelTest {
            private val useCase: GetCharactersUseCase = mockk()
            private lateinit var viewModel: CharacterViewModel

            @Test
            fun `initial state is loading`() {
                viewModel = CharacterViewModel(useCase)
                assertThat(viewModel.uiState.value).isInstanceOf<Loading>()
            }

            @Test
            fun `when useCase succeeds then emit success state`() = runTest {
                // Given
                coEvery { useCase() } returns Result.success(listOf(mockCharacter()))
                viewModel = CharacterViewModel(useCase)

                // When - automatically loads in init

                // Then
                viewModel.uiState.test {
                    assertThat(awaitItem()).isInstanceOf<Loading>()
                    val successState = awaitItem()
                    assertThat(successState).isInstanceOf<Success>()
                }
            }
        }
        ```

        **3. Repository Tests:**
        ```kotlin
        class CharacterRepositoryTest {
            private val api: ApiService = mockk()
            private val mapper: CharacterMapper = CharacterMapper
            private lateinit var repository: CharacterRepositoryImpl

            @Test
            fun `when api returns data then map to domain`() = runTest {
                // Given
                val dto = mockCharacterDto()
                coEvery { api.getCharacters() } returns CharacterListResponse(listOf(dto))

                // When
                val result = repository.getAll()

                // Then
                assertThat(result.isSuccess).isTrue()
                verify { mapper.toDomain(dto) }
            }

            @Test
            fun `when api throws exception then return failure`() = runTest {
                // Given
                coEvery { api.getCharacters() } throws IOException()

                // When
                val result = repository.getAll()

                // Then
                assertThat(result.isFailure).isTrue()
            }
        }
        ```

        **4. Compose UI Tests:**
        ```kotlin
        @Test
        fun whenLoadingThenShowProgressIndicator() {
            composeTestRule.setContent {
                CharacterScreen(
                    uiState = Loading
                )
            }

            composeTestRule
                .onNodeWithTag("loading_indicator")
                .assertIsDisplayed()
        }

        @Test
        fun whenSuccessThenShowList() {
            val characters = listOf(mockCharacter())

            composeTestRule.setContent {
                CharacterScreen(
                    uiState = Success(characters)
                )
            }

            composeTestRule
                .onNodeWithText(characters.first().name)
                .assertIsDisplayed()
        }
        ```

        Herramientas y librerías:
        - **JUnit**: Framework de testing
        - **Mockk**: Mocking para Kotlin
        - **Truth**: Assertions legibles
        - **Turbine**: Testing de Flows
        - **Coroutines Test**: runTest, TestDispatcher
        - **Compose Test**: composeTestRule

        Principios de buenos tests:
        - **AAA Pattern**: Arrange, Act, Assert
        - **FIRST**: Fast, Independent, Repeatable, Self-validating, Timely
        - **Given-When-Then**: Clarity en estructura
        - Nombres descriptivos
        - Un concepto por test
        - No lógica en tests

        Estrategia de Mocking:
        - Mock dependencies externas (APIs, databases)
        - Don't mock domain entities
        - Fake para casos complejos
        - Test doubles apropiados

        Cuando creas tests:
        1. Cubres casos happy path
        2. Cubres casos de error
        3. Cubres edge cases
        4. Verificas comportamiento, no implementación
        5. Tests como documentación

        Colaboras con:
        - Engineer: para hacer código testeable
        - API Agent: para mockear responses
        - Architect: para testability en arquitectura
    """.trimIndent()

    override suspend fun process(message: AgentMessage): AgentResponse {
        val intent = analyzeIntent(message)

        return when (intent) {
            Intent.CREATE -> handleTestCreation(message)
            Intent.TEST -> handleTestExecution(message)
            Intent.REVIEW -> handleTestReview(message)
            Intent.QUERY -> handleTestingQuery(message)
            else -> handleGeneralTestRequest(message)
        }
    }

    private fun handleTestCreation(message: AgentMessage): AgentResponse {
        return collaborationResponse(
            content = """
                # Estrategia de Testing - Jedi Archive

                Implementaré una estrategia completa de testing:

                ## 📊 Distribución de Tests

                **Unit Tests (60%):**
                - Domain layer (UseCases, Entities)
                - ViewModels
                - Mappers
                - Utility functions

                **Integration Tests (30%):**
                - Repositories
                - API integration
                - Data flow completo

                **UI Tests (10%):**
                - Screens principales
                - User flows críticos
                - Accessibility

                ## 🧪 Estructura de Testing

                ```
                :core:domain/src/test/
                ├── usecase/
                │   └── GetCharactersUseCaseTest.kt
                └── model/
                    └── CharacterTest.kt

                :core:data/src/test/
                ├── repository/
                │   └── CharacterRepositoryTest.kt
                └── mapper/
                    └── CharacterMapperTest.kt

                :feature:character-list/src/test/
                └── CharacterListViewModelTest.kt

                :feature:character-list/src/androidTest/
                └── CharacterListScreenTest.kt
                ```

                ## 🛠️ Dependencias de Testing

                ```kotlin
                // Unit Testing
                testImplementation("junit:junit:4.13.2")
                testImplementation("io.mockk:mockk:1.13.8")
                testImplementation("com.google.truth:truth:1.1.5")
                testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")
                testImplementation("app.cash.turbine:turbine:1.0.0")

                // Android Testing
                androidTestImplementation("androidx.test.ext:junit:1.1.5")
                androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
                androidTestImplementation("androidx.compose.ui:ui-test-junit4")
                ```

                ## 📝 Ejemplos de Tests a Crear

                **1. UseCase Test:**
                ```kotlin
                @Test
                fun `when repository succeeds then return characters`() = runTest {
                    // Given
                    val expected = listOf(mockCharacter())
                    coEvery { repository.getAll() } returns Result.success(expected)

                    // When
                    val result = useCase()

                    // Then
                    assertThat(result.getOrNull()).isEqualTo(expected)
                }
                ```

                **2. ViewModel Test:**
                ```kotlin
                @Test
                fun `when initialized then load characters`() = runTest {
                    viewModel.uiState.test {
                        assertThat(awaitItem()).isInstanceOf<Loading>()
                        assertThat(awaitItem()).isInstanceOf<Success>()
                    }
                }
                ```

                **3. Compose UI Test:**
                ```kotlin
                @Test
                fun whenErrorStateThenShowRetryButton() {
                    composeTestRule.setContent {
                        CharacterScreen(uiState = Error("Network error"))
                    }

                    composeTestRule
                        .onNodeWithText("Retry")
                        .assertIsDisplayed()
                        .performClick()
                }
                ```

                ## 🎯 Coverage Goals

                - **Domain**: 90%+ (crítico)
                - **Data**: 80%+
                - **Presentation**: 70%+
                - **UI**: Key flows cubiertos

                ## 🚀 Próximos Pasos

                1. Setup de dependencias de testing
                2. Crear test fixtures y mocks
                3. Implementar unit tests por capa
                4. Integration tests de repositories
                5. UI tests de flows principales

                Colaboraré con Engineer para código testeable y API Agent para mocks.

                ¿Quieres que implemente los tests de alguna capa específica primero?
            """.trimIndent(),
            collaborators = listOf(AgentId.ENGINEER, AgentId.API_INTEGRATION),
            metadata = mapOf(
                "test_types" to listOf("unit", "integration", "ui"),
                "coverage_target" to 80,
                "frameworks" to listOf("junit", "mockk", "turbine", "compose-test")
            )
        )
    }

    private fun handleTestExecution(message: AgentMessage): AgentResponse {
        return successResponse("""
            Para ejecutar tests:

            **Comandos Gradle:**
            ```bash
            # Todos los tests
            ./gradlew test

            # Tests de módulo específico
            ./gradlew :core:domain:test

            # Tests con coverage
            ./gradlew testDebugUnitTest jacocoTestReport

            # UI tests
            ./gradlew connectedAndroidTest
            ```

            **Verificación:**
            ✓ Todos los tests pasan
            ✓ Coverage mínimo alcanzado
            ✓ No flaky tests
            ✓ Performance aceptable

            ¿Qué tests quieres ejecutar?
        """.trimIndent())
    }

    private fun handleTestReview(message: AgentMessage): AgentResponse {
        return successResponse("""
            Revisaré los tests evaluando:

            **Estructura:**
            ✓ AAA pattern (Arrange, Act, Assert)
            ✓ Given-When-Then clarity
            ✓ Test naming descriptivo

            **Coverage:**
            ✓ Happy paths cubiertos
            ✓ Error cases cubiertos
            ✓ Edge cases considerados

            **Calidad:**
            ✓ Tests independientes
            ✓ No flaky tests
            ✓ Mocks apropiados
            ✓ Assertions significativas

            **Performance:**
            ✓ Tests rápidos
            ✓ No sleeps/waits innecesarios

            ¿Qué tests quieres que revise?
        """.trimIndent())
    }

    private fun handleTestingQuery(message: AgentMessage): AgentResponse {
        return successResponse("""
            Como QA Engineer puedo ayudarte con:

            🧪 Estrategia de testing completa
            ✅ Unit tests (JUnit, Mockk)
            🔗 Integration tests
            📱 UI tests (Compose Testing)
            🎭 Mocking y fakes
            📊 Coverage reports
            🚀 CI/CD pipeline
            🐛 Bug reproduction tests
            ⚡ Performance testing
            ♿ Accessibility testing

            ¿Qué aspecto de testing necesitas?
        """.trimIndent())
    }

    private fun handleGeneralTestRequest(message: AgentMessage): AgentResponse {
        return successResponse("""
            He analizado tu petición de testing.

            Para crear los tests apropiados necesito saber:
            - ¿Qué componente/capa quieres testear?
            - ¿Ya existe el código o estamos en TDD?
            - ¿Hay algún comportamiento crítico específico?
            - ¿Qué nivel de coverage buscas?

            Implementaré tests robustos y mantenibles.
        """.trimIndent())
    }
}
