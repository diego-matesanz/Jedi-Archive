package com.diego.matesanz.jedi.archive.core.agents

/**
 * Registro centralizado de todos los agentes disponibles en el sistema
 */
class AgentRegistry {
    private val agents = mutableMapOf<AgentId, Agent>()

    /**
     * Registra un agente en el sistema
     */
    fun register(agent: Agent) {
        agents[agent.id] = agent
    }

    /**
     * Obtiene un agente por su ID
     */
    fun getAgent(id: AgentId): Agent? {
        return agents[id]
    }

    /**
     * Obtiene todos los agentes registrados
     */
    fun getAllAgents(): List<Agent> {
        return agents.values.toList()
    }

    /**
     * Obtiene agentes por rol
     */
    fun getAgentsByRole(role: AgentRole): List<Agent> {
        return agents.values.filter { it.role == role }
    }

    /**
     * Desregistra un agente
     */
    fun unregister(id: AgentId) {
        agents.remove(id)
    }

    /**
     * Verifica si un agente está registrado
     */
    fun isRegistered(id: AgentId): Boolean {
        return agents.containsKey(id)
    }

    /**
     * Limpia todos los agentes registrados
     */
    fun clear() {
        agents.clear()
    }

    /**
     * Obtiene estadísticas del registro
     */
    fun getStats(): RegistryStats {
        return RegistryStats(
            totalAgents = agents.size,
            agentsByRole = agents.values.groupBy { it.role }.mapValues { it.value.size }
        )
    }
}

data class RegistryStats(
    val totalAgents: Int,
    val agentsByRole: Map<AgentRole, Int>
)
