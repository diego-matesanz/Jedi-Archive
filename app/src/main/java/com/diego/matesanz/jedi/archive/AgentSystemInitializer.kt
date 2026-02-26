package com.diego.matesanz.jedi.archive

import com.diego.matesanz.jedi.archive.agents.api.ApiIntegrationAgent
import com.diego.matesanz.jedi.archive.agents.architect.ArchitectAgent
import com.diego.matesanz.jedi.archive.agents.engineer.EngineerAgent
import com.diego.matesanz.jedi.archive.agents.qa.QaTestingAgent
import com.diego.matesanz.jedi.archive.agents.uiux.UiUxAgent
import com.diego.matesanz.jedi.archive.core.agents.AgentOrchestrator
import com.diego.matesanz.jedi.archive.core.agents.AgentRegistry

/**
 * Inicializa el sistema de agentes y el orquestador.
 * Registra todos los agentes especializados disponibles.
 */
object AgentSystemInitializer {

    private val registry = AgentRegistry()
    private val orchestrator = AgentOrchestrator(registry)

    /**
     * Inicializa el sistema completo de agentes
     */
    fun initialize(): AgentOrchestrator {
        // Registrar todos los agentes
        registry.register(ArchitectAgent())
        registry.register(EngineerAgent())
        registry.register(UiUxAgent())
        registry.register(ApiIntegrationAgent())
        registry.register(QaTestingAgent())

        return orchestrator
    }

    /**
     * Obtiene el orquestador (asume que ya fue inicializado)
     */
    fun getOrchestrator(): AgentOrchestrator = orchestrator

    /**
     * Obtiene el registro de agentes
     */
    fun getRegistry(): AgentRegistry = registry

    /**
     * Obtiene estadísticas del sistema
     */
    fun getSystemStats() = registry.getStats()
}
