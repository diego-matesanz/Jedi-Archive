package com.diego.matesanz.jedi.archive.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.diego.matesanz.jedi.archive.core.data.repository.SwapiRepositoryImpl
import com.diego.matesanz.jedi.archive.core.datastore.ThemePreferences
import com.diego.matesanz.jedi.archive.core.domain.usecase.GetEntityDetailUseCase
import com.diego.matesanz.jedi.archive.core.domain.usecase.SearchUseCase
import com.diego.matesanz.jedi.archive.core.navigation.JediArchiveDestination
import com.diego.matesanz.jedi.archive.core.navigation.navigateToDetail
import com.diego.matesanz.jedi.archive.core.navigation.toEntityType
import com.diego.matesanz.jedi.archive.core.network.api.SwapiService
import com.diego.matesanz.jedi.archive.feature.detail.DetailViewModelFactory
import com.diego.matesanz.jedi.archive.feature.detail.ui.DetailScreen
import com.diego.matesanz.jedi.archive.feature.search.SearchViewModelFactory
import com.diego.matesanz.jedi.archive.feature.search.ui.SearchScreen
import com.diego.matesanz.jedi.archive.feature.settings.SettingsViewModelFactory
import com.diego.matesanz.jedi.archive.feature.settings.ui.SettingsScreen
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

/**
 * NavHost principal de Jedi Archive
 */
@Composable
fun JediArchiveNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    // Setup manual dependencies
    val json = Json {
        ignoreUnknownKeys = true
        coerceInputValues = true
    }

    val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()

    val retrofit = Retrofit.Builder()
        .baseUrl("https://swapi.dev/api/")
        .client(okHttpClient)
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .build()

    val swapiService = retrofit.create(SwapiService::class.java)
    val repository = SwapiRepositoryImpl(swapiService)
    val searchUseCase = SearchUseCase(repository)
    val getEntityDetailUseCase = GetEntityDetailUseCase(repository)
    val themePreferences = ThemePreferences(context)

    NavHost(
        navController = navController,
        startDestination = JediArchiveDestination.Search.route,
        modifier = modifier
    ) {
        // Search Screen
        composable(route = JediArchiveDestination.Search.route) {
            val searchViewModel = viewModel<com.diego.matesanz.jedi.archive.feature.search.SearchViewModel>(
                factory = SearchViewModelFactory(searchUseCase)
            )

            SearchScreen(
                viewModel = searchViewModel,
                onResultClick = { entityType, id ->
                    navController.navigateToDetail(entityType, id)
                },
                onNavigateToSettings = {
                    navController.navigate(JediArchiveDestination.Settings.route)
                }
            )
        }

        // Settings Screen
        composable(route = JediArchiveDestination.Settings.route) {
            val settingsViewModel = viewModel<com.diego.matesanz.jedi.archive.feature.settings.SettingsViewModel>(
                factory = SettingsViewModelFactory(themePreferences)
            )

            SettingsScreen(
                viewModel = settingsViewModel,
                onNavigateBack = { navController.popBackStack() }
            )
        }

        // Detail Screen
        composable(
            route = JediArchiveDestination.Detail.route,
            arguments = listOf(
                navArgument(JediArchiveDestination.Detail.ARG_ENTITY_TYPE) {
                    type = NavType.StringType
                },
                navArgument(JediArchiveDestination.Detail.ARG_ID) {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            val entityTypeArg = backStackEntry.arguments?.getString(
                JediArchiveDestination.Detail.ARG_ENTITY_TYPE
            ) ?: return@composable

            val entityId = backStackEntry.arguments?.getString(
                JediArchiveDestination.Detail.ARG_ID
            ) ?: return@composable

            val entityType = entityTypeArg.toEntityType()
            val detailViewModel = viewModel<com.diego.matesanz.jedi.archive.feature.detail.DetailViewModel>(
                factory = DetailViewModelFactory(getEntityDetailUseCase)
            )

            DetailScreen(
                entityType = entityType,
                entityId = entityId,
                viewModel = detailViewModel,
                onNavigateBack = { navController.popBackStack() },
                onNavigateToDetail = { type, id ->
                    navController.navigateToDetail(type, id)
                }
            )
        }
    }
}
