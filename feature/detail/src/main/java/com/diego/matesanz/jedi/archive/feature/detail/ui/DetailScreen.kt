package com.diego.matesanz.jedi.archive.feature.detail.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.diego.matesanz.jedi.archive.core.designsystem.Spacing
import com.diego.matesanz.jedi.archive.core.domain.model.EntityType
import com.diego.matesanz.jedi.archive.feature.detail.DetailUiState
import com.diego.matesanz.jedi.archive.feature.detail.DetailViewModel
import com.diego.matesanz.jedi.archive.feature.detail.components.DetailSectionCard

/**
 * Pantalla de detalle de entidad
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    entityType: EntityType,
    entityId: String,
    viewModel: DetailViewModel,
    onNavigateBack: () -> Unit,
    onNavigateToDetail: (entityType: String, id: String) -> Unit,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(entityType, entityId) {
        viewModel.loadDetail(entityType, entityId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        when (uiState) {
                            is DetailUiState.Success -> (uiState as DetailUiState.Success).entity.name
                            else -> "Loading..."
                        }
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        },
        modifier = modifier
    ) { paddingValues ->
        when (val state = uiState) {
            is DetailUiState.Loading -> {
                LoadingState(modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues))
            }
            is DetailUiState.Success -> {
                SuccessState(
                    viewModel = viewModel,
                    entity = state.entity,
                    onNavigateToDetail = onNavigateToDetail,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                )
            }
            is DetailUiState.Error -> {
                ErrorState(
                    message = state.message,
                    onRetry = { viewModel.retry(entityType, entityId) },
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                )
            }
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
                text = "Loading details...",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun SuccessState(
    viewModel: DetailViewModel,
    entity: com.diego.matesanz.jedi.archive.core.domain.model.SwapiEntity,
    onNavigateToDetail: (entityType: String, id: String) -> Unit,
    modifier: Modifier = Modifier
) {
    val sections = viewModel.getDetailSections(entity)

    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(Spacing.md),
        verticalArrangement = Arrangement.spacedBy(Spacing.md)
    ) {
        // Header con nombre de entidad
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(Spacing.lg),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = getEntityIcon(entity.type.name),
                        style = MaterialTheme.typography.displayLarge
                    )
                    Spacer(modifier = Modifier.height(Spacing.sm))
                    Text(
                        text = entity.name,
                        style = MaterialTheme.typography.headlineMedium,
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(Spacing.xs))
                    AssistChip(
                        onClick = {},
                        label = {
                            Text(entity.type.name.lowercase().replaceFirstChar { it.uppercase() })
                        },
                        colors = AssistChipDefaults.assistChipColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            labelColor = MaterialTheme.colorScheme.onPrimary
                        )
                    )
                }
            }
        }

        // Secciones de información
        items(sections) { section ->
            DetailSectionCard(
                section = section,
                onNavigateToDetail = onNavigateToDetail
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
                text = "Error Loading Details",
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

/**
 * Devuelve un emoji según el tipo de entidad
 */
private fun getEntityIcon(entityType: String): String {
    return when (entityType) {
        "PERSON" -> "👤"
        "PLANET" -> "🌍"
        "SPECIES" -> "👽"
        "STARSHIP" -> "🚀"
        "VEHICLE" -> "🚗"
        "FILM" -> "🎬"
        else -> "📄"
    }
}
