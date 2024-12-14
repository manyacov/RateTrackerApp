package com.manyacov.presentation.favorites

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import com.manyacov.ui.theme.RateTrackerAppTheme
import com.manyacov.ui.R
import com.manyacov.ui.theme.HeaderBg
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import com.manyacov.domain.rate_tracker.model.FavoriteRatesValue
import com.manyacov.presentation.ui_parts.EmptyDescription
import com.manyacov.presentation.ui_parts.ErrorBox
import com.manyacov.presentation.ui_parts.FavoritesPriceItem
import com.manyacov.presentation.ui_parts.Loader
import com.manyacov.presentation.ui_parts.NoInternetLine
import com.manyacov.presentation.utils.handleError
import com.manyacov.presentation.utils.isInternetAvailable
import com.manyacov.ui.theme.Outline

@Composable
fun FavoritesScreen(
    modifier: Modifier = Modifier,
    viewModel: FavoritesViewModel
) {
    val state = viewModel.state

    LaunchedEffect(Unit) {
        viewModel.getFavoritesList()
    }

    FavoritesScreen(
        state = state,
        modifier = modifier,
        removeFavoritePair = { base, symbols ->
            viewModel.removeFavoritePair(base, symbols)
        }
    )
}

@Composable
fun FavoritesScreen(
    state: FavoritesTrackerState,
    modifier: Modifier = Modifier,
    removeFavoritePair: (String, String) -> Unit = { _, _ -> }
) {
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
                text = stringResource(id = R.string.favorites)
            )
        }

        Spacer(
            modifier = Modifier
                .height(dimensionResource(id = R.dimen.space_size_1))
                .fillMaxWidth()
                .background(color = Outline)
        )

        NoInternetLine(isInternetAvailable(LocalContext.current))

        Loader(state.isLoading)

        if (state.error != null) {
            ErrorBox(description = stringResource(id = state.error.handleError()))
        } else {
            EmptyDescription(
                isEmpty = state.listFavorites?.isEmpty() == true && !state.isLoading,
                description = stringResource(R.string.favorites_empty)
            )

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(dimensionResource(id = R.dimen.space_size_16))
            ) {
                items(state.listFavorites ?: listOf()) { item ->
                    FavoritesPriceItem(
                        item = item,
                        onClick = { base, symbols ->
                            removeFavoritePair(base, symbols)
                        }
                    )
                }
            }
        }

        Spacer(
            modifier = Modifier
                .height(dimensionResource(id = R.dimen.space_size_1))
                .fillMaxWidth()
                .background(color = Outline)
        )
    }
}

@Preview
@Composable
fun FavoritesScreenPreview() {
    val item = FavoriteRatesValue(
        baseSymbols = "USD",
        symbols = "EUR",
        value = 0.48554
    )

    RateTrackerAppTheme {
        FavoritesScreen(
            state = FavoritesTrackerState(listFavorites = List(3) { item })
        )
    }
}