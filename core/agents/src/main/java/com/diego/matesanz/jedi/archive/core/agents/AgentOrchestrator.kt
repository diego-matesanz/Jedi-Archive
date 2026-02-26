package com.diego.matesanz.jedi.archive.core.agents

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * Orquestador central que gestiona todos los agentes del sistema.
 * Responsable de:
 * - Enrutar mensajes al agente apropiado
 * - Coordinar colaboración entre agentes
 * - Mantener el contexto de la conversación
 * - Gestionar el ciclo de vida de las peticiones
 */
class AgentOrchestrator(
    private val registry: AgentRegistry
) {
    private val conversationMemory = mutableMapOf<String, MutableList<AgentMessage>>()

    /**
     * Procesa una petición del usuario y la enruta al agente apropiado
     */
    suspend fun processUserRequest(userMessage: String): Flow<AgentResponse> = flow {
        val message = AgentMessage(
            content = userMessage,
            sender = null,
            recipient = null,
            messageType = MessageType.USER_REQUEST
        )

        // Guardar en memoria
        addToMemory(message)

        // 1. Analizar y clasificar la petición
        val selectedAgent = selectAgent(message)

        emit(AgentResponse(
            agentId = AgentId.ORCHESTRATOR,
            content = "Enrutando a ${selectedAgent.name}...",
            status = ResponseStatus.IN_PROGRESS
        ))

        // 2. Ejecutar el agente seleccionado
        val response = selectedAgent.process(message)

        // 3. Si requiere colaboración, coordinar con otros agentes
        if (response.requiresCollaboration) {
            val collaborativeResponse = coordinateCollaboration(
                initiator = selectedAgent,
                collaborators = response.collaborationWith,
                context = CollaborationContext(
                    initiator = selectedAgent.id,
                    participants = response.collaborationWith,
                    objective = userMessage,
                    conversationHistory = conversationMemory[message.conversationId] ?: emptyList()
                )
            )
            emit(collaborativeResponse)
        } else {
            emit(response)
        }
    }

    /**
     * Selecciona el agente más apropiado para manejar el mensaje
     */
    private fun selectAgent(message: AgentMessage): Agent {
        val candidates = registry.getAllAgents()
            .filter { it.canHandle(message) }
            .sortedByDescending { it.priority }

        if (candidates.isEmpty()) {
            throw NoSuitableAgentException("No se encontró un agente capaz de manejar: ${message.content}")
        }

        // Si hay múltiples candidatos, usar análisis semántico
        return if (candidates.size > 1) {
            selectBestMatch(message, candidates)
        } else {
            candidates.first()
        }
    }

    /**
     * Selecciona el mejor agente basándose en análisis semántico
     */
    private fun selectBestMatch(message: AgentMessage, candidates: List<Agent>): Agent {
        val content = message.content.lowercase()

        // Scoring basado en keywords
        val scores = candidates.associateWith { agent ->
            agent.triggerKeywords.count { keyword ->
                content.contains(keyword.lowercase())
            }
        }

        return scores.maxByOrNull { it.value }?.key ?: candidates.first()
    }

    /**
     * Coordina la colaboración entre múltiples agentes
     */
    private suspend fun coordinateCollaboration(
        initiator: Agent,
        collaborators: List<AgentId>,
        context: CollaborationContext
    ): AgentResponse {
        val responses = mutableListOf<AgentResponse>()

        for (collaboratorId in collaborators) {
            val collaborator = registry.getAgent(collaboratorId)
                ?: continue

            val response = initiator.collaborate(collaborator, context)
            responses.add(response)
        }

        // Combinar respuestas
        return combineResponses(initiator.id, responses)
    }

    /**
     * Combina múltiples respuestas en una respuesta unificada
     */
    private fun combineResponses(initiatorId: AgentId, responses: List<AgentResponse>): AgentResponse {
        val combinedContent = responses.joinToString("\n\n") {
            "**${it.agentId.name}**: ${it.content}"
        }

        val overallStatus = when {
            responses.all { it.status == ResponseStatus.SUCCESS } -> ResponseStatus.SUCCESS
            responses.any { it.status == ResponseStatus.ERROR } -> ResponseStatus.ERROR
            else -> ResponseStatus.IN_PROGRESS
        }

        return AgentResponse(
            agentId = initiatorId,
            content = combinedContent,
            status = overallStatus
        )
    }

    /**
     * Añade un mensaje al historial de conversación
     */
    private fun addToMemory(message: AgentMessage) {
        conversationMemory
            .getOrPut(message.conversationId) { mutableListOf() }
            .add(message)
    }

    /**
     * Obtiene el historial de una conversación
     */
    fun getConversationHistory(conversationId: String): List<AgentMessage> {
        return conversationMemory[conversationId] ?: emptyList()
    }

    /**
     * Limpia la memoria de conversaciones antiguas
     */
    fun clearOldConversations(olderThanMillis: Long = 3600000) { // 1 hora
        val now = System.currentTimeMillis()
        conversationMemory.entries.removeIf { (_, messages) ->
            messages.lastOrNull()?.timestamp?.let { now - it > olderThanMillis } ?: true
        }
    }
}

class NoSuitableAgentException(message: String) : Exception(message)
