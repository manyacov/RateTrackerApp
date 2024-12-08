package com.manyacov.presentation.ui_parts

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.manyacov.domain.rate_tracker.model.FavoriteRatesValue
import com.manyacov.ui.theme.RateTrackerAppTheme
import com.manyacov.ui.theme.Yellow
import com.manyacov.ratetrackerapp.ui.utils.fontDimensionResource
import com.manyacov.ui.R
import com.manyacov.ui.theme.CardBg
import com.manyacov.ui.theme.roundedCorner12

@Composable
fun FavoritesPriceItem(
    modifier: Modifier = Modifier,
    item: FavoriteRatesValue
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                vertical = dimensionResource(id = R.dimen.space_size_4),
            ),
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .clip(roundedCorner12)
                .background(color = CardBg)
                .padding(
                    vertical = dimensionResource(id = R.dimen.space_size_14),
                    horizontal = dimensionResource(id = R.dimen.space_size_16)
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.weight(1f),
                text = "${item.baseSymbols}/${item.symbols}",
                fontWeight = FontWeight.Normal,
                fontSize = fontDimensionResource(id = R.dimen.text_size_14)
            )

            Text(
                modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.space_size_16)),
                text = item.value.toString(),
                fontWeight = FontWeight.Bold,
                fontSize = fontDimensionResource(id = R.dimen.text_size_16)
            )

            Icon(
                painter = painterResource(id = R.drawable.ic_star_on),
                tint = Yellow,
                contentDescription = null
            )
        }
    }
}

@Preview
@Composable
fun FavoritesPriceItemPreview() {
    val item = FavoriteRatesValue (
        baseSymbols = "EUR",
        symbols = "USD",
        value = 0.45687
    )
    RateTrackerAppTheme {
        FavoritesPriceItem(item = item)
    }
}