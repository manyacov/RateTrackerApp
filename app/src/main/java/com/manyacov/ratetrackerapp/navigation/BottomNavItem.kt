package com.manyacov.ratetrackerapp.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

open class BottomNavItem(
    val path: String,
    @StringRes val titleRes: Int,
    @DrawableRes val iconRes: Int
)