package com.manyacov.ratetrackerapp.navigation

import com.manyacov.ui.R

sealed class NavItem {
    object Currencies :
        BottomNavItem(
            path = NavPath.CURRENCIES,
            titleRes = R.string.currencies,
            iconRes = R.drawable.ic_currencies
        )

    object Favorites :
        BottomNavItem(
            path = NavPath.FAVORITES,
            titleRes = R.string.favorites,
            iconRes = R.drawable.ic_star_on
        )

    object Filters :
        BottomNavItem(
            path = NavPath.FILTERS,
            titleRes = R.string.filters,
            iconRes = 0
        )
}