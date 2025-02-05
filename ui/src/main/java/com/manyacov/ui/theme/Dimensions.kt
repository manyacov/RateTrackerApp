package com.manyacov.ui.theme

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

val LocalDim = compositionLocalOf { Dimensions() }

data class Dimensions(
    val spaceSize1: Dp = 1.dp,
    val spaceSize4: Dp = 4.dp,
    val spaceSize8: Dp = 8.dp,
    val spaceSize12: Dp = 12.dp,
    val spaceSize14: Dp = 14.dp,
    val spaceSize16: Dp = 16.dp,
    val spaceSize18: Dp = 18.dp,
    val spaceSize20: Dp = 20.dp,
    val spaceSize24: Dp = 24.dp,
    val spaceSize40: Dp = 40.dp,
    val spaceSize48: Dp = 48.dp
)