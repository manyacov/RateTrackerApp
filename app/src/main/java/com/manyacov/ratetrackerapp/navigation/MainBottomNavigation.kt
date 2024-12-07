package com.manyacov.ratetrackerapp.navigation

import android.annotation.SuppressLint
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
import androidx.navigation.NavController
import com.manyacov.ui.R
import com.manyacov.ui.theme.DefaultBg
import com.manyacov.ui.theme.Primary

@SuppressLint("RestrictedApi")
@Composable
fun BottomNavigationBar(navController: NavController) {

    NavigationBar(
        containerColor = DefaultBg,
        contentColor = Primary
    ) {
        var selectedItem by remember { mutableIntStateOf(0) }

        val items = listOf("Currencies", "Favorites")
        val icons = listOf(R.drawable.ic_currencies, R.drawable.ic_star_on)

        items.forEachIndexed { index, item ->
            NavigationBarItem(
                icon = {
                    Icon(painter = painterResource(id = icons[index]), contentDescription = null)
                },
                label = { Text(item) },
                selected = selectedItem == index,
                onClick = {
                    selectedItem = index
                },
                alwaysShowLabel = false
            )
        }
    }
}
