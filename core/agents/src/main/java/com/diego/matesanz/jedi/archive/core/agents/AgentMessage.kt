package com.diego.matesanz.jedi.archive.core.agents

import java.util.UUID

/**
 * Representa un mensaje en el sistema de agentes
 */
data class AgentMessage(
    val id: String = UUID.randomUUID().toString(),
    val content: String,
    val sender: AgentId?,
    val recipient: AgentId?,
    val messageType: MessageType,
    val context: Map<String, Any> = emptyMap(),
    val timestamp: Long = System.currentTimeMillis(),
    val conversationId: String = UUID.randomUUID().toString()
)

/**
 * Tipos de mensajes que pueden intercambiarse
 */
enum class MessageType {
    USER_REQUEST,           // Petición inicial del usuario
    AGENT_REQUEST,          // Petición de un agente a otro
    AGENT_RESPONSE,         // Respuesta de un agente
    COLLABORATION_REQUEST,  // Solicitud de colaboración
    STATUS_UPDATE,          // Actualización de estado
    ERROR,                  // Mensaje de error
    COMPLETION             // Tarea completada
}

/**
 * Respuesta de un agente
 */
data class AgentResponse(
    val agentId: AgentId,
    val content: String,
    val status: ResponseStatus,
    val requiresCollaboration: Boolean = false,
    val collaborationWith: List<AgentId> = emptyList(),
    val metadata: Map<String, Any> = emptyMap(),
    val timestamp: Long = System.currentTimeMillis()
)

/**
 * Estado de la respuesta
 */
enum class ResponseStatus {
    SUCCESS,
    NEEDS_COLLABORATION,
    NEEDS_USER_INPUT,
    IN_PROGRESS,
    ERROR,
    DELEGATED
}

/**
 * Contexto de colaboración entre agentes
 */
data class CollaborationContext(
    val initiator: AgentId,
    val participants: List<AgentId>,
    val objective: String,
    val sharedData: Map<String, Any> = emptyMap(),
    val conversationHistory: List<AgentMessage> = emptyList()
)
