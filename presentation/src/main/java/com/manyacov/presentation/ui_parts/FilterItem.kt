package com.manyacov.presentation.ui_parts

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.manyacov.ui.theme.RateTrackerAppTheme
import com.manyacov.ui.R
import com.manyacov.ui.theme.LocalDim
import com.manyacov.ui.theme.roundedCorner8

@Composable
fun FilterItem(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    Box(
        modifier = modifier
            .size(LocalDim.current.spaceSize48)
            .clip(shape = roundedCorner8)
            .border(
                border = BorderStroke(
                    LocalDim.current.spaceSize1,
                    MaterialTheme.colorScheme.secondary
                ), shape = roundedCorner8
            )
            .background(MaterialTheme.colorScheme.background)
            .clickable { onClick() }
            .padding(LocalDim.current.spaceSize12)
    ) {
        Icon(
            modifier = modifier.size(LocalDim.current.spaceSize24),
            painter = painterResource(id = R.drawable.ic_filter),
            tint = MaterialTheme.colorScheme.primary,
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