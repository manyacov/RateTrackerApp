package com.manyacov.ratetrackerapp.ui

import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import com.manyacov.domain.rate_tracker.model.CurrencySymbols
import com.manyacov.presentation.ui_parts.dropdown.SymbolsDropdownMenu
import org.junit.Rule
import org.junit.Test

class SymbolsDropdownMenuTest {
    @get:Rule
    val rule = createComposeRule()

    @Test
    fun testCurrencyDropdownMenuList() {
        val symbolsList = listOf(
            CurrencySymbols("USD"),
            CurrencySymbols("EUR"),
            CurrencySymbols("BYN"),
        )

        rule.setContent {
            SymbolsDropdownMenu(
                items = symbolsList
            )
        }

        rule.onNodeWithTag("symbols_dropdown_menu_surface").assertHasClickAction()

        rule.onNodeWithTag("symbols_dropdown_menu_surface").performClick()

        symbolsList.forEach { item ->
            rule.onNodeWithTag("item_${item.symbols}").assertIsDisplayed()
        }
    }
}