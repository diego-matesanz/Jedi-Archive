package com.diego.matesanz.jedi.archive.feature.search.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.diego.matesanz.jedi.archive.core.designsystem.Spacing
import com.diego.matesanz.jedi.archive.feature.search.CategoryFilter
import com.diego.matesanz.jedi.archive.feature.search.SearchUiState
import com.diego.matesanz.jedi.archive.feature.search.SearchViewModel
import com.diego.matesanz.jedi.archive.feature.search.components.*

/**
 * Pantalla de búsqueda principal
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    viewModel: SearchViewModel,
    onResultClick: (entityType: String, id: String) -> Unit,
    onNavigateToSettings: () -> Unit,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()
    val selectedCategory by viewModel.selectedCategory.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Jedi Archive") },
                actions = {
                    IconButton(onClick = onNavigateToSettings) {
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = "Settings",
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        },
        modifier = modifier
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // SearchBar
            JediSearchBar(
                query = searchQuery,
                onQueryChange = viewModel::onSearchQueryChanged,
                onSearch = viewModel::onSearchClicked,
                onClear = viewModel::onClearSearch,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(Spacing.md)
            )

            // Category Chips
            CategoryChipRow(
                categories = CategoryFilter.all(),
                selectedCategory = selectedCategory,
                onCategorySelected = viewModel::onCategorySelected,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = Spacing.md)
            )

            Spacer(modifier = Modifier.height(Spacing.md))

            // Content
            when (val state = uiState) {
                is SearchUiState.Initial -> {
                    InitialState(modifier = Modifier.fillMaxSize())
                }
                is SearchUiState.Loading -> {
                    LoadingState(modifier = Modifier.fillMaxSize())
                }
                is SearchUiState.Success -> {
                    SuccessState(
                        results = state.results,
                        onResultClick = onResultClick,
                        modifier = Modifier.fillMaxSize()
                    )
                }
                is SearchUiState.Empty -> {
                    EmptyState(
                        query = state.query,
                        modifier = Modifier.fillMaxSize()
                    )
                }
                is SearchUiState.Error -> {
                    ErrorState(
                        message = state.message,
                        onRetry = viewModel::onSearchClicked,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }
    }
}

@Composable
private fun InitialState(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "🔍",
                style = MaterialTheme.typography.displayLarge
            )
            Spacer(modifier = Modifier.height(Spacing.md))
            Text(
                text = "Search the Galaxy",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(Spacing.xs))
            Text(
                text = "Enter at least 2 characters to search",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
private fun LoadingState(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator(
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(Spacing.md))
            Text(
                text = "Searching...",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun EmptyState(
    query: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(Spacing.lg)
        ) {
            Text(
                text = "🤷",
                style = MaterialTheme.typography.displayLarge
            )
            Spacer(modifier = Modifier.height(Spacing.md))
            Text(
                text = "No results found",
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(Spacing.xs))
            Text(
                text = "No matches for \"$query\"",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
private fun ErrorState(
    message: String,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(Spacing.lg)
        ) {
            Text(
                text = "⚠️",
                style = MaterialTheme.typography.displayLarge
            )
            Spacer(modifier = Modifier.height(Spacing.md))
            Text(
                text = "Error",
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.error
            )
            Spacer(modifier = Modifier.height(Spacing.xs))
            Text(
                text = message,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(Spacing.lg))
            Button(onClick = onRetry) {
                Text("Retry")
            }
        }
    }
}
