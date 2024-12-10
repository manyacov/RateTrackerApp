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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import com.manyacov.presentation.ui_filter_parts.BlueThemeButton
import com.manyacov.presentation.ui_filter_parts.SortOption
import com.manyacov.ui.theme.RateTrackerAppTheme
import com.manyacov.ui.theme.TextSecondary
import com.manyacov.ratetrackerapp.ui.utils.fontDimensionResource
import com.manyacov.ui.R
import com.manyacov.ui.theme.HeaderBg

@Composable
fun FilterScreen(
    modifier: Modifier = Modifier,
    filterType: String?,
    navController: NavHostController? = null
) {
    val selectedOption = remember { mutableStateOf("Code A-Z") }

    Column(
        modifier = modifier
            .padding(bottom = dimensionResource(id = R.dimen.space_size_16)),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = HeaderBg)
                .padding(
                    horizontal = dimensionResource(id = R.dimen.space_size_16),
                    vertical = dimensionResource(id = R.dimen.space_size_12)
                ),
            horizontalArrangement = Arrangement
                .spacedBy(dimensionResource(id = R.dimen.space_size_20)),
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
                start = dimensionResource(id = R.dimen.space_size_16),
                top = dimensionResource(id = R.dimen.space_size_16),
                end = dimensionResource(id = R.dimen.space_size_16),
                bottom = dimensionResource(id = R.dimen.space_size_14),
            ),
            text = stringResource(id = R.string.sort_by).uppercase(),
            fontSize = fontDimensionResource(id = R.dimen.text_size_12),
            color = TextSecondary
        )

        SortOptions.entries.forEach {
            val filter = stringResource(id = it.getDescriptionRes())
            SortOption(
                modifier = Modifier
                    .padding(horizontal = dimensionResource(id = R.dimen.space_size_16)),
                text = filter,
                isSelected = selectedOption.value == filter,
                onClick = { selectedOption.value = filter }
            )
        }

        // Code sort options
//        SortOption(
//            text = "Code A-Z",
//            isSelected = selectedOption.value == "Code A-Z",
//            onClick = { selectedOption.value = "Code A-Z" }
//        )
//
//        SortOption(
//            text = "Code Z-A",
//            isSelected = selectedOption.value == "Code Z-A",
//            onClick = { selectedOption.value = "Code Z-A" }
//        )
//
//        SortOption(
//            text = "Quote Asc.",
//            isSelected = selectedOption.value == "Quote Asc.",
//            onClick = { selectedOption.value = "Quote Asc." }
//        )
//
//        SortOption(
//            text = "Quote Desc.",
//            isSelected = selectedOption.value == "Quote Desc.",
//            onClick = { selectedOption.value = "Quote Desc." }
//        )

        Spacer(modifier = modifier.weight(1f))

        BlueThemeButton(
            label = stringResource(id = R.string.apply),
            onClick = { navController?.navigate("currencies/${selectedOption.value}") }
        )
    }
}

@Preview
@Composable
fun FilterScreenPreview() {
    RateTrackerAppTheme {
        FilterScreen(
            filterType = null
        )
    }
}