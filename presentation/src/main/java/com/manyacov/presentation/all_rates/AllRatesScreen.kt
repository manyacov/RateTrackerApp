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
import androidx.navigation.NavHostController
import com.manyacov.domain.rate_tracker.model.CurrencyRateValue
import com.manyacov.presentation.ui_parts.CurrencyPriceItem
import com.manyacov.presentation.ui_parts.FilterItem
import com.manyacov.presentation.ui_parts.SymbolsDropdownMenu
import com.manyacov.ui.theme.RateTrackerAppTheme
import com.manyacov.ui.R
import com.manyacov.ui.theme.HeaderBg
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.LazyPagingItems
import com.manyacov.ui.theme.Outline
import androidx.paging.compose.collectAsLazyPagingItems
import com.manyacov.domain.rate_tracker.utils.NetworkIssues
import com.manyacov.presentation.filter.getSortOptionByDescription
import com.manyacov.presentation.ui_parts.EmptyDescription
import com.manyacov.presentation.ui_parts.Loader
import com.manyacov.presentation.ui_parts.NoInternetLine
import com.manyacov.presentation.utils.isInternetAvailable


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
        viewModel.getLatestRates(state.baseSymbols, filterType.getSortOptionByDescription())
    }

    AllRatesScreen(
        state = state,
        filterType = filterType,
        ratesLazyPagingItems = ratesLazyPagingItems,
        modifier = modifier,
        navController = navController,
        selectFavorite = { symbols ->
            viewModel.selectFavorite(symbols)
        },
        changeBaseCurrency = { baseSymbols ->
            Log.println(Log.ERROR, "OOOO", baseSymbols)
            viewModel.getLatestRates(baseSymbols, filterType.getSortOptionByDescription())
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
    selectFavorite: (String) -> Unit = {},
    changeBaseCurrency: (String) -> Unit = {}
) {
    var selectedIndex by remember { mutableIntStateOf(0) }

    Column(modifier = modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = HeaderBg)
                .padding(
                    horizontal = dimensionResource(id = R.dimen.space_size_16),
                    vertical = dimensionResource(id = R.dimen.space_size_12)
                ),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.space_size_18))
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                style = MaterialTheme.typography.titleLarge,
                text = stringResource(id = R.string.currencies)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.space_size_8))
            ) {

                SymbolsDropdownMenu(
                    modifier = Modifier
                        .height(dimensionResource(id = R.dimen.space_size_48))
                        .weight(1f),
                    items = state.symbols,
                    selectedIndex = selectedIndex,
                    onItemSelected = { index, item ->
                        selectedIndex = index
                        changeBaseCurrency(item.symbols)
                    },
                )

                FilterItem(
                    onClick = { navController?.navigate("filters/$filterType") }
                )
            }
        }

        Spacer(
            modifier = Modifier
                .height(dimensionResource(id = R.dimen.space_size_1))
                .fillMaxWidth()
                .background(color = Outline)
        )

        NoInternetLine(isInternetAvailable(LocalContext.current))

        Loader(state.isLoading)

        EmptyDescription(
            isEmpty = ratesLazyPagingItems?.itemCount == 0,
            stringResource(R.string.all_rates_empty)
        )

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(dimensionResource(id = R.dimen.space_size_16))
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
        AllRatesScreen(
            state = RateTrackerState(),
            filterType = null,
            ratesLazyPagingItems = null,
            selectFavorite = {},
            changeBaseCurrency = {}
        )
    }
}