package com.manyacov.presentation.ui_parts

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.manyacov.ratetrackerapp.ui.utils.fontDimensionResource
import com.manyacov.ui.R

@Composable
fun ErrorBox(
    modifier: Modifier = Modifier,
    description: String,
    reload: () -> Unit = {}
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .clickable { reload() }
            .wrapContentSize(Alignment.Center)
            .padding(dimensionResource(R.dimen.space_size_16))
    ) {
        Text(
            text = description,
            color = MaterialTheme.colorScheme.error,
            fontWeight = FontWeight.Normal,
            fontSize = fontDimensionResource(id = R.dimen.text_size_14),
            textAlign = TextAlign.Center
        )
    }
}

@Preview
@Composable
fun ErrorBoxPreview() {
    ErrorBox(description = "Houston, we've had a problem")
}
