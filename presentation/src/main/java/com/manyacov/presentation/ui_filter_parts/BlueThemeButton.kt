package com.manyacov.presentation.ui_filter_parts

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.manyacov.ui.theme.Primary
import com.manyacov.ui.theme.RateTrackerAppTheme
import com.manyacov.ui.theme.roundedCorner20
import com.manyacov.ratetrackerapp.ui.utils.fontDimensionResource
import com.manyacov.ui.R

@Composable
fun BlueThemeButton(
    modifier: Modifier = Modifier,
    label: String = "",
    onClick: () -> Unit = {}
) {
    Button(
        modifier = modifier
            .fillMaxWidth()
            .height(dimensionResource(id = R.dimen.space_size_40))
            .background(
                color = Primary,
                shape = roundedCorner20
            ),
        shape = roundedCorner20,
        contentPadding = PaddingValues(),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent
        ),
        //enabled = enabled,
        onClick = onClick
    ) {
        Text(
            text = label,
            fontSize = fontDimensionResource(id = R.dimen.text_size_14),
            color = Color.White,
            fontWeight = FontWeight.Normal,
        )
    }
}

@Preview
@Composable
fun BlueThemeButtonPreview() {
    RateTrackerAppTheme {
        BlueThemeButton(
            modifier = Modifier
                .padding(horizontal = dimensionResource(id = R.dimen.space_size_16)),
            label = stringResource(id = R.string.apply)
        )
    }
}