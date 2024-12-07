package com.manyacov.presentation.ui_filter_parts

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.manyacov.domain.rate_tracker.model.CurrencySymbols
import com.manyacov.ui.theme.RateTrackerAppTheme

@Composable
fun CurrencySymbolItem(
    modifier: Modifier = Modifier,
    item: CurrencySymbols
) {
    Text(
        modifier = modifier,
        text = item.symbols ?: ""
    )
}
@Preview
@Composable
fun CurrencySymbolItemPreview() {
    RateTrackerAppTheme {
        CurrencySymbolItem(
            item = CurrencySymbols("USD")
        )
    }
}