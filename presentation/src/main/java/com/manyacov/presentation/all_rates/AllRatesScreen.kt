package com.manyacov.presentation.all_rates

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.manyacov.domain.rate_tracker.model.CurrencyRateValue
import com.manyacov.presentation.ui_parts.CurrencyPriceItem
import com.manyacov.presentation.ui_parts.FilterItem
import com.manyacov.presentation.ui_parts.SymbolsDropdownMenu
import com.manyacov.ui.theme.RateTrackerAppTheme
import com.manyacov.ui.R
import com.manyacov.ui.theme.HeaderBg
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle


@Composable
fun AllRatesScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController? = null,
    viewModel: AllRatesViewModel
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    if (state.symbols.isNotEmpty()) {
        Log.println(Log.ERROR, "TTTTT", state.symbols.toString())
    }
//    LaunchedEffect(Unit) {
//        viewModel.loadInfo()
//    }

    Column(modifier = modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = HeaderBg)
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(18.dp)
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                style = MaterialTheme.typography.titleLarge,
                text = stringResource(id = R.string.currencies)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                SymbolsDropdownMenu(
                    modifier = modifier.weight(1f),
                    itemsList = state.symbols
                )
                FilterItem(
                    onClick = { navController?.navigate("Filters") }
                )
            }
        }

        val k = CurrencyRateValue(
            symbols = "USD",
            value = 0.45687,
            isFavorite = false
        )
        val k1 = CurrencyRateValue(
            symbols = "USD",
            value = 0.45687,
            isFavorite = true
        )
        val list = listOf(k, k, k1, k1, k, k)

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp)
        ) {
            items(list) { item ->
                CurrencyPriceItem(item = item)
            }
        }
    }
}

@Preview
@Composable
fun AllRatesScreenPreview() {
    RateTrackerAppTheme {
        AllRatesScreen(
            viewModel = viewModel()
        )
    }
}