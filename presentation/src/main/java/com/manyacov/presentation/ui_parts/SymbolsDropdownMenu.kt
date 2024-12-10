package com.manyacov.presentation.ui_parts

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.manyacov.domain.rate_tracker.model.CurrencySymbols
import com.manyacov.ui.theme.LightPrimary
import com.manyacov.ui.theme.RateTrackerAppTheme
import com.manyacov.ui.theme.Secondary
import com.manyacov.ui.theme.roundedCorner8
import com.manyacov.ratetrackerapp.ui.utils.fontDimensionResource
import com.manyacov.ui.R

@Composable
fun SymbolsDropdownMenu(
    modifier: Modifier = Modifier,
    itemsList: List<CurrencySymbols> = listOf(),
    onSelect: (String) -> Unit = {}
) {
    var expanded by remember(key1 = itemsList) { mutableStateOf(false) }
    var selectedCurrency by remember(key1 = itemsList) { mutableStateOf(itemsList[0]) }

    Column(
        modifier = modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .clickable { expanded = true }
                .background(Color.White, shape = roundedCorner8)
                .border(border = BorderStroke(1.dp, Secondary), shape = roundedCorner8)
                .padding(
                    horizontal = dimensionResource(id = R.dimen.space_size_16),
                    vertical = dimensionResource(id = R.dimen.space_size_14)
                )
        ) {
            Text(
                text = selectedCurrency.symbols ?: "",
                fontSize = fontDimensionResource(id = R.dimen.text_size_14),
                modifier = Modifier.weight(1f)
            )

            Icon(
                painter = painterResource(id = R.drawable.ic_down),
                contentDescription = null
            )
        }


        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .background(Color.White, shape = roundedCorner8)
                .border(border = BorderStroke(1.dp, Secondary), shape = roundedCorner8)
                .height(150.dp),
        ) {
            itemsList.forEach { currency ->
                DropdownMenuItem(
                    text = {
                        Text(
                            text = currency.symbols ?: "",
                            fontSize = 16.sp,
                            modifier = Modifier
                                .background(
                                    if (selectedCurrency == currency) LightPrimary
                                    else Color.Transparent
                                )
                                .fillMaxWidth()
                                .height(20.dp)
                                .padding(horizontal = 16.dp)

                        )
                    },
                    onClick = {
                        selectedCurrency = currency
                        onSelect(currency.symbols)
                        expanded = false
                    },
                    modifier = modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Preview
@Composable
fun SymbolsDropdownMenuPreview() {
    val symbolList = listOf(
        CurrencySymbols("EUR"),
        CurrencySymbols("USD"),
        CurrencySymbols("JPY"),
        CurrencySymbols("GBP"),
        CurrencySymbols("AUD"),
        CurrencySymbols("AUD"),
        CurrencySymbols("AUD"),
        CurrencySymbols("AUD"),
        CurrencySymbols("AUD"),
        CurrencySymbols("DFF"),
        CurrencySymbols("AUD"),
        CurrencySymbols("AUD"),
        CurrencySymbols("AUD"),
        CurrencySymbols("AUD"),
        CurrencySymbols("AUD"),
        CurrencySymbols("DFW"),
        CurrencySymbols("AUD"),
        CurrencySymbols("AUD"),
        CurrencySymbols("AUD"),
        CurrencySymbols("AUD"),
        CurrencySymbols("AUD"),
        CurrencySymbols("AUD"),
        CurrencySymbols("AUD"),
        CurrencySymbols("AUD"),
        CurrencySymbols(")@("),
        CurrencySymbols("AUD"),
        CurrencySymbols("AUD"),
        CurrencySymbols("AUD"),
    )
    RateTrackerAppTheme {
        SymbolsDropdownMenu(itemsList = symbolList)
    }
}

