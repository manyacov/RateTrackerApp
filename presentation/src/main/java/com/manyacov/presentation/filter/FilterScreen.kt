package com.manyacov.presentation.filter

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import com.manyacov.presentation.all_rates.AllRatesViewModel
import com.manyacov.presentation.all_rates.RateTrackerState
import com.manyacov.presentation.ui_filter_parts.BlueThemeButton
import com.manyacov.presentation.ui_filter_parts.SortOption
import com.manyacov.ui.theme.RateTrackerAppTheme
import com.manyacov.ui.theme.TextSecondary
import com.manyacov.ui.R
import com.manyacov.ui.theme.HeaderBg
import com.manyacov.ui.theme.LocalDim
import com.manyacov.ui.theme.LocalTextDim

@Composable
fun FilterScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController? = null,
    viewModel: AllRatesViewModel
) {
    val filterState = viewModel.state.collectAsState()

    FilterScreen(
        modifier = modifier,
        filterState = filterState.value,
        navController = navController,
        updateFilterOption = { filterOption ->
            viewModel.updateFilterOption(filterOption)
        }
    )
}

@Composable
fun FilterScreen(
    modifier: Modifier = Modifier,
    filterState: RateTrackerState,
    navController: NavHostController? = null,
    updateFilterOption: (SortOptions) -> Unit = {}
) {
    Column(
        modifier = modifier
            .padding(bottom = LocalDim.current.spaceSize16),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = HeaderBg)
                .padding(
                    horizontal = LocalDim.current.spaceSize16,
                    vertical = LocalDim.current.spaceSize12
                ),
            horizontalArrangement = Arrangement
                .spacedBy(LocalDim.current.spaceSize20),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier.clickable(onClick = {
                    navController?.navigateUp()
                }),
                painter = painterResource(id = R.drawable.ic_back),
                contentDescription = null
            )

            Text(
                modifier = Modifier.fillMaxWidth(),
                style = MaterialTheme.typography.titleLarge,
                text = stringResource(id = R.string.filters)
            )
        }

        Text(
            modifier = Modifier.padding(
                start = LocalDim.current.spaceSize16,
                top = LocalDim.current.spaceSize16,
                end = LocalDim.current.spaceSize16,
                bottom = LocalDim.current.spaceSize14,
            ),
            text = stringResource(id = R.string.sort_by).uppercase(),
            fontSize = LocalTextDim.current.spaceSize12,
            color = TextSecondary
        )

        SortOptions.entries.forEach {
            val title = stringResource(id = it.getDescriptionRes())

            SortOption(
                modifier = Modifier
                    .padding(horizontal = LocalDim.current.spaceSize16),
                text = title,
                isSelected = filterState.filterOption == it,
                onClick = { updateFilterOption(it) }
            )
        }

        Spacer(modifier = modifier.weight(1f))

        BlueThemeButton(
            modifier = Modifier
                .padding(horizontal = LocalDim.current.spaceSize16),
            label = stringResource(id = R.string.apply),
            onClick = {
                navController?.navigateUp()
            }
        )
    }
}

@Preview
@Composable
fun FilterScreenPreview() {
    RateTrackerAppTheme {
        FilterScreen(filterState = RateTrackerState())
    }
}