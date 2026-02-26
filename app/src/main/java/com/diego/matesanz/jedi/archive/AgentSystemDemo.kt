package com.diego.matesanz.jedi.archive

import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.runBlocking

/**
 * Demo del sistema de agentes.
 * Muestra cómo el orquestador enruta automáticamente mensajes a los agentes apropiados.
 */
object AgentSystemDemo {

    @JvmStatic
    fun main(args: Array<String>) = runBlocking {
        println("=== Jedi Archive - Sistema Multi-Agente ===\n")

        // Inicializar sistema
        val orchestrator = AgentSystemInitializer.initialize()
        val stats = AgentSystemInitializer.getSystemStats()

        println("Sistema inicializado con ${stats.totalAgents} agentes:")
        stats.agentsByRole.forEach { (role, count) ->
            println("  - $role: $count agente(s)")
        }
        println()

        // Ejemplos de peticiones del usuario
        val userRequests = listOf(
            "Necesito definir la arquitectura del proyecto",
            "Quiero implementar una pantalla de lista de personajes con Compose",
            "Define un design system para la aplicación",
            "Integra la API de Star Wars (SWAPI)",
            "Necesito tests para el ViewModel"
        )

        userRequests.forEachIndexed { index, request ->
            println("--- Petición ${index + 1} ---")
            println("Usuario: $request")
            println()

            orchestrator.processUserRequest(request).collect { response ->
                println("${response.agentId.name}:")
                println(response.content)
                println()

                if (response.requiresCollaboration) {
                    println("⚡ Requiere colaboración con: ${response.collaborationWith.joinToString { it.name }}")
                    println()
                }
            }

            println("${"=".repeat(60)}\n")
        }

        println("Demo completada. El sistema puede enrutar inteligentemente peticiones a los agentes apropiados.")
    }
}

/**
 * Ejemplo de integración en una aplicación Android real
 */
class AgentSystemExample {

    private val orchestrator by lazy {
        AgentSystemInitializer.initialize()
    }

    /**
     * Ejemplo: El usuario pide algo en la app
     */
    suspend fun handleUserRequest(userMessage: String) {
        orchestrator.processUserRequest(userMessage).collect { response ->
            when (response.status) {
                com.diego.matesanz.jedi.archive.core.agents.ResponseStatus.SUCCESS -> {
                    // Mostrar respuesta exitosa al usuario
                    println("✓ ${response.content}")
                }
                com.diego.matesanz.jedi.archive.core.agents.ResponseStatus.ERROR -> {
                    // Mostrar error al usuario
                    println("✗ Error: ${response.content}")
                }
                com.diego.matesanz.jedi.archive.core.agents.ResponseStatus.IN_PROGRESS -> {
                    // Mostrar loading/progress
                    println("⏳ ${response.content}")
                }
                com.diego.matesanz.jedi.archive.core.agents.ResponseStatus.NEEDS_COLLABORATION -> {
                    // El orquestrador manejará la colaboración automáticamente
                    println("🤝 Coordinando con otros agentes...")
                }
                com.diego.matesanz.jedi.archive.core.agents.ResponseStatus.NEEDS_USER_INPUT -> {
                    // Solicitar más información al usuario
                    println("❓ ${response.content}")
                }
                com.diego.matesanz.jedi.archive.core.agents.ResponseStatus.DELEGATED -> {
                    // Delegado a otro agente
                    println("➡️ ${response.content}")
                }
            }
        }
    }

    /**
     * Ejemplo: Consultar historial de una conversación
     */
    fun getConversationHistory(conversationId: String) {
        val history = orchestrator.getConversationHistory(conversationId)
        history.forEach { message ->
            println("[${message.sender?.name ?: "User"}] ${message.content}")
        }
    }

    /**
     * Ejemplo: Limpiar conversaciones antiguas (housekeeping)
     */
    fun cleanupOldConversations() {
        orchestrator.clearOldConversations(olderThanMillis = 3600000) // 1 hora
    }
}
