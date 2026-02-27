package com.diego.matesanz.jedi.archive.core.designsystem

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.ui.unit.dp

/**
 * Sistema de formas para Jedi Archive
 * Inspirado en holocrones y arquitectura de la Alta República
 */

val JediArchiveShapes = Shapes(
    // Extra Small - Para tags y chips pequeños
    extraSmall = RoundedCornerShape(4.dp),

    // Small - Para botones pequeños y elementos compactos
    small = RoundedCornerShape(8.dp),

    // Medium - Para cards y contenedores principales
    medium = RoundedCornerShape(12.dp),

    // Large - Para modales y bottom sheets
    large = RoundedCornerShape(16.dp),

    // Extra Large - Para diálogos y contenedores grandes
    extraLarge = RoundedCornerShape(24.dp)
)

/**
 * Shapes específicos para componentes customizados
 */
object ComponentShapes {
    val searchBar = RoundedCornerShape(28.dp)  // Más redondeado para la barra de búsqueda
    val categoryChip = RoundedCornerShape(16.dp)
    val navigationTag = RoundedCornerShape(20.dp)
    val resultCard = RoundedCornerShape(12.dp)
}
