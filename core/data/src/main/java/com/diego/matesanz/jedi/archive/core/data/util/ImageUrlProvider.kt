package com.diego.matesanz.jedi.archive.core.data.util

import com.diego.matesanz.jedi.archive.core.domain.model.EntityType

/**
 * Provee URLs de imágenes para entidades de Star Wars
 *
 * Usa mapeos estáticos generados desde Wookieepedia MediaWiki API.
 * Las imágenes provienen de starwars.fandom.com y están disponibles para:
 * - 87 Personajes (100%)
 * - 57 Planetas (95%)
 * - 37 Especies (100%)
 * - 22 Naves Espaciales (61%)
 * - 25 Vehículos (64%)
 * - 3 Películas (50%)
 *
 * Total: 231 imágenes disponibles
 */
object ImageUrlProvider {
    /**
     * Obtiene la URL de imagen para una entidad específica
     * @param entityType Tipo de entidad SWAPI
     * @param id ID numérico de la entidad
     * @return URL de imagen o null si no está disponible (usará emoji fallback en UI)
     */
    fun getImageUrl(entityType: EntityType, id: String): String? {
        // Validar que el ID sea numérico
        if (id.toIntOrNull() == null) return null

        return when (entityType) {
            EntityType.PERSON -> CharacterImageMapping.imageUrls[id]
            EntityType.PLANET -> PlanetsImageMapping.imageUrls[id]
            EntityType.SPECIES -> SpeciesImageMapping.imageUrls[id]
            EntityType.STARSHIP -> StarshipsImageMapping.imageUrls[id]
            EntityType.VEHICLE -> VehiclesImageMapping.imageUrls[id]
            EntityType.FILM -> FilmsImageMapping.imageUrls[id]
        }
    }
}
