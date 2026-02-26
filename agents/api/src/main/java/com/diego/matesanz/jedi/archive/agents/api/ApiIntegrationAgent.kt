package com.diego.matesanz.jedi.archive.agents.api

import com.diego.matesanz.jedi.archive.core.agents.*

/**
 * API Integration Engineer Agent
 *
 * Responsabilidades:
 * - Integración con APIs públicas (REST)
 * - Configuración de Retrofit/Ktor
 * - Definición de DTOs (Data Transfer Objects)
 * - Mappers (DTO → Domain Entity)
 * - Manejo de estados de red (Success, Error, Loading)
 * - Retry logic y error handling
 * - Serialización (Gson/Kotlinx.serialization)
 * - Repository implementations
 */
class ApiIntegrationAgent : BaseAgent() {

    override val id = AgentId.API_INTEGRATION

    override val name = "API Integration Engineer"

    override val description = """
        Ingeniero especializado en integración de APIs REST.
        Experto en Retrofit, Ktor, serialización, manejo de errores de red y Repository pattern.
        Gestiona toda la capa de datos remotos.
    """.trimIndent()

    override val role = AgentRole.INTEGRATOR

    override val triggerKeywords = listOf(
        "api",
        "retrofit",
        "ktor",
        "network",
        "http",
        "request",
        "response",
        "dto",
        "mapper",
        "serialización",
        "json",
        "endpoint",
        "repository",
        "data source",
        "remote",
        "fetch",
        "llamada",
        "consumir",
        "integrar"
    )

    override val priority = 9

    override val systemPrompt = """
        Eres un API Integration Engineer experto en:
        - Retrofit 2 (preferido en Android)
        - Ktor Client (alternativa moderna)
        - Kotlinx.serialization / Gson
        - OkHttp interceptors
        - Error handling
        - Repository pattern
        - Data mapping

        Arquitectura de Data Layer:

        ```
        :core:network
        ├── api/
        │   └── ApiService.kt (Retrofit interface)
        ├── dto/
        │   └── ResponseDto.kt (Data Transfer Objects)
        ├── NetworkModule.kt (DI setup)
        └── interceptors/
            └── LoggingInterceptor.kt

        :core:data
        ├── repository/
        │   └── RepositoryImpl.kt (implementa interface de domain)
        ├── mapper/
        │   └── DtoMapper.kt (DTO → Entity)
        └── source/
            └── RemoteDataSource.kt
        ```

        Patrones que implementas:

        **1. DTOs separados de Entities:**
        ```kotlin
        // DTO (red)
        @Serializable
        data class CharacterDto(
            val name: String,
            val birth_year: String  // snake_case de API
        )

        // Entity (domain)
        data class Character(
            val name: String,
            val birthYear: String  // camelCase del dominio
        )
        ```

        **2. Repository Pattern:**
        ```kotlin
        interface CharacterRepository {
            suspend fun getCharacters(): Result<List<Character>>
        }

        class CharacterRepositoryImpl(
            private val remoteDataSource: RemoteDataSource,
            private val mapper: CharacterMapper
        ) : CharacterRepository {
            override suspend fun getCharacters() = runCatching {
                remoteDataSource.getCharacters()
                    .map { mapper.toDomain(it) }
            }
        }
        ```

        **3. Result wrapping:**
        - Usas `Result<T>` de Kotlin
        - Wrappeas exceptions en Result.failure
        - Estados: Loading, Success, Error

        **4. Retrofit setup:**
        ```kotlin
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.example.com/")
            .addConverterFactory(Json.asConverterFactory())
            .client(okHttpClient)
            .build()
        ```

        **5. Error handling:**
        - HTTP errors (404, 500, etc.)
        - Network errors (timeout, no internet)
        - Parsing errors
        - Mensajes user-friendly

        APIs populares que conoces:
        - Star Wars API (SWAPI)
        - Pokemon API (PokeAPI)
        - Rick & Morty API
        - JSONPlaceholder
        - The Movie DB (TMDB)

        Cuando integras una API:
        1. Analizas la documentación
        2. Defines DTOs basados en responses
        3. Creas interface Retrofit
        4. Implementas Repository
        5. Creas Mappers
        6. Manejas todos los casos de error

        Colaboras con:
        - Architect: para definir estructura de datos
        - Engineer: para proveer datos a UI
        - QA: para mockear responses en tests
    """.trimIndent()

    override suspend fun process(message: AgentMessage): AgentResponse {
        val intent = analyzeIntent(message)

        return when (intent) {
            Intent.CREATE -> handleApiIntegration(message)
            Intent.MODIFY -> handleApiModification(message)
            Intent.REVIEW -> handleApiReview(message)
            Intent.QUERY -> handleApiQuery(message)
            else -> handleGeneralApiRequest(message)
        }
    }

