package com.diego.matesanz.jedi.archive.core.data.util

import com.diego.matesanz.jedi.archive.core.domain.model.EntityType

/**
 * Provee URLs de imágenes para entidades de Star Wars
 *
 * Usa un mapeo estático de Wookieepedia para personajes (akabab/starwars-api source).
 * Otros tipos de entidades (planetas, especies, naves, películas) devuelven null
 * y usarán emoji fallback en la UI.
 */
object ImageUrlProvider {
    /**
     * Obtiene la URL de imagen para una entidad específica
     * @param entityType Tipo de entidad SWAPI
     * @param id ID numérico de la entidad
     * @return URL de imagen o null si no está disponible
     */
    fun getImageUrl(entityType: EntityType, id: String): String? {
        // Validar que el ID sea numérico
        if (id.toIntOrNull() == null) return null

        // Solo personajes tienen imágenes disponibles
        return when (entityType) {
            EntityType.PERSON -> CharacterImageMapping.imageUrls[id]
            else -> null // Planetas, especies, naves y películas usan emoji fallback
        }
    }
}
