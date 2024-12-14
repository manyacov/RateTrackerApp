package com.manyacov.presentation.ui_parts

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.manyacov.ratetrackerapp.ui.utils.fontDimensionResource
import com.manyacov.ui.R

@Composable
fun EmptyDescription(
    modifier: Modifier = Modifier,
    isEmpty: Boolean,
    description: String
) {
    if (isEmpty) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .wrapContentSize(Alignment.Center)
                .padding(dimensionResource(R.dimen.space_size_16))
        ) {
            Text(
                text = description,
                fontWeight = FontWeight.Normal,
                fontSize = fontDimensionResource(id = R.dimen.text_size_14)
            )
        }
    }
}

@Preview
@Composable
fun EmptyDescriptionPreview() {
    EmptyDescription(isEmpty = true, description = "Nothing is here")
}
