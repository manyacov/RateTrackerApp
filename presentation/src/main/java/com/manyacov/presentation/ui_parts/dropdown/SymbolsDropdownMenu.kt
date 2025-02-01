package com.manyacov.presentation.ui_parts.dropdown

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.Dialog
import com.manyacov.domain.rate_tracker.model.CurrencySymbols
import com.manyacov.ui.theme.Secondary
import com.manyacov.ui.theme.roundedCorner8
import com.manyacov.ui.R
import com.manyacov.ui.theme.LocalDim
import com.manyacov.ui.theme.RateTrackerAppTheme
import com.manyacov.ui.theme.roundedCorner12

@Composable
fun SymbolsDropdownMenu(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    items: List<CurrencySymbols>,
    selectedIndex: Int = 0,
    onItemSelected: (index: Int, item: CurrencySymbols) -> Unit = { _, _ -> },
) {
    var expanded by remember { mutableStateOf(false) }

    val outlinedTextFieldColors = TextFieldDefaults.colors(
        focusedContainerColor = MaterialTheme.colorScheme.background,
        unfocusedContainerColor = MaterialTheme.colorScheme.background,
        focusedIndicatorColor = Color.Transparent,
        unfocusedIndicatorColor = Color.Transparent,
        focusedTrailingIconColor = MaterialTheme.colorScheme.primary,
        unfocusedTrailingIconColor = MaterialTheme.colorScheme.primary,
    )

    Box(
        modifier = modifier
            .clip(roundedCorner8)
            .border(
                border = BorderStroke(LocalDim.current.spaceSize1, Secondary),
                shape = roundedCorner8
            )
    ) {
        OutlinedTextField(
            textStyle = MaterialTheme.typography.displayMedium,
            value = items.getOrNull(selectedIndex)?.symbols.orEmpty(),
            enabled = enabled,
            modifier = modifier
                .fillMaxWidth()
                .clip(roundedCorner8),
            trailingIcon = {
                Icon(
                    painter = if (expanded) {
                        painterResource(id = R.drawable.ic_up)
                    } else {
                        painterResource(id = R.drawable.ic_down)
                    },
                    contentDescription = null,
                )
            },
            onValueChange = { },
            readOnly = true,
            colors = outlinedTextFieldColors
        )

        Surface(
            modifier = Modifier
                .fillMaxSize()
                .clip(roundedCorner8)
                .clickable(enabled = enabled) { expanded = true }
                .testTag("symbols_dropdown_menu_surface"),
            color = Color.Transparent,
        ) {}
    }

    if (expanded) {
        Dialog(onDismissRequest = { expanded = false }) {
            Surface(
                modifier = Modifier.padding(LocalDim.current.spaceSize16),
                shape = roundedCorner12,
            ) {
                val listState = rememberLazyListState()
                if (selectedIndex > -1) {
                    LaunchedEffect("ScrollToSelected") {
                        listState.scrollToItem(index = selectedIndex)
                    }
                }

                LazyColumn(
                    modifier = Modifier.fillMaxWidth(),
                    state = listState
                ) {
                    itemsIndexed(items) { index, item ->
                        val selectedItem = index == selectedIndex
                        CurrencyDropdownMenuItem(
                            modifier = modifier.testTag("item_${item.symbols}"),
                            text = item.symbols,
                            selected = selectedItem,
                            onClick = {
                                onItemSelected(index, item)
                                expanded = false
                            },
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun SymbolsDropdownMenuPreview() {
    RateTrackerAppTheme {
        SymbolsDropdownMenu(
            items = listOf(
                CurrencySymbols("USD"),
                CurrencySymbols("EUR"),
                CurrencySymbols("BYN"),
            )
        )
    }
}