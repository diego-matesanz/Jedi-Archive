package com.diego.matesanz.jedi.archive.core.ui

import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import coil.compose.SubcomposeAsyncImage
import com.diego.matesanz.jedi.archive.core.designsystem.GoldenLight
import com.diego.matesanz.jedi.archive.core.domain.model.EntityType

/**
 * Composable que muestra imágenes de entidades con efectos shimmer/fade-in y emoji fallback
 *
 * @param imageUrl URL de la imagen a cargar
 * @param entityType Tipo de entidad para emoji fallback
 * @param modifier Modificador para personalizar tamaño y forma
 * @param shape Forma del contenedor (default: MaterialTheme.shapes.medium)
 * @param contentScale Escala del contenido (default: ContentScale.Crop)
 * @param placeholderTextStyle Estilo del texto del placeholder
 */
@Composable
fun EntityImage(
    imageUrl: String?,
    entityType: EntityType,
    modifier: Modifier = Modifier,
    shape: Shape = MaterialTheme.shapes.medium,
    contentScale: ContentScale = ContentScale.Fit,
    placeholderTextStyle: TextStyle = MaterialTheme.typography.headlineMedium
) {
    if (imageUrl == null) {
        // Si no hay URL, mostrar emoji directamente
        EmojiPlaceholder(
            entityType = entityType,
            modifier = modifier,
            shape = shape,
            textStyle = placeholderTextStyle
        )
    } else {
        SubcomposeAsyncImage(
            model = imageUrl,
            contentDescription = "Image of ${entityType.name}",
            modifier = modifier,
            contentScale = contentScale,
            loading = {
                ShimmerPlaceholder(
                    modifier = Modifier.fillMaxSize(),
                    shape = shape
                )
            },
            error = {
                EmojiPlaceholder(
                    entityType = entityType,
                    modifier = Modifier.fillMaxSize(),
                    shape = shape,
                    textStyle = placeholderTextStyle
                )
            },
            success = { state ->
                // Fade-in animation
                var alpha by remember { mutableStateOf(0f) }
                LaunchedEffect(Unit) {
                    animate(
                        initialValue = 0f,
                        targetValue = 1f,
                        animationSpec = tween(
                            durationMillis = 300,
                            easing = FastOutSlowInEasing
                        )
                    ) { value, _ ->
                        alpha = value
                    }
                }

                Box(modifier = Modifier.alpha(alpha)) {
                    state.painter?.let { painter ->
                        androidx.compose.foundation.Image(
                            painter = painter,
                            contentDescription = "Image of ${entityType.name}",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = contentScale
                        )
                    }
                }
            }
        )
    }
}

/**
 * Placeholder con efecto shimmer durante la carga
 */
@Composable
private fun ShimmerPlaceholder(
    modifier: Modifier = Modifier,
    shape: Shape = MaterialTheme.shapes.medium
) {
    val infiniteTransition = rememberInfiniteTransition(label = "shimmer")
    val alpha by infiniteTransition.animateFloat(
        initialValue = 0.3f,
        targetValue = 0.7f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "shimmerAlpha"
    )

    Surface(
        modifier = modifier,
        shape = shape,
        color = GoldenLight.copy(alpha = alpha)
    ) {
        // Empty surface with animated alpha
    }
}

/**
 * Placeholder con emoji cuando falla la carga o no hay imagen
 */
@Composable
private fun EmojiPlaceholder(
    entityType: EntityType,
    modifier: Modifier = Modifier,
    shape: Shape = MaterialTheme.shapes.medium,
    textStyle: TextStyle = MaterialTheme.typography.headlineMedium
) {
    val emoji = when (entityType) {
        EntityType.PERSON -> "👤"
        EntityType.PLANET -> "🌍"
        EntityType.SPECIES -> "🧬"
        EntityType.STARSHIP -> "🚀"
        EntityType.VEHICLE -> "🚗"
        EntityType.FILM -> "🎬"
    }

    Surface(
        modifier = modifier,
        shape = shape,
        color = GoldenLight.copy(alpha = 0.2f)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = emoji,
                style = textStyle
            )
        }
    }
}
