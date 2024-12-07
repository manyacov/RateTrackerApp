package com.manyacov.presentation.ui_parts

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.manyacov.ui.theme.RateTrackerAppTheme
import com.manyacov.ui.R
import com.manyacov.ui.theme.DefaultBg
import com.manyacov.ui.theme.Primary
import com.manyacov.ui.theme.Secondary
import com.manyacov.ui.theme.roundedCorner8

@Composable
fun FilterItem(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    Box(
        modifier = modifier
            .size(48.dp)
            .clip(shape = roundedCorner8)
            .border(border = BorderStroke(1.dp, Secondary), shape = roundedCorner8)
            .background(DefaultBg)
            .clickable { onClick() }
            .padding(12.dp)
    ) {
        Icon(
            modifier = modifier.size(24.dp),
            painter = painterResource(id = R.drawable.ic_filter),
            tint = Primary,
            contentDescription = ""
        )
    }
}

@Preview
@Composable
fun FilterItemPreview() {
    RateTrackerAppTheme {
        FilterItem()
    }
}