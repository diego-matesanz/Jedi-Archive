package com.diego.matesanz.jedi.archive.core.navigation

/**
 * Destinations de navegación en Jedi Archive
 */
sealed class JediArchiveDestination(val route: String) {

    /**
     * Pantalla de búsqueda (pantalla principal)
     */
    data object Search : JediArchiveDestination("search")

    /**
     * Pantalla de detalle de entidad
     * @param entityType Tipo de entidad (person, planet, etc.)
     * @param id ID de la entidad
     */
    data object Detail : JediArchiveDestination("detail/{entityType}/{id}") {
        const val ARG_ENTITY_TYPE = "entityType"
        const val ARG_ID = "id"

        fun createRoute(entityType: String, id: String): String {
            return "detail/$entityType/$id"
        }
    }

    /**
     * Pantalla de configuración
     */
    data object Settings : JediArchiveDestination("settings")
}

/**
 * Tipos de entidad para navegación (strings)
 */
object EntityTypeArg {
    const val PERSON = "person"
    const val PLANET = "planet"
    const val SPECIES = "species"
    const val STARSHIP = "starship"
    const val VEHICLE = "vehicle"
    const val FILM = "film"
}
