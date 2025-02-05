package com.manyacov.presentation.ui_parts

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.manyacov.ui.theme.LocalDim
import com.manyacov.ui.theme.LocalTextDim

@Composable
fun EmptyDescription(
    modifier: Modifier = Modifier,
    isEmpty: Boolean,
    description: String,
    reload: () -> Unit = {}
) {
    if (isEmpty) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .clickable { reload() }
                .wrapContentSize(Alignment.Center)
                .padding(LocalDim.current.spaceSize16)
        ) {
            Text(
                text = description,
                fontWeight = FontWeight.Normal,
                fontSize = LocalTextDim.current.spaceSize14,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview
@Composable
fun EmptyDescriptionPreview() {
    EmptyDescription(isEmpty = true, description = "Nothing is here")
}
