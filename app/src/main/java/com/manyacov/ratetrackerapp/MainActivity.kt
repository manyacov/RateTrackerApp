package com.manyacov.ratetrackerapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.manyacov.domain.rate_tracker.model.CurrencySymbols
import com.manyacov.presentation.ui_filter_parts.CurrencySymbolItem
import com.manyacov.ui.theme.RateTrackerAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            RateTrackerAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()

                    MainScreen(navController = navController)
//                    Scaffold(
//
//                    ) {
//
//                    }
                    //SymbolsDropdownMenu(viewModel.state.symbols ?: listOf(CurrencySymbols("EUR")))
                }
            }
        }
    }
}

@Composable
fun FilterList(itemList: List<CurrencySymbols>, modifier: Modifier = Modifier) {

    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(horizontal = 8.dp)
    ) {
        items(itemList) { item ->
            CurrencySymbolItem(item = item)
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun GreetingPreview() {
//    RateTrackerAppTheme {
//        Greeting("Android")
//    }
//}