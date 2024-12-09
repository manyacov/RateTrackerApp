package com.manyacov.ratetrackerapp.navigation

import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.manyacov.presentation.all_rates.AllRatesScreen
import com.manyacov.presentation.filter.FilterScreen
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.manyacov.presentation.all_rates.AllRatesViewModel
import com.manyacov.presentation.favorites.FavoritesScreen
import com.manyacov.presentation.favorites.FavoritesViewModel

@Composable
fun AppNavigation(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {

    BackHandler(enabled = navController.currentBackStackEntry != null) {
        navController.navigateUp()
    }

    NavHost(
        navController = navController,
        startDestination = "Currencies/{filter_type}",
        modifier = modifier
    ) {
        composable(
            "Currencies/{filter_type}",
            enterTransition = { fadeIn(animationSpec = tween(500)) },
        ) { backStackEntry ->
            val viewModel = hiltViewModel<AllRatesViewModel>()

            val filterType = backStackEntry.arguments?.getString("filter_type")

            AllRatesScreen(
                navController = navController,
                filterType = filterType,
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