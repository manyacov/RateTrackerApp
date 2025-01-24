package com.manyacov.presentation.ui_parts.dropdown

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import com.manyacov.ui.theme.DefaultBg
import com.manyacov.ui.theme.LightPrimary
import com.manyacov.ui.theme.LocalDim
import com.manyacov.ui.theme.RateTrackerAppTheme

@Composable
fun CurrencyDropdownMenuItem(
    modifier: Modifier = Modifier,
    text: String,
    selected: Boolean,
    onClick: () -> Unit = {},
) {
    val bgColor = when {
        selected -> LightPrimary
        else -> DefaultBg
    }

    Box(modifier = modifier
        .clickable { onClick() }
        .background(color = bgColor)
        .fillMaxWidth()
        .testTag("dropdown_item_box")
    ) {
        Text(
            modifier = Modifier
                .padding(LocalDim.current.spaceSize16),
            text = text,
            style = MaterialTheme.typography.displayMedium,
        )
    }
}

@Preview
@Composable
fun CurrencyDropdownMenuItemNotSelectedPreview() {
    RateTrackerAppTheme {
        CurrencyDropdownMenuItem(
            text = "EUR",
            selected = false
        )
    }
}

@Preview
@Composable
fun CurrencyDropdownMenuItemSelectedPreview() {
    RateTrackerAppTheme {
        CurrencyDropdownMenuItem(
            text = "EUR",
            selected = true
        )
    }
}