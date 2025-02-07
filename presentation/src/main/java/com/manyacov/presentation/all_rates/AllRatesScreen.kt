package com.manyacov.presentation.all_rates

import android.annotation.SuppressLint
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
import androidx.compose.foundation.lazy.items
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
import com.manyacov.presentation.ui_parts.dropdown.SymbolsDropdownMenu
import com.manyacov.ui.theme.RateTrackerAppTheme
import com.manyacov.ui.R
import com.manyacov.ui.theme.HeaderBg
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.manyacov.ui.theme.Outline
import com.manyacov.domain.rate_tracker.model.CurrencySymbols
import com.manyacov.presentation.ui_parts.EmptyDescription
import com.manyacov.presentation.ui_parts.ErrorBox
import com.manyacov.presentation.ui_parts.Loader
import com.manyacov.presentation.utils.handleError
import com.manyacov.ui.theme.LocalDim

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun AllRatesScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController? = null,
    viewModel: AllRatesViewModel,
) {
    val state = viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.getCurrencySymbols()
    }

    AllRatesScreen(
        state = state.value,
        modifier = modifier,
        navController = navController,
        selectFavorite = { symbols ->
            viewModel.selectFavorite(symbols)
        },
        changeBaseCurrency = { base ->
            viewModel.updateSelectedSymbols(base)
        },
        reloadRates = { viewModel.reloadRates() }
    )
}

@Composable
fun AllRatesScreen(
    state: RateTrackerState,
    modifier: Modifier = Modifier,
    navController: NavHostController? = null,
    selectFavorite: (String) -> Unit = {},
    changeBaseCurrency: (CurrencySymbols) -> Unit = {},
    reloadRates: () -> Unit = {}
) {
    Column(modifier = modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = HeaderBg)
                .padding(
                    horizontal = LocalDim.current.spaceSize16,
                    vertical = LocalDim.current.spaceSize12
                ),
            verticalArrangement = Arrangement.spacedBy(LocalDim.current.spaceSize18)
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                style = MaterialTheme.typography.titleLarge,
                text = stringResource(id = R.string.currencies)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(LocalDim.current.spaceSize8)
            ) {
                val base = state.baseSymbols
                val selectedIndex = state.symbols.indexOf(base)

                SymbolsDropdownMenu(
                    modifier = Modifier
                        .height(LocalDim.current.spaceSize48)
                        .weight(1f),
                    items = state.symbols,
                    selectedIndex = selectedIndex,
                    onItemSelected = { _, item ->
                        changeBaseCurrency(CurrencySymbols(item.symbols))
                    },
                )

                FilterItem(
                    onClick = { navController?.navigate("filters") }
                )
            }
        }

        Spacer(
            modifier = Modifier
                .height(LocalDim.current.spaceSize1)
                .fillMaxWidth()
                .background(color = Outline)
        )

        Loader(state.isLoading)

        if (state.error != null) {
            ErrorBox(
                description = stringResource(id = state.error.handleError()),
                reload = reloadRates
            )
        } else {
            EmptyDescription(
                isEmpty = state.ratesList.isEmpty(),
                description = stringResource(R.string.all_rates_empty),
                reload = reloadRates
            )

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(LocalDim.current.spaceSize16)
            ) {

                items(state.ratesList, key = { it.id }) { item ->
                    CurrencyPriceItem(
                        item = item,
                        onClick = { symbols -> selectFavorite(symbols) }
                    )
                }
            }
        }

        Spacer(
            modifier = Modifier
                .height(LocalDim.current.spaceSize1)
                .fillMaxWidth()
                .background(color = Outline)
        )
    }
}

@Preview
@Composable
fun AllRatesScreenPreview() {
    val item = CurrencyRateValue(
        id = 0,
        symbols = "USD",
        value = 0.45687,
        isFavorite = false
    )
    val itemFav = CurrencyRateValue(
        id = 1,
        symbols = "USD",
        value = 0.45687,
        isFavorite = true
    )
    val ratesList = listOf(item, item, itemFav, itemFav, item)

    RateTrackerAppTheme {
        AllRatesScreen(
            state = RateTrackerState(ratesList = ratesList),
            selectFavorite = {},
            changeBaseCurrency = {},
        )
    }
}

@Preview
@Composable
fun AllRatesScreenEmptyPreview() {
    RateTrackerAppTheme {
        AllRatesScreen(
            state = RateTrackerState(),
            selectFavorite = {},
            changeBaseCurrency = {}
        )
    }
}