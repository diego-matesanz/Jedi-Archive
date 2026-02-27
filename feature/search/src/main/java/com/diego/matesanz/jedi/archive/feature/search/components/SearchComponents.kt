package com.diego.matesanz.jedi.archive.feature.search.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.diego.matesanz.jedi.archive.core.designsystem.ComponentShapes
import com.diego.matesanz.jedi.archive.core.designsystem.Spacing
import com.diego.matesanz.jedi.archive.core.domain.model.SearchResult
import com.diego.matesanz.jedi.archive.core.navigation.toNavigationArg
import com.diego.matesanz.jedi.archive.core.ui.EntityImage
import com.diego.matesanz.jedi.archive.feature.search.CategoryFilter

/**
 * Barra de búsqueda estilo Jedi Archive
 */
@Composable
fun JediSearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    onSearch: () -> Unit,
    onClear: () -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = query,
        onValueChange = onQueryChange,
        modifier = modifier,
        placeholder = { Text("Search in the Archives...") },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search",
                tint = MaterialTheme.colorScheme.primary
            )
        },
        trailingIcon = {
            if (query.isNotEmpty()) {
                IconButton(onClick = onClear) {
                    Icon(
                        imageVector = Icons.Default.Clear,
                        contentDescription = "Clear",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        },
        singleLine = true,
        shape = ComponentShapes.searchBar,
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            unfocusedBorderColor = MaterialTheme.colorScheme.outline
        )
    )
}

/**
 * Fila de chips de categorías
 */
@Composable
fun CategoryChipRow(
    categories: List<CategoryFilter>,
    selectedCategory: CategoryFilter,
    onCategorySelected: (CategoryFilter) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(Spacing.xs),
        contentPadding = PaddingValues(vertical = Spacing.xs)
    ) {
        items(categories) { category ->
            CategoryChip(
                category = category,
                isSelected = category == selectedCategory,
                onClick = { onCategorySelected(category) }
            )
        }
    }
}

/**
 * Chip individual de categoría
 */
@Composable
private fun CategoryChip(
    category: CategoryFilter,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    FilterChip(
        selected = isSelected,
        onClick = onClick,
        label = { Text(category.displayName) },
        modifier = modifier,
        shape = ComponentShapes.categoryChip,
        colors = FilterChipDefaults.filterChipColors(
            selectedContainerColor = MaterialTheme.colorScheme.primary,
            selectedLabelColor = MaterialTheme.colorScheme.onPrimary
        )
    )
}

/**
 * Lista de resultados exitosa
 */
@Composable
fun SuccessState(
    results: List<SearchResult>,
    onResultClick: (entityType: String, id: String) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(Spacing.md),
        verticalArrangement = Arrangement.spacedBy(Spacing.md)
    ) {
        item {
            Text(
                text = "${results.size} result${if (results.size != 1) "s" else ""} found",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(bottom = Spacing.xs)
            )
        }

        items(results) { result ->
            ResultCard(
                result = result,
                onClick = { onResultClick(result.type.toNavigationArg(), result.id) }
            )
        }
    }
}

/**
 * Card de resultado individual
 */
@Composable
private fun ResultCard(
    result: SearchResult,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    ElevatedCard(
        onClick = onClick,
        modifier = modifier.fillMaxWidth(),
        shape = ComponentShapes.resultCard,
        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = 2.dp
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Spacing.md),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Entity image with shimmer/fade-in
            EntityImage(
                imageUrl = result.imageUrl,
                entityType = result.type,
                modifier = Modifier.size(56.dp),
                shape = MaterialTheme.shapes.medium,
                placeholderTextStyle = MaterialTheme.typography.headlineSmall,
                contentScale = ContentScale.Fit
            )

            Spacer(modifier = Modifier.width(Spacing.md))

            // Content
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = result.name,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                result.subtitle?.let { subtitle ->
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = subtitle,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            // Type badge
            AssistChip(
                onClick = {},
                label = { Text(result.type.name.lowercase().replaceFirstChar { it.uppercase() }) },
                modifier = Modifier.padding(start = Spacing.xs),
                colors = AssistChipDefaults.assistChipColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer,
                    labelColor = MaterialTheme.colorScheme.onSecondaryContainer
                )
            )
        }
    }
}
