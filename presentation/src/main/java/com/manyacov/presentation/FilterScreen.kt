package com.manyacov.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
    navController: NavHostController? = null
) {
    val selectedOption = remember { mutableStateOf("Code A-Z") }

    Column(
        modifier = modifier.padding(bottom = 16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = HeaderBg)
                .padding(horizontal = 16.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            Text(
                modifier = Modifier.fillMaxWidth(),
                style = MaterialTheme.typography.titleLarge,
                text = stringResource(id = R.string.filters)
            )
        }

        Text(
            text = stringResource(id = R.string.sort_by).uppercase(),
            fontSize = fontDimensionResource(id = R.dimen.text_size_12),
            color = TextSecondary
        )

        // Code sort options
        SortOption(
            text = "Code A-Z",
            isSelected = selectedOption.value == "Code A-Z",
            onClick = { selectedOption.value = "Code A-Z" }
        )

        SortOption(
            text = "Code Z-A",
            isSelected = selectedOption.value == "Code Z-A",
            onClick = { selectedOption.value = "Code Z-A" }
        )

        SortOption(
            text = "Quote Asc.",
            isSelected = selectedOption.value == "Quote Asc.",
            onClick = { selectedOption.value = "Quote Asc." }
        )

        SortOption(
            text = "Quote Desc.",
            isSelected = selectedOption.value == "Quote Desc.",
            onClick = { selectedOption.value = "Quote Desc." }
        )

        Spacer(modifier = modifier.weight(1f))

        BlueThemeButton(label = stringResource(id = R.string.apply))
    }
}

@Preview
@Composable
fun FilterScreenPreview() {
    RateTrackerAppTheme {
        FilterScreen()
    }
}