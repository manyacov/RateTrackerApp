package com.manyacov.presentation.ui_parts

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.manyacov.domain.rate_tracker.model.FavoriteRatesValue
import com.manyacov.ui.theme.RateTrackerAppTheme
import com.manyacov.ui.theme.Yellow
import com.manyacov.ui.R
import com.manyacov.ui.theme.CardBg
import com.manyacov.ui.theme.LocalDim
import com.manyacov.ui.theme.LocalTextDim
import com.manyacov.ui.theme.roundedCorner12

@Composable
fun FavoritesPriceItem(
    modifier: Modifier = Modifier,
    item: FavoriteRatesValue,
    onClick: (String, String) -> Unit = { _, _ -> }
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick(item.baseSymbols, item.symbols) }
            .padding(
                vertical = LocalDim.current.spaceSize4,
            ),
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .clip(roundedCorner12)
                .background(color = CardBg)
                .padding(
                    vertical = LocalDim.current.spaceSize14,
                    horizontal = LocalDim.current.spaceSize16
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.weight(1f),
                text = "${item.baseSymbols}/${item.symbols}",
                fontWeight = FontWeight.Normal,
                fontSize = LocalTextDim.current.spaceSize14
            )

            Text(
                modifier = Modifier.padding(horizontal = LocalDim.current.spaceSize16),
                text = item.value.toString(),
                fontWeight = FontWeight.Bold,
                fontSize = LocalTextDim.current.spaceSize16
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