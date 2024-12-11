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
import com.manyacov.ratetrackerapp.ui.utils.fontDimensionResource
import com.manyacov.ui.R

@Composable
fun EmptyDescription(isEmpty: Boolean, description: String) {
    if (isEmpty) {
        Box(
            modifier = Modifier
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