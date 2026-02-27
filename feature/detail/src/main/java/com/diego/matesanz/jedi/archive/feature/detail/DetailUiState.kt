package com.diego.matesanz.jedi.archive.feature.detail

import com.diego.matesanz.jedi.archive.core.domain.model.SwapiEntity

/**
 * Estados de UI para la pantalla de detalle
 */
sealed interface DetailUiState {
    /**
     * Cargando detalle
     */
    data object Loading : DetailUiState

    /**
     * Detalle cargado exitosamente
     */
    data class Success(
        val entity: SwapiEntity
    ) : DetailUiState

    /**
     * Error al cargar detalle
     */
    data class Error(val message: String) : DetailUiState
}

/**
 * Representa una sección de información en el detalle
 */
data class DetailSection(
    val title: String,
    val items: List<DetailItem>
)

/**
 * Representa un item de información en una sección
 */
sealed class DetailItem {
    /**
     * Campo de texto simple (label: value)
     */
    data class TextField(
        val label: String,
        val value: String
    ) : DetailItem()

    /**
     * Tag navegable (clickeable)
     */
    data class NavigableTag(
        val label: String,
        val entityId: String,
        val entityType: String
    ) : DetailItem()

    /**
     * Lista de tags navegables
     */
    data class NavigableTagList(
        val label: String,
        val tags: List<NavigableTag>
    ) : DetailItem()
}
