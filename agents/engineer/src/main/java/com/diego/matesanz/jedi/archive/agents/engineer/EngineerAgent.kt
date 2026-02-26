package com.diego.matesanz.jedi.archive.agents.engineer

import com.diego.matesanz.jedi.archive.core.agents.*

/**
 * Senior Android Engineer Agent
 *
 * Responsabilidades:
 * - Implementar features con Jetpack Compose
 * - Desarrollar ViewModels con StateFlow
 * - Crear Composables siguiendo Material Design 3
 * - Implementar navegación
 * - Gestionar estado de UI
 * - Aplicar best practices de Kotlin y Compose
 * - Optimizar rendimiento
 */
class EngineerAgent : BaseAgent() {

    override val id = AgentId.ENGINEER

    override val name = "Senior Android Engineer"

    override val description = """
        Ingeniero senior Android experto en Jetpack Compose y Kotlin.
        Especializado en desarrollo de UI moderna, state management, y optimización de rendimiento.
        Implementa features siguiendo la arquitectura definida.
    """.trimIndent()

    override val role = AgentRole.DEVELOPER

    override val triggerKeywords = listOf(
        "implementar",
        "crear",
        "desarrollar",
        "feature",
        "pantalla",
        "screen",
        "composable",
        "viewmodel",
        "ui",
        "navegación",
        "estado",
        "state",
        "flow",
        "compose",
        "jetpack",
        "código",
        "función",
        "clase",
        "componente",
        "lista",
        "detalle",
        "formulario"
    )

    override val priority = 8

    override val systemPrompt = """
        Eres un Senior Android Engineer con expertise profundo en:
        - Jetpack Compose (Material 3)
        - Kotlin moderno (Coroutines, Flow, sealed classes)
        - State management (StateFlow, State hoisting)
        - ViewModels y lifecycle
        - Performance optimization
        - Best practices

        Principios que sigues:

        **Compose:**
        - Composables pequeños y reutilizables
        - State hoisting apropiado
        - Side effects en lugares correctos (LaunchedEffect, rememberUpdatedState)
        - Composition local para temas
        - Preview annotations para cada composable

        **ViewModels:**
        - StateFlow para UI state
        - Sealed classes para estados (Loading, Success, Error)
        - Single source of truth
        - No lógica de negocio (delegas a UseCases)
        - Coroutines scope apropiado (viewModelScope)

        **Código:**
        - Inmutabilidad por defecto (val, data classes)
        - Extension functions para claridad
        - Null safety riguroso
        - Naming conventions claras
        - No comments, código auto-explicativo

        **Estructura típica de Feature:**
        ```
        feature/
        ├── ui/
        │   ├── FeatureScreen.kt (Composable principal)
        │   ├── components/ (Composables específicos)
        │   └── FeatureViewModel.kt
        ├── FeatureUiState.kt (sealed interface)
        └── FeatureUiEvent.kt (sealed interface)
        ```

        Cuando implementas:
        1. Sigues la arquitectura del Architect
        2. Usas el design system del UI/UX
        3. Integras datos del API Agent
        4. Escribes código testeable para QA

        Colaboras frecuentemente con todos los agentes.
    """.trimIndent()

    override suspend fun process(message: AgentMessage): AgentResponse {
        val intent = analyzeIntent(message)

        return when (intent) {
            Intent.CREATE -> handleFeatureCreation(message)
            Intent.MODIFY -> handleFeatureModification(message)
            Intent.REVIEW -> handleCodeReview(message)
            Intent.QUERY -> handleEngineeringQuery(message)
            else -> handleGeneralEngineeringRequest(message)
        }
    }

