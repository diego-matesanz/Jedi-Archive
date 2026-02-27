package com.diego.matesanz.jedi.archive.core.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination

/**
 * Extensiones de navegación para simplificar la navegación en la app
 */

/**
 * Navega a la pantalla de detalle de una entidad
 */
fun NavController.navigateToDetail(entityType: String, id: String) {
    navigate(JediArchiveDestination.Detail.createRoute(entityType, id))
}

/**
 * Navega a la pantalla de búsqueda
 */
fun NavController.navigateToSearch() {
    navigate(JediArchiveDestination.Search.route) {
        // Pop up to start destination and save state
        popUpTo(graph.findStartDestination().id) {
            saveState = true
        }
        // Avoid multiple copies of the same destination
        launchSingleTop = true
        // Restore state when reselecting
        restoreState = true
    }
}

/**
 * Navega a la pantalla de configuración
 */
fun NavController.navigateToSettings() {
    navigate(JediArchiveDestination.Settings.route)
}

/**
 * Convierte un EntityType de dominio a string para navegación
 */
fun com.diego.matesanz.jedi.archive.core.domain.model.EntityType.toNavigationArg(): String {
    return when (this) {
        com.diego.matesanz.jedi.archive.core.domain.model.EntityType.PERSON -> EntityTypeArg.PERSON
        com.diego.matesanz.jedi.archive.core.domain.model.EntityType.PLANET -> EntityTypeArg.PLANET
        com.diego.matesanz.jedi.archive.core.domain.model.EntityType.SPECIES -> EntityTypeArg.SPECIES
        com.diego.matesanz.jedi.archive.core.domain.model.EntityType.STARSHIP -> EntityTypeArg.STARSHIP
        com.diego.matesanz.jedi.archive.core.domain.model.EntityType.VEHICLE -> EntityTypeArg.VEHICLE
        com.diego.matesanz.jedi.archive.core.domain.model.EntityType.FILM -> EntityTypeArg.FILM
    }
}

/**
 * Convierte un string de navegación a EntityType de dominio
 */
fun String.toEntityType(): com.diego.matesanz.jedi.archive.core.domain.model.EntityType {
    return when (this) {
        EntityTypeArg.PERSON -> com.diego.matesanz.jedi.archive.core.domain.model.EntityType.PERSON
        EntityTypeArg.PLANET -> com.diego.matesanz.jedi.archive.core.domain.model.EntityType.PLANET
        EntityTypeArg.SPECIES -> com.diego.matesanz.jedi.archive.core.domain.model.EntityType.SPECIES
        EntityTypeArg.STARSHIP -> com.diego.matesanz.jedi.archive.core.domain.model.EntityType.STARSHIP
        EntityTypeArg.VEHICLE -> com.diego.matesanz.jedi.archive.core.domain.model.EntityType.VEHICLE
        EntityTypeArg.FILM -> com.diego.matesanz.jedi.archive.core.domain.model.EntityType.FILM
        else -> throw IllegalArgumentException("Unknown entity type: $this")
    }
}
