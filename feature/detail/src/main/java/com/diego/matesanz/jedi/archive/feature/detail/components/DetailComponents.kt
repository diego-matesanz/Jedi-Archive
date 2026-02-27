package com.diego.matesanz.jedi.archive.feature.detail.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.diego.matesanz.jedi.archive.core.designsystem.ComponentShapes
import com.diego.matesanz.jedi.archive.core.designsystem.Spacing
import com.diego.matesanz.jedi.archive.feature.detail.DetailItem
import com.diego.matesanz.jedi.archive.feature.detail.DetailSection

/**
 * Card para una sección de detalles
 */
@Composable
fun DetailSectionCard(
    section: DetailSection,
    onNavigateToDetail: (entityType: String, id: String) -> Unit,
    modifier: Modifier = Modifier
) {
    ElevatedCard(
        modifier = modifier.fillMaxWidth(),
        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = 2.dp
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Spacing.md)
        ) {
            // Section title
            if (section.title.isNotEmpty()) {
                Text(
                    text = section.title,
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(bottom = Spacing.sm)
                )
                HorizontalDivider(
                    modifier = Modifier.padding(bottom = Spacing.sm),
                    color = MaterialTheme.colorScheme.outlineVariant
                )
            }

            // Section items
            section.items.forEachIndexed { index, item ->
                when (item) {
                    is DetailItem.TextField -> {
                        TextFieldItem(item)
                    }
                    is DetailItem.NavigableTag -> {
                        NavigableTagItem(
                            item = item,
                            onClick = { onNavigateToDetail(item.entityType, item.entityId) }
                        )
                    }
                    is DetailItem.NavigableTagList -> {
                        NavigableTagListItem(
                            item = item,
                            onTagClick = { tag -> onNavigateToDetail(tag.entityType, tag.entityId) }
                        )
                    }
                }

                // Spacer between items
                if (index < section.items.lastIndex) {
                    Spacer(modifier = Modifier.height(Spacing.sm))
                }
            }
        }
    }
}

/**
 * Item de texto simple
 */
@Composable
private fun TextFieldItem(
    item: DetailItem.TextField,
    modifier: Modifier = Modifier
) {
    if (item.label.isNotEmpty()) {
        Row(
            modifier = modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = item.label,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.weight(0.4f)
            )
            Text(
                text = item.value,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.weight(0.6f)
            )
        }
    } else {
        // Opening crawl o texto largo sin label
        Text(
            text = item.value,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = modifier.fillMaxWidth()
        )
    }
}

/**
 * Tag navegable individual
 */
@Composable
private fun NavigableTagItem(
    item: DetailItem.NavigableTag,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = item.label,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.weight(0.4f)
        )
        NavigableChip(
            text = item.label,
            onClick = onClick,
            modifier = Modifier.weight(0.6f)
        )
    }
}

/**
 * Lista de tags navegables
 */
@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun NavigableTagListItem(
    item: DetailItem.NavigableTagList,
    onTagClick: (DetailItem.NavigableTag) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = item.label,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(bottom = Spacing.xs)
        )

        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(Spacing.xs),
            verticalArrangement = Arrangement.spacedBy(Spacing.xs),
            modifier = Modifier.fillMaxWidth()
        ) {
            item.tags.forEach { tag ->
                NavigableChip(
                    text = "${tag.label} #${tag.entityId}",
                    onClick = { onTagClick(tag) }
                )
            }
        }
    }
}

/**
 * Chip navegable clickeable
 */
@Composable
private fun NavigableChip(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    AssistChip(
        onClick = onClick,
        label = { Text(text) },
        modifier = modifier,
        shape = ComponentShapes.navigationTag,
        colors = AssistChipDefaults.assistChipColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer,
            labelColor = MaterialTheme.colorScheme.onSecondaryContainer
        )
    )
}
