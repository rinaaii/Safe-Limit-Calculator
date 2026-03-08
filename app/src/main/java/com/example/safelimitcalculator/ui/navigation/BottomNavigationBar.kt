package com.example.safelimitcalculator.ui.navigation

import androidx.compose.runtime.getValue
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.safelimitcalculator.ui.theme.LocalAppTheme.colors
import com.example.safelimitcalculator.ui.theme.LocalAppTheme.typography

@Composable
fun BottomNavigationBar(navController: NavController) {
    val items = listOf(
        BottomNavItem.Home,
        BottomNavItem.Expenses,
        BottomNavItem.Payments,
        BottomNavItem.Analytics,
        BottomNavItem.Settings
    )

    NavigationBar(
        containerColor = colors.surface,
        contentColor = colors.textSecondary
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        items.forEach { item ->
            val selected = currentRoute == item.route

            NavigationBarItem(
                selected = selected,
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.startDestinationId) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = {
                    Icon(
                        painter = painterResource(id = item.icon),
                        contentDescription = item.label,
                        tint = if (selected) colors.iconActive else colors.iconInactive
                    )
                },
                label = {
                    Text(
                        text = item.label,
                        style = typography.body,
                        color = if (selected) colors.textPrimary else colors.textSecondary
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = colors.iconActive,
                    unselectedIconColor = colors.iconInactive,
                    selectedTextColor = colors.textPrimary,
                    unselectedTextColor = colors.textSecondary,
                    indicatorColor = colors.primaryAccent.copy(alpha = 0.1f)
                )
            )
        }
    }
}