    private fun handleApiIntegration(message: AgentMessage): AgentResponse {
        return collaborationResponse(
            content = """
                # Integración de API

                Para integrar una API pública necesito:

                ## 1. Información de la API
                ¿Qué API vas a usar? Ejemplos:
                - **SWAPI** (Star Wars): https://swapi.dev/
                - **PokeAPI**: https://pokeapi.co/
                - **Rick & Morty API**: https://rickandmortyapi.com/
                - Otra (dime cuál)

                ## 2. Lo que implementaré:

                **:core:network module:**
                ```kotlin
                // ApiService.kt
                interface ApiService {
                    @GET("people/")
                    suspend fun getCharacters(): CharacterListResponse

                    @GET("people/{id}/")
                    suspend fun getCharacter(@Path("id") id: Int): CharacterDto
                }

                // NetworkModule.kt
                object NetworkModule {
                    fun provideRetrofit(): Retrofit
                    fun provideApiService(retrofit: Retrofit): ApiService
                }
                ```

                **:core:data module:**
                ```kotlin
                // CharacterRepository.kt
                interface CharacterRepository {
                    suspend fun getAll(): Result<List<Character>>
                    suspend fun getById(id: Int): Result<Character>
                }

                // CharacterRepositoryImpl.kt
                class CharacterRepositoryImpl(
                    private val api: ApiService,
                    private val mapper: CharacterMapper
                ) : CharacterRepository {
                    override suspend fun getAll() = runCatching {
                        api.getCharacters()
                            .results
                            .map { mapper.toDomain(it) }
                    }
                }

                // CharacterMapper.kt
                object CharacterMapper {
                    fun toDomain(dto: CharacterDto): Character {
                        return Character(
                            id = extractIdFromUrl(dto.url),
                            name = dto.name,
                            birthYear = dto.birth_year
                        )
                    }
                }
                ```

                ## 3. Estados de red:
                ```kotlin
                sealed interface NetworkResult<out T> {
                    data class Success<T>(val data: T) : NetworkResult<T>
                    data class Error(
                        val exception: Throwable,
                        val message: String
                    ) : NetworkResult<Nothing>
                    data object Loading : NetworkResult<Nothing>
                }
                ```

                ## 4. Dependencias necesarias:
                ```kotlin
                // Retrofit
                implementation("com.squareup.retrofit2:retrofit:2.9.0")
                implementation("com.squareup.retrofit2:converter-kotlinx-serialization:2.9.0")

                // Kotlinx Serialization
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.0")

                // OkHttp (logging)
                implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")
                ```

                Colaboraré con Architect para la estructura y Engineer para conectar con UI.

                ¿Qué API específica quieres integrar?
            """.trimIndent(),
            collaborators = listOf(AgentId.ARCHITECT, AgentId.ENGINEER),
            metadata = mapOf(
                "layer" to "data",
                "modules" to listOf("core:network", "core:data"),
                "patterns" to listOf("repository", "mapper", "result_wrapping")
            )
        )
    }

    private fun handleApiModification(message: AgentMessage): AgentResponse {
        return successResponse("""
            Para modificar la integración de API:

            1. Identificaré qué cambiar:
               - ¿Nuevos endpoints?
               - ¿Modificar DTOs?
               - ¿Cambiar manejo de errores?
               - ¿Actualizar mappers?

            2. Actualizaré manteniendo:
               - Interfaces del domain
               - Backwards compatibility
               - Tests existentes

            ¿Qué necesitas modificar específicamente?
        """.trimIndent())
    }

    private fun handleApiReview(message: AgentMessage): AgentResponse {
        return collaborationResponse(
            content = """
                Revisaré la integración de API evaluando:

                **Architecture:**
                ✓ Separación DTO vs Entity
                ✓ Repository pattern correcto
                ✓ Mapper implementations

                **Error Handling:**
                ✓ Try-catch apropiado
                ✓ Result wrapping
                ✓ User-friendly messages
                ✓ Timeout handling

                **Performance:**
                ✓ Efficient parsing
                ✓ Minimal mappings
                ✓ Proper coroutines usage

                **Best Practices:**
                ✓ Retrofit configuration
                ✓ Interceptors (logging, auth)
                ✓ Serialization setup
                ✓ Base URL management

                Colaboraré con QA para verificar casos edge.

                ¿Qué código de API quieres que revise?
            """.trimIndent(),
            collaborators = listOf(AgentId.QA_TESTING)
        )
    }

    private fun handleApiQuery(message: AgentMessage): AgentResponse {
        return successResponse("""
            Como API Integration Engineer puedo ayudarte con:

            🌐 Integración de APIs REST
            🔧 Setup de Retrofit/Ktor
            📦 DTOs y serialización
            🔄 Mappers (DTO → Entity)
            🎯 Repository pattern
            ⚠️ Error handling robusto
            🔁 Retry logic
            📊 Estado de red (Loading/Success/Error)
            🔐 Authentication (si requiere)
            🧪 Mock responses para testing

            ¿Qué necesitas?
        """.trimIndent())
    }

    private fun handleGeneralApiRequest(message: AgentMessage): AgentResponse {
        return successResponse("""
            He analizado tu petición de API.

            Para ayudarte necesito saber:
            - ¿Qué API pública vas a consumir?
            - ¿Qué endpoints necesitas? (listar, detalle, buscar, etc.)
            - ¿La API requiere autenticación?
            - ¿Hay límites de rate limiting?

            Una vez definido, implementaré toda la capa de datos.
        """.trimIndent())
    }
}
