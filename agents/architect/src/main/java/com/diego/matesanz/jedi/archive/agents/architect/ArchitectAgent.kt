package com.diego.matesanz.jedi.archive.agents.architect

import com.diego.matesanz.jedi.archive.core.agents.*

/**
 * Senior Android Architect Agent
 *
 * Responsabilidades:
 * - Definir arquitectura limpia (Clean Architecture)
 * - Diseñar estructura multimódulo por capas
 * - Establecer patrones de diseño (MVVM, Repository, UseCase)
 * - Definir flujo de datos y dependencias
 * - Garantizar escalabilidad y mantenibilidad
 * - Establecer principios SOLID
 */
class ArchitectAgent : BaseAgent() {

    override val id = AgentId.ARCHITECT

    override val name = "Senior Android Architect"

    override val description = """
        Arquitecto senior especializado en definir arquitecturas limpias y escalables para Android.
        Experto en Clean Architecture, multimódulo, SOLID, patrones de diseño y mejores prácticas.
        Define la estructura de capas: Presentation, Domain, Data.
    """.trimIndent()

    override val role = AgentRole.ARCHITECT

    override val triggerKeywords = listOf(
        "arquitectura",
        "estructura",
        "módulo",
        "capa",
        "clean architecture",
        "diseño",
        "patrón",
        "dependency",
        "organización",
        "separación",
        "mvvm",
        "repository",
        "usecase",
        "domain",
        "presentation",
        "data",
        "di",
        "inyección"
    )

    override val priority = 10 // Alta prioridad para decisiones arquitectónicas

    override val systemPrompt = """
        Eres un Senior Android Architect con más de 10 años de experiencia.

        Tu filosofía:
        - Clean Architecture es fundamental: separación clara de capas
        - Multimódulo por capa para escalabilidad y tiempos de compilación
        - SOLID principles en cada decisión
        - El Domain layer es puro Kotlin sin dependencias de Android
        - Single source of truth para el estado
        - Unidirectional data flow
        - Testabilidad es prioritaria

        Estructura multimódulo que implementas:

        :app (Aplicación principal)
        :feature:featureName (Módulos de features con UI)
        :core:ui (Componentes UI compartidos)
        :core:designsystem (Design System)
        :core:domain (Casos de uso, entidades)
        :core:data (Repositorios, data sources)
        :core:network (API clients)
        :core:common (Utilidades compartidas)

        Capas:
        1. Presentation: ViewModels, UI States, Compose screens
        2. Domain: Use Cases, Entities, Repository interfaces
        3. Data: Repository implementations, DTOs, Mappers, Data Sources

        Cuando defines arquitectura:
        - Siempre explicas el "por qué" de cada decisión
        - Consideras trade-offs
        - Priorizas simplicidad sobre complejidad innecesaria
        - Documentas patrones y convenciones

        Colaboras con:
        - Engineer: para implementar la arquitectura definida
        - API Agent: para definir contratos de datos
        - QA Agent: para garantizar testabilidad
    """.trimIndent()

    override suspend fun process(message: AgentMessage): AgentResponse {
        val intent = analyzeIntent(message)

        return when (intent) {
            Intent.CREATE -> handleArchitectureCreation(message)
            Intent.DESIGN -> handleArchitectureDesign(message)
            Intent.REVIEW -> handleArchitectureReview(message)
            Intent.MODIFY -> handleArchitectureModification(message)
            Intent.QUERY -> handleArchitectureQuery(message)
            else -> handleGeneralArchitectureRequest(message)
        }
    }

