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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.manyacov.domain.rate_tracker.model.FavoriteRatesValue
import com.manyacov.presentation.ui_parts.EmptyDescription
import com.manyacov.presentation.ui_parts.ErrorBox
import com.manyacov.presentation.ui_parts.FavoritesPriceItem
import com.manyacov.presentation.ui_parts.Loader
import com.manyacov.presentation.ui_parts.NoInternetLine
import com.manyacov.presentation.utils.handleError
import com.manyacov.presentation.utils.isInternetAvailable
import com.manyacov.ui.theme.LocalDim
import com.manyacov.ui.theme.Outline

@Composable
fun FavoritesScreen(
    modifier: Modifier = Modifier,
    viewModel: FavoritesViewModel
) {
    val state = viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.getFavoritesList()
    }

    FavoritesScreen(
        state = state.value,
        modifier = modifier,
        removeFavoritePair = { base, symbols ->
            viewModel.removeFavoritePair(base, symbols)
        },
        reloadFavorites = { viewModel.getFavoritesList() }
    )
}

@Composable
fun FavoritesScreen(
    state: FavoritesTrackerState,
    modifier: Modifier = Modifier,
    removeFavoritePair: (String, String) -> Unit = { _, _ -> },
    reloadFavorites: () -> Unit = {}
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
                text = stringResource(id = R.string.favorites)
            )
        }

        Spacer(
            modifier = Modifier
                .height(LocalDim.current.spaceSize1)
                .fillMaxWidth()
                .background(color = Outline)
        )

        NoInternetLine(isInternetAvailable(LocalContext.current))

        Loader(state.isLoading)

        if (state.error != null) {
            ErrorBox(
                description = stringResource(id = state.error.handleError()),
                reload = reloadFavorites
            )
        } else {
            EmptyDescription(
                isEmpty = state.listFavorites.isEmpty() && !state.isLoading,
                description = stringResource(R.string.favorites_empty),
                reload = reloadFavorites
            )

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(LocalDim.current.spaceSize16)
            ) {
                items(state.listFavorites) { item ->
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
                .height(LocalDim.current.spaceSize1)
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