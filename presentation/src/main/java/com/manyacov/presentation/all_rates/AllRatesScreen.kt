package com.manyacov.presentation.all_rates

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.LazyPagingItems
import com.manyacov.ui.theme.Outline
import androidx.paging.compose.collectAsLazyPagingItems
import com.manyacov.presentation.filter.getSortOptionByDescription


@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun AllRatesScreen(
    modifier: Modifier = Modifier,
    filterType: String?,
    navController: NavHostController? = null,
    viewModel: AllRatesViewModel,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val ratesLazyPagingItems = viewModel.rateValues?.collectAsLazyPagingItems()

    LaunchedEffect(Unit) {
        viewModel.getCurrencySymbols()
        Log.println(Log.ERROR, "TTTT LaunchedEffect getCurrencySymbols", filterType.toString())
    }

    LaunchedEffect(key1 = filterType) {
        Log.println(Log.ERROR, "TTTT LaunchedEffect filterType", filterType.toString())

        //viewModel.applyFilter("USD", filterType)
        viewModel.getLatestRates("USD", filterType.getSortOptionByDescription())
    }

    AllRatesScreen(
        state = state,
        filterType = filterType,
        ratesLazyPagingItems = ratesLazyPagingItems,
        modifier = modifier,
        navController = navController,
        selectFavorite = { symbols ->
            viewModel.selectFavorite(state.symbols.first().symbols, symbols)
        }
    )
}

@Composable
fun AllRatesScreen(
    state: RateTrackerState,
    filterType: String?,
    ratesLazyPagingItems: LazyPagingItems<CurrencyRateValue>?,
    modifier: Modifier = Modifier,
    navController: NavHostController? = null,
    selectFavorite: (String) -> Unit = {}
) {
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
                    onClick = { navController?.navigate("filters/$filterType") }
                )
            }
        }

        Spacer(
            modifier = Modifier
                .height(1.dp)
                .fillMaxWidth()
                .background(color = Outline)
        )

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp)
        ) {
            items(count = ratesLazyPagingItems?.itemCount ?: 0) { index ->
                Log.println(Log.ERROR, "FFFF", "init lazy column")
                Log.println(Log.ERROR, "FFFF", ratesLazyPagingItems?.itemCount.toString())
                val item = ratesLazyPagingItems?.get(index)
                if (item != null) {
                    CurrencyPriceItem(
                        item = item,
                        onClick = { symbols -> selectFavorite(symbols) }
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun AllRatesScreenPreview() {
    val item = CurrencyRateValue(
        symbols = "USD",
        value = 0.45687,
        isFavorite = false
    )
    val itemFav = CurrencyRateValue(
        symbols = "USD",
        value = 0.45687,
        isFavorite = true
    )
    val ratesList = listOf(item, item, itemFav, itemFav, item)

    RateTrackerAppTheme {
//        AllRatesScreen(
//            state = RateTrackerState(
//                rates = ratesList
//            )
//        )
    }
}