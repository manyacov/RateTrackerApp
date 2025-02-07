package com.manyacov.presentation.ui_filter_parts

import androidx.compose.ui.Alignment
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.material3.Text
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.manyacov.ui.theme.Primary
import com.manyacov.ui.theme.RateTrackerAppTheme
import com.manyacov.ui.theme.Secondary
import com.manyacov.ui.theme.LocalDim
import com.manyacov.ui.theme.LocalTextDim

@Composable
fun SortOption(
    modifier: Modifier = Modifier,
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit = {},
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = text,
            fontWeight = FontWeight(500),
            fontSize = LocalTextDim.current.spaceSize16
        )

        RadioButton(
            modifier = Modifier.padding(LocalDim.current.spaceSize12),
            selected = isSelected,
            onClick = onClick,
            colors = RadioButtonDefaults.colors(
                selectedColor = Primary,
                unselectedColor = Secondary
            )
        )
    }
}

@Preview
@Composable
fun SortOptionSelectedPreview() {
    RateTrackerAppTheme {
        SortOption(
            text = "Selected",
            isSelected = true
        )
    }
}

@Preview
@Composable
fun SortOptionUnselectedPreview() {
    RateTrackerAppTheme {
        SortOption(
            text = "Unselected",
            isSelected = false
        )
    }
}


