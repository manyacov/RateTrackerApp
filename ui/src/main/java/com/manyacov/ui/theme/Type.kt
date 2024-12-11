package com.manyacov.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.manyacov.ui.R

val interFontFamily = FontFamily(
    Font(R.font.inter_18pt_regular, FontWeight.Normal),
    Font(R.font.inter_18pt_bold, FontWeight.Bold)
)

val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = interFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),

    titleLarge = TextStyle(
        fontFamily = interFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.5.sp,
        color = TextDefault
    ),

    displayMedium = TextStyle(
        fontFamily = interFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.5.sp,
        color = TextDefault
    ),
)