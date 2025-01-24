package com.manyacov.ui.theme

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

val LocalTextDim = compositionLocalOf { TextDimensions() }

data class TextDimensions(
    val spaceSize12: TextUnit = 12.sp,
    val spaceSize14: TextUnit  = 14.sp,
    val spaceSize16: TextUnit  = 16.sp,
)