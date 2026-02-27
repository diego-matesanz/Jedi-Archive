package com.diego.matesanz.jedi.archive.core.domain.usecase

import com.diego.matesanz.jedi.archive.core.domain.model.*
import com.diego.matesanz.jedi.archive.core.domain.repository.SwapiRepository

/**
 * Use Case para obtener el detalle de una entidad
 */
class GetEntityDetailUseCase(
    private val repository: SwapiRepository
) {
    /**
     * Obtiene el detalle de una entidad según su tipo e ID
     */
    suspend operator fun invoke(
        entityType: EntityType,
        id: String
    ): Result<SwapiEntity> {
        return when (entityType) {
            EntityType.PERSON -> repository.getPerson(id)
            EntityType.PLANET -> repository.getPlanet(id)
            EntityType.SPECIES -> repository.getSingleSpecies(id)
            EntityType.STARSHIP -> repository.getStarship(id)
            EntityType.VEHICLE -> repository.getVehicle(id)
            EntityType.FILM -> repository.getFilm(id)
        }
    }
}
