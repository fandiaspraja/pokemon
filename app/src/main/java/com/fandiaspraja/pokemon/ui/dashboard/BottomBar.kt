package com.fandiaspraja.pokemon.ui.dashboard


import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Person


@Composable
fun BottomBar(
    navController: NavController,
    currentRoute: String?
) {

    val items = listOf(
        Screen.Home,
        Screen.Profile
    )

    NavigationBar {

        items.forEach { screen ->

            val icon = when (screen) {
                Screen.Home -> Icons.Default.Home
                Screen.Profile -> Icons.Default.Person
                else -> Icons.Default.Home
            }

            NavigationBarItem(
                selected = currentRoute == screen.route,
                onClick = {
                    navController.navigate(screen.route) {
                        popUpTo(Screen.Home.route)
                        launchSingleTop = true
                    }
                },
                icon = {
                    Icon(imageVector = icon, contentDescription = screen.route)
                },
                label = {
                    Text(
                        text = screen.route.replaceFirstChar {
                            it.uppercase()
                        }
                    )
                }
            )
        }
    }
}

