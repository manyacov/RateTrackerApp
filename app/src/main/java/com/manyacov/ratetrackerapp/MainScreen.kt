package com.manyacov.ratetrackerapp

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.manyacov.ratetrackerapp.navigation.AppNavigation
import com.manyacov.ratetrackerapp.navigation.BottomNavigationBar
import com.manyacov.ui.theme.RateTrackerAppTheme

//@Composable
//fun MainScreen(navController: NavController) {
//    Scaffold(
//        bottomBar = { BottomNavigationBar(navController) }
//    ) { innerPadding ->
//        NavHost(navController, startDestination = "Home", Modifier.padding(innerPadding)) {
//            composable("Currencies") { AllRatesScreen() }
//            composable("Favorites") { AllRatesScreen() }
//        }
//    }
//}


@Composable
fun MainScreen(navController: NavController) {
    Scaffold(
        bottomBar = { BottomNavigationBar(navController) }
    ) { innerPadding ->
//        NavHost(navController, startDestination = "Home", Modifier.padding(innerPadding)) {
//            composable("Home") { AllRatesScreen() }
//            composable("Search") { AllRatesScreen() }
//        }
        AppNavigation(
            modifier = Modifier
                //.fillMaxWidth()
                //.height(68.dp)
                .padding(innerPadding)
        )
    }
}

@Preview
@Composable
fun MainScreenPreview() {
    val context = LocalContext.current

    RateTrackerAppTheme {
        MainScreen(navController = NavController(context = context))
    }
}