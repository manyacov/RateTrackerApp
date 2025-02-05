package com.manyacov.ratetrackerapp.ui

import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.manyacov.presentation.ui_parts.dropdown.CurrencyDropdownMenuItem
import com.manyacov.ui.theme.DefaultBg
import com.manyacov.ui.theme.LightPrimary
import org.junit.Rule
import org.junit.Test

class SymbolsDropdownMenuItemTest {
    @get:Rule
    val rule = createComposeRule()

    @Test
    fun testCurrencyDropdownMenuItemText() {
        rule.setContent {
            CurrencyDropdownMenuItem(
                text = "EUR",
                selected = false
            )
        }

        rule.onNodeWithText("EUR").assertIsDisplayed()
    }

    @Test
    fun testCurrencyDropdownMenuItemClick() {
        rule.setContent {
            CurrencyDropdownMenuItem(
                text = "EUR",
                selected = false
            )
        }
        rule.onNodeWithTag("dropdown_item_box").assertHasClickAction()

        rule.onNodeWithTag("dropdown_item_box").assertBackgroundColor(DefaultBg)

        rule.onNodeWithTag("dropdown_item_box").performClick()

        rule.onNodeWithTag("dropdown_item_box").assertBackgroundColor(LightPrimary)

        rule.onNodeWithTag("dropdown_item_box").performClick()

        rule.onNodeWithTag("dropdown_item_box").assertBackgroundColor(DefaultBg)
    }
}