    private fun handleFeatureCreation(message: AgentMessage): AgentResponse {
        return collaborationResponse(
            content = """
                # Implementación de Feature

                Para implementar una feature completa necesito:

                ## 1. Arquitectura (del Architect)
                - Estructura de módulo
                - Capa donde implementar
                - Patrones a seguir

                ## 2. Design System (del UI/UX)
                - Componentes disponibles
                - Tokens de diseño
                - Guías de accesibilidad

                ## 3. Datos (del API Agent)
                - Modelos de dominio
                - Repository interfaces
                - Estados de datos

                ## Implementaré:
                ```
                ✓ Screen Composable (UI)
                ✓ ViewModel (state management)
                ✓ UIState (sealed interface)
                ✓ UIEvent (sealed interface)
                ✓ Navigation integration
                ✓ Error handling
                ✓ Loading states
                ✓ Preview functions
                ```

                ## Ejemplo estructura:
                ```kotlin
                // FeatureUiState.kt
                sealed interface FeatureUiState {
                    data object Loading : FeatureUiState
                    data class Success(val data: List<Item>) : FeatureUiState
                    data class Error(val message: String) : FeatureUiState
                }

                // FeatureViewModel.kt
                class FeatureViewModel(
                    private val useCase: GetDataUseCase
                ) : ViewModel() {
                    private val _uiState = MutableStateFlow<FeatureUiState>(Loading)
                    val uiState: StateFlow<FeatureUiState> = _uiState.asStateFlow()

                    init {
                        loadData()
                    }
                }

                // FeatureScreen.kt
                @Composable
                fun FeatureScreen(
                    viewModel: FeatureViewModel = viewModel()
                ) {
                    val uiState by viewModel.uiState.collectAsState()

                    when (val state = uiState) {
                        is Loading -> LoadingContent()
                        is Success -> SuccessContent(state.data)
                        is Error -> ErrorContent(state.message)
                    }
                }
                ```

                ¿Qué feature específica quieres que implemente?
            """.trimIndent(),
            collaborators = listOf(
                AgentId.ARCHITECT,
                AgentId.UIUX,
                AgentId.API_INTEGRATION
            )
        )
    }

    private fun handleFeatureModification(message: AgentMessage): AgentResponse {
        return successResponse("""
            Para modificar código existente:

            1. Leeré el código actual
            2. Entenderé el contexto y arquitectura
            3. Aplicaré el cambio manteniendo patrones
            4. Verificaré que no rompo nada

            ¿Qué archivo o feature necesitas modificar?
        """.trimIndent())
    }

    private fun handleCodeReview(message: AgentMessage): AgentResponse {
        return collaborationResponse(
            content = """
                Realizaré code review enfocándome en:

                **Compose:**
                ✓ Recomposición eficiente
                ✓ State hoisting correcto
                ✓ Side effects apropiados
                ✓ Composables reutilizables

                **Kotlin:**
                ✓ Null safety
                ✓ Immutability
                ✓ Extension functions
                ✓ Scoping functions apropiados

                **Architecture:**
                ✓ Separación de responsabilidades
                ✓ ViewModels sin lógica de negocio
                ✓ StateFlow usage correcto

                **Performance:**
                ✓ remember/rememberSaveable
                ✓ derivedStateOf
                ✓ LazyColumn keys

                ¿Qué código quieres que revise?
            """.trimIndent(),
            collaborators = listOf(AgentId.QA_TESTING)
        )
    }

    private fun handleEngineeringQuery(message: AgentMessage): AgentResponse {
        return successResponse("""
            Como Senior Engineer puedo ayudarte con:

            💻 Implementación de features
            🎨 Jetpack Compose UI
            📊 State management (StateFlow, MutableState)
            🔄 ViewModels y lifecycle
            🚀 Optimización de rendimiento
            📱 Navegación
            🎯 Best practices Kotlin/Compose
            🔧 Refactoring

            ¿Qué necesitas?
        """.trimIndent())
    }

    private fun handleGeneralEngineeringRequest(message: AgentMessage): AgentResponse {
        return successResponse("""
            He analizado tu petición de desarrollo.

            Para ayudarte mejor, dime:
            - ¿Es una nueva feature o modificación?
            - ¿Qué componente/pantalla específicamente?
            - ¿Hay requisitos especiales?

            Implementaré siguiendo la arquitectura definida y usando el design system.
        """.trimIndent())
    }
}
