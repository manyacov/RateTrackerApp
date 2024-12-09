package com.manyacov.ratetrackerapp.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import kotlinx.serialization.Serializable

@Serializable
data class BottomNav(
    val path: String,
    @StringRes val nameRes: Int,
    @DrawableRes val iconRes: Int
)