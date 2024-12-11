package com.manyacov.presentation.ui_parts

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.manyacov.ratetrackerapp.ui.utils.fontDimensionResource
import com.manyacov.ui.R
import com.manyacov.ui.theme.LightPrimary

@Composable
fun NoInternetLine(isInternetAvailable: Boolean) {
    if (!isInternetAvailable) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(LightPrimary)
                .padding(
                    horizontal = dimensionResource(id = R.dimen.space_size_12),
                    vertical = dimensionResource(id = R.dimen.space_size_8),
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(R.string.no_internet),
                fontSize = fontDimensionResource(id = R.dimen.text_size_12)
            )
        }
    }
}

@Preview
@Composable
fun NoInternetLinePreview() {
    NoInternetLine(false)
}