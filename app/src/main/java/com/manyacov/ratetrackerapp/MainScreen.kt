package com.manyacov.ratetrackerapp

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import com.manyacov.ratetrackerapp.navigation.AppNavigation
import com.manyacov.ratetrackerapp.navigation.BottomNavigationBar
import com.manyacov.ui.theme.RateTrackerAppTheme

@Composable
fun MainScreen(navController: NavHostController) {
    Scaffold(
        bottomBar = { BottomNavigationBar(navController) }
    ) { innerPadding ->
        AppNavigation(
            modifier = Modifier
                //.fillMaxWidth()
                //.height(68.dp)
                .padding(innerPadding),
            navController = navController
        )
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