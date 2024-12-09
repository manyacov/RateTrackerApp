package com.manyacov.ratetrackerapp.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.manyacov.ui.R
import com.manyacov.ui.theme.DefaultBg
import com.manyacov.ui.theme.Primary

val bottomNavItemsList = listOf(
    BottomNav(
        path = "Currencies/{filter_type}",
        nameRes = R.string.currencies,
        iconRes = R.drawable.ic_currencies
    ),
    BottomNav(
        path = "Favorites",
        nameRes = R.string.favorites,
        iconRes = R.drawable.ic_star_on
    )
)

@Composable
fun BottomNavigationBar(navController: NavHostController) {

    NavigationBar(
        containerColor = DefaultBg,
        contentColor = Primary
    ) {
        var selectedItem by remember { mutableIntStateOf(0) }

//        val items = listOf("Currencies/{filter_type}", "Favorites")
//        val icons = listOf(R.drawable.ic_currencies, R.drawable.ic_star_on)

        bottomNavItemsList.forEachIndexed { index, item ->
            NavigationBarItem(
                icon = {
                    Icon(
                        painter = painterResource(id = item.iconRes),
                        contentDescription = null
                    )
                },
                label = {
                    Text(text = stringResource(id = item.nameRes))
                },
                selected = selectedItem == index,
                onClick = {
                    selectedItem = index
                    navController.navigate(bottomNavItemsList[index])
                },
                alwaysShowLabel = true
            )
        }
    }
}
