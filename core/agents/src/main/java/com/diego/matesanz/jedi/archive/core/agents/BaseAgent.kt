package com.diego.matesanz.jedi.archive.core.agents

/**
 * Implementación base abstracta de un agente.
 * Proporciona funcionalidad común para todos los agentes.
 */
abstract class BaseAgent : Agent {

    override fun canHandle(message: AgentMessage): Boolean {
        val content = message.content.lowercase()

        // Verificar keywords
        val hasKeyword = triggerKeywords.any { keyword ->
            content.contains(keyword.lowercase())
        }

        // Verificar si está dirigido específicamente a este agente
        val isDirectlyAddressed = message.recipient == id

        return hasKeyword || isDirectlyAddressed
    }

    override suspend fun collaborate(
        otherAgent: Agent,
        context: CollaborationContext
    ): AgentResponse {
        // Delegar al otro agente con contexto enriquecido
        val enrichedContext = enrichContext(context)

        val collaborationMessage = AgentMessage(
            content = context.objective,
            sender = id,
            recipient = otherAgent.id,
            messageType = MessageType.COLLABORATION_REQUEST,
            context = enrichedContext
        )

        return otherAgent.process(collaborationMessage)
    }

    /**
     * Enriquece el contexto de colaboración con información específica del agente
     */
    protected open fun enrichContext(context: CollaborationContext): Map<String, Any> {
        return context.sharedData + mapOf(
            "collaborator" to id.name,
            "role" to role.name
        )
    }

    /**
     * Analiza la intención del mensaje
     */
    protected fun analyzeIntent(message: AgentMessage): Intent {
        val content = message.content.lowercase()

        return when {
            content.contains("crear") || content.contains("generar") || content.contains("implementar") -> Intent.CREATE
            content.contains("modificar") || content.contains("actualizar") || content.contains("cambiar") -> Intent.MODIFY
            content.contains("revisar") || content.contains("analizar") || content.contains("validar") -> Intent.REVIEW
            content.contains("diseñar") || content.contains("diseño") -> Intent.DESIGN
            content.contains("test") || content.contains("probar") -> Intent.TEST
            content.contains("documentar") || content.contains("documentación") -> Intent.DOCUMENT
            else -> Intent.QUERY
        }
    }

    /**
     * Crea una respuesta de error estándar
     */
    protected fun errorResponse(error: String): AgentResponse {
        return AgentResponse(
            agentId = id,
            content = "Error: $error",
            status = ResponseStatus.ERROR
        )
    }

    /**
     * Crea una respuesta exitosa
     */
    protected fun successResponse(content: String, metadata: Map<String, Any> = emptyMap()): AgentResponse {
        return AgentResponse(
            agentId = id,
            content = content,
            status = ResponseStatus.SUCCESS,
            metadata = metadata
        )
    }

    /**
     * Crea una respuesta que requiere colaboración
     */
    protected fun collaborationResponse(
        content: String,
        collaborators: List<AgentId>,
        metadata: Map<String, Any> = emptyMap()
    ): AgentResponse {
        return AgentResponse(
            agentId = id,
            content = content,
            status = ResponseStatus.NEEDS_COLLABORATION,
            requiresCollaboration = true,
            collaborationWith = collaborators,
            metadata = metadata
        )
    }
}

/**
 * Intenciones que puede tener un mensaje
 */
enum class Intent {
    CREATE,
    MODIFY,
    REVIEW,
    DESIGN,
    TEST,
    DOCUMENT,
    QUERY
}