    private fun handleArchitectureCreation(message: AgentMessage): AgentResponse {
        val architectureProposal = """
            # Propuesta de Arquitectura - Jedi Archive

            ## 🏗️ Arquitectura: Clean Architecture + Multimódulo

            ### Estructura de Módulos:

            ```
            :app
            ├─ :feature:character-list
            ├─ :feature:character-detail
            ├─ :feature:search
            │
            ├─ :core:ui (Componentes Compose compartidos)
            ├─ :core:designsystem (Theme, Colors, Typography)
            ├─ :core:domain (Use Cases, Entities)
            ├─ :core:data (Repositories, Mappers)
            ├─ :core:network (Retrofit, API)
            └─ :core:common (Utils, Extensions)
            ```

            ### Capas (Clean Architecture):

            **1. Presentation Layer** (en módulos :feature)
            - Screens (Composables)
            - ViewModels
            - UI States (sealed classes/interfaces)
            - UI Events

            **2. Domain Layer** (:core:domain)
            - Use Cases (interactors)
            - Entities (modelos de dominio)
            - Repository Interfaces
            - Reglas de negocio

            **3. Data Layer** (:core:data + :core:network)
            - Repository Implementations
            - DTOs (Data Transfer Objects)
            - Mappers (DTO → Entity)
            - Remote Data Sources

            ### Flujo de Datos:
            ```
            UI → ViewModel → UseCase → Repository → DataSource → API
                     ↓           ↓          ↓
                  UIState ← Entity ←── DTO (mapped)
            ```

            ### Dependencias entre Módulos:
            - :feature → :core:ui, :core:domain
            - :core:ui → :core:designsystem
            - :core:data → :core:domain, :core:network
            - :core:network → (ninguna dependencia interna)

            ### Patrones Implementados:
            - **MVVM**: ViewModels + StateFlow para UI
            - **Repository Pattern**: Abstracción de fuentes de datos
            - **Use Case Pattern**: Lógica de negocio encapsulada
            - **Dependency Injection**: Hilt/Koin (a definir)
            - **Single Source of Truth**: Repository es la única fuente

            ### Ventajas:
            ✅ Separación clara de responsabilidades
            ✅ Testabilidad máxima (Domain sin Android)
            ✅ Escalabilidad (nuevas features = nuevos módulos)
            ✅ Compilación paralela (módulos independientes)
            ✅ Reutilización de código (:core modules)
            ✅ Mantenibilidad a largo plazo

            ### Próximos Pasos:
            1. Crear estructura de módulos
            2. Definir dependencias Gradle
            3. Implementar capa de datos (con API Agent)
            4. Crear design system (con UI/UX Agent)
            5. Implementar features (con Engineer Agent)

            ¿Quieres que proceda con la creación de esta estructura?
        """.trimIndent()

        return collaborationResponse(
            content = architectureProposal,
            collaborators = listOf(AgentId.ENGINEER, AgentId.API_INTEGRATION, AgentId.UIUX),
            metadata = mapOf(
                "architecture_type" to "clean_multimodule",
                "layers" to listOf("presentation", "domain", "data"),
                "requires_setup" to true
            )
        )
    }

    private fun handleArchitectureDesign(message: AgentMessage): AgentResponse {
        return successResponse("""
            Para diseñar una arquitectura óptima necesito información:

            1. ¿Qué API pública vas a consumir? (Star Wars API, Pokemon API, etc.)
            2. ¿Qué features principales tendrá la app? (lista, detalle, búsqueda, filtros)
            3. ¿Planeas soporte offline o solo online?
            4. ¿Usarás navegación simple o compleja (bottom nav, drawer, etc.)?

            Con esta info definiré la arquitectura exacta más adecuada.
        """.trimIndent())
    }

    private fun handleArchitectureReview(message: AgentMessage): AgentResponse {
        return successResponse("""
            Para revisar la arquitectura actual analizaré:

            ✓ Separación de capas (Presentation, Domain, Data)
            ✓ Acoplamiento entre módulos
            ✓ Flujo de dependencias (hacia el Domain)
            ✓ Testabilidad
            ✓ Patrones implementados
            ✓ Posibles code smells arquitectónicos

            ¿Qué aspecto específico quieres que revise?
        """.trimIndent())
    }

    private fun handleArchitectureModification(message: AgentMessage): AgentResponse {
        return collaborationResponse(
            content = """
                Modificar la arquitectura es una decisión importante.

                Consideraciones:
                - Impacto en módulos existentes
                - Migración de código
                - Testing de regresión
                - Comunicación al equipo

                Necesitaré colaboración del Engineer y QA Agent para ejecutar cambios.

                ¿Qué modificación específica necesitas?
            """.trimIndent(),
            collaborators = listOf(AgentId.ENGINEER, AgentId.QA_TESTING)
        )
    }

    private fun handleArchitectureQuery(message: AgentMessage): AgentResponse {
        return successResponse("""
            Como arquitecto puedo ayudarte con:

            📐 Diseño de arquitectura inicial
            🏗️ Estructura de módulos
            🔄 Patrones de diseño (MVVM, MVI, Repository, UseCase)
            📦 Organización de capas
            🔗 Gestión de dependencias
            ✅ Principios SOLID
            🧪 Estrategias de testing
            📚 Mejores prácticas Android

            ¿Qué necesitas saber?
        """.trimIndent())
    }

    private fun handleGeneralArchitectureRequest(message: AgentMessage): AgentResponse {
        return successResponse("""
            He analizado tu petición arquitectónica.

            Para darte la mejor respuesta necesito más contexto:
            - ¿Estás empezando el proyecto o modificando uno existente?
            - ¿Qué problema arquitectónico estás enfrentando?
            - ¿Hay restricciones técnicas o de equipo?

            Puedo definir una arquitectura completa desde cero o ayudarte con aspectos específicos.
        """.trimIndent())
    }
}
