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
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.manyacov.ui.theme.RateTrackerAppTheme
import com.manyacov.ui.R
import com.manyacov.ui.theme.HeaderBg
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.manyacov.domain.rate_tracker.model.FavoriteRatesValue
import com.manyacov.presentation.ui_parts.FavoritesPriceItem
import com.manyacov.ui.theme.Outline

@Composable
fun FavoritesScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController? = null,
    viewModel: FavoritesViewModel
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    FavoritesScreen(
        state = state,
        modifier = modifier,
        navController = navController
    )
}

@Composable
fun FavoritesScreen(
    state: FavoritesTrackerState,
    modifier: Modifier = Modifier,
    navController: NavHostController? = null,
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
                text = stringResource(id = R.string.favorites)
            )
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
            items(state.listFavorites ?: listOf()) { item ->
                FavoritesPriceItem(item = item)
            }
        }
    }
}

@Preview
@Composable
fun AllRatesScreenPreview() {
    val item = FavoriteRatesValue(
        baseSymbols = "USD",
        symbols = "EUR",
        value = 0.48554
    )

    val list = listOf(item, item, item)

    RateTrackerAppTheme {
        FavoritesScreen(
            state = FavoritesTrackerState(
                listFavorites = list
            )
        )
    }
}