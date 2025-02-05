package com.manyacov.ratetrackerapp

import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.manyacov.presentation.all_rates.AllRatesScreen
import com.manyacov.presentation.all_rates.AllRatesViewModel
import com.manyacov.presentation.favorites.FavoritesScreen
import com.manyacov.presentation.favorites.FavoritesViewModel
import com.manyacov.presentation.filter.FilterScreen
import com.manyacov.ratetrackerapp.navigation.BottomNavigationBar
import com.manyacov.ratetrackerapp.navigation.NavItem
import com.manyacov.ratetrackerapp.utils.BOTTOM_NAV_CHANGING_DURATION
import com.manyacov.ratetrackerapp.utils.SCREEN_CHANGING_DURATION
import com.manyacov.ui.theme.RateTrackerAppTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest

@Composable
fun MainScreen(navController: NavHostController) {
    var bottomBarState by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        navController.currentBackStackEntryFlow.collectLatest { backStackEntry ->
            delay(BOTTOM_NAV_CHANGING_DURATION)
            bottomBarState = backStackEntry.destination.route != NavItem.Filters.path
        }
    }

    Scaffold(
        bottomBar = {
            if (bottomBarState) {
                BottomNavigationBar(navController)
            }
        }
    ) { innerPadding ->
        BackHandler(enabled = navController.currentBackStackEntry != null) {
            navController.navigateUp()
        }

        val allRatesViewModel = hiltViewModel<AllRatesViewModel>()

        NavHost(navController, startDestination = NavItem.Currencies.path) {
            composable(
                route = NavItem.Currencies.path,
                enterTransition = { fadeIn(animationSpec = tween(SCREEN_CHANGING_DURATION)) }
            ) {
                AllRatesScreen(
                    modifier = Modifier.padding(innerPadding),
                    navController = navController,
                    viewModel = allRatesViewModel
                )
            }
            composable(
                route = NavItem.Favorites.path,
                enterTransition = { fadeIn(animationSpec = tween(SCREEN_CHANGING_DURATION)) })
            {
                val favoritesViewModel = hiltViewModel<FavoritesViewModel>()

                FavoritesScreen(
                    modifier = Modifier.padding(innerPadding),
                    viewModel = favoritesViewModel
                )
            }
            composable(
                route = NavItem.Filters.path,
                enterTransition = { fadeIn(animationSpec = tween(SCREEN_CHANGING_DURATION)) })
            {
                FilterScreen(
                    modifier = Modifier.padding(innerPadding),
                    navController = navController,
                    viewModel = allRatesViewModel
                )
            }
        }
    }
}

@Preview
@Composable
fun MainScreenPreview() {
    RateTrackerAppTheme {
        MainScreen(navController = rememberNavController())
    }
}