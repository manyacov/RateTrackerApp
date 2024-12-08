package com.manyacov.ratetrackerapp.navigation

import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.manyacov.presentation.all_rates.AllRatesScreen
import com.manyacov.presentation.FilterScreen
import androidx.hilt.navigation.compose.hiltViewModel
import com.manyacov.presentation.all_rates.AllRatesViewModel
import com.manyacov.presentation.favorites.FavoritesScreen
import com.manyacov.presentation.favorites.FavoritesViewModel

@Composable
fun AppNavigation(
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()

    BackHandler(enabled = navController.currentBackStackEntry != null) {
        navController.navigateUp()
    }

    NavHost(
        navController = navController,
        startDestination = "Currencies",
        modifier = modifier
    ) {
        composable(
            "Currencies",
            enterTransition = { fadeIn(animationSpec = tween(500)) },
        ) {
            val viewModel = hiltViewModel<AllRatesViewModel>()
            AllRatesScreen(
                navController = navController,
                viewModel = viewModel
            )
        }
        composable(
            "Favorites",
            enterTransition = { fadeIn(animationSpec = tween(500)) },
        ) {
            val viewModel = hiltViewModel<FavoritesViewModel>()
            FavoritesScreen(
                navController = navController,
                viewModel = viewModel
            )
        }
        composable(
            "Filters",
            enterTransition = { fadeIn(animationSpec = tween(500)) },
        ) {
            FilterScreen(navController = navController)
        }
    }
}