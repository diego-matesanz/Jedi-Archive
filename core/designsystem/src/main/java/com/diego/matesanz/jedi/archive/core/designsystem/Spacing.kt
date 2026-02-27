package com.diego.matesanz.jedi.archive.core.designsystem

import androidx.compose.ui.unit.dp

/**
 * Sistema de espaciado consistente para Jedi Archive
 */
object Spacing {
    val none = 0.dp
    val xxxs = 2.dp
    val xxs = 4.dp
    val xs = 8.dp
    val sm = 12.dp
    val md = 16.dp
    val lg = 24.dp
    val xl = 32.dp
    val xxl = 48.dp
    val xxxl = 64.dp

    // Spacing específico para componentes
    object Component {
        val cardPadding = md
        val cardSpacing = xs
        val listItemPadding = md
        val searchBarPadding = sm
        val tagPadding = xs
        val sectionSpacing = lg
    }

    // Spacing para navegación y layout
    object Layout {
        val screenPadding = md
        val bottomNavHeight = 56.dp
        val topBarHeight = 64.dp
    }
}
