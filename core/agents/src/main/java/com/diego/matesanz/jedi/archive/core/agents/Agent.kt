package com.diego.matesanz.jedi.archive.core.agents

/**
 * Representa un agente en el sistema multi-agente.
 * Cada agente tiene un rol específico y capacidades definidas.
 */
interface Agent {
    /**
     * Identificador único del agente
     */
    val id: AgentId

    /**
     * Nombre descriptivo del agente
     */
    val name: String

    /**
     * Descripción detallada de las capacidades y responsabilidades del agente
     */
    val description: String

    /**
     * Rol del agente en el sistema
     */
    val role: AgentRole

    /**
     * Keywords que activan este agente
     */
    val triggerKeywords: List<String>

    /**
     * Nivel de prioridad cuando múltiples agentes pueden manejar una petición
     */
    val priority: Int

    /**
     * System prompt que define la personalidad y comportamiento del agente
     */
    val systemPrompt: String

    /**
     * Procesa un mensaje y retorna una respuesta
     */
    suspend fun process(message: AgentMessage): AgentResponse

    /**
     * Determina si este agente puede manejar el mensaje dado
     */
    fun canHandle(message: AgentMessage): Boolean

    /**
     * Colabora con otro agente
     */
    suspend fun collaborate(otherAgent: Agent, context: CollaborationContext): AgentResponse
}

/**
 * Identificadores únicos para cada agente
 */
enum class AgentId {
    ARCHITECT,
    ENGINEER,
    UIUX,
    API_INTEGRATION,
    QA_TESTING,
    ORCHESTRATOR
}

/**
 * Roles que pueden tener los agentes
 */
enum class AgentRole {
    ARCHITECT,
    DEVELOPER,
    DESIGNER,
    INTEGRATOR,
    TESTER,
    COORDINATOR
}
