package com.manyacov.ratetrackerapp

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.manyacov.presentation.all_rates.AllRatesScreen
import com.manyacov.presentation.all_rates.AllRatesViewModel
import com.manyacov.presentation.favorites.FavoritesScreen
import com.manyacov.presentation.favorites.FavoritesViewModel
import com.manyacov.presentation.filter.FilterScreen
import com.manyacov.ratetrackerapp.navigation.BottomNavigationBar
import com.manyacov.ratetrackerapp.navigation.NavItem
import com.manyacov.ui.theme.RateTrackerAppTheme

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen(navController: NavHostController) {

    val bottomBarState = remember { mutableStateOf(true) }

    Scaffold(
        bottomBar = {
            if (bottomBarState.value) {
                BottomNavigationBar(navController)
            }
        }
    ) { _ ->
        BackHandler(enabled = navController.currentBackStackEntry != null) {
            navController.navigateUp()
        }

        NavHost(navController, startDestination = NavItem.Currencies.path) {
            composable(
                route = NavItem.Currencies.path,
                enterTransition = { fadeIn(animationSpec = tween(500)) }
            ) { backStackEntry ->
                val viewModel = hiltViewModel<AllRatesViewModel>()

                val filterType = backStackEntry.arguments?.getString("filter_type")

                AllRatesScreen(
                    navController = navController,
                    filterType = filterType,
                    viewModel = viewModel
                )
                bottomBarState.value = true
            }
            composable(
                route = NavItem.Favorites.path,
                enterTransition = { fadeIn(animationSpec = tween(500)) })
            {
                val viewModel = hiltViewModel<FavoritesViewModel>()
                FavoritesScreen(navController = navController, viewModel = viewModel)
                bottomBarState.value = true
            }
            composable(NavItem.Filters.path,
                enterTransition = { fadeIn(animationSpec = tween(500)) })
            { backStackEntry ->
                val filterType = backStackEntry.arguments?.getString("filter_type")

                FilterScreen(
                    filterType = filterType,
                    navController = navController
                )
                bottomBarState.value = false
            }
        }
    }
}

@Preview
@Composable
fun MainScreenPreview() {
    val context = LocalContext.current

    RateTrackerAppTheme {
        MainScreen(navController = NavHostController(context = context))
    }
}