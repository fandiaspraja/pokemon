package com.fandiaspraja.pokemon.ui.dashboard

import android.util.Log
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.fandiaspraja.pokemon.core.domain.model.Pokemon
import com.fandiaspraja.pokemon.ui.detail.DetailScreen
import com.fandiaspraja.pokemon.ui.detail.DetailViewModel
import com.fandiaspraja.pokemon.ui.home.HomeScreen
import com.fandiaspraja.pokemon.ui.home.HomeViewModel
import com.fandiaspraja.pokemon.ui.login.LoginScreen
import com.fandiaspraja.pokemon.ui.login.LoginViewModel
import com.fandiaspraja.pokemon.ui.profile.ProfileScreen
import com.fandiaspraja.pokemon.ui.profile.ProfileViewModel
import com.fandiaspraja.pokemon.ui.register.RegisterScreen
import com.fandiaspraja.pokemon.ui.register.RegisterViewModel
import com.fandiaspraja.pokemon.ui.splash.SplashScreen
import com.fandiaspraja.pokemon.ui.splash.SplashViewModel
import org.koin.androidx.compose.koinViewModel


sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Login : Screen("login")
    object Register : Screen("register")
    object Home : Screen("home")
    object Profile : Screen("profile")

    object Detail : Screen("detail/{pokemonName}") { // 1. Add argument placeholder
        fun createRoute(pokemonName: String) = "detail/$pokemonName"
    }

}

@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route,
        modifier = modifier
    ) {
        composable(Screen.Splash.route) {
            val viewModel: SplashViewModel = koinViewModel()

            SplashScreen(
                viewModel = viewModel,
                onNavigateToHome = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Splash.route) { inclusive = true }
                    }
                },
                onNavigateToLogin = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Splash.route) { inclusive = true }
                    }
                }
            )
        }

        composable(Screen.Login.route) {
            val viewModel: LoginViewModel = koinViewModel()

            LoginScreen(
                viewModel = viewModel,
                onLoginSuccess = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                },
                goToRegister = {
                    navController.navigate(Screen.Register.route)
                }
            )
        }

        composable(Screen.Register.route) {
            val viewModel: RegisterViewModel = koinViewModel()

            RegisterScreen(
                viewModel = viewModel,
                onBack = { navController.popBackStack() },
                onRegisterSuccess = {
                    navController.popBackStack()
                }
            )
        }

        composable(Screen.Home.route) {
            val viewModel: HomeViewModel = koinViewModel()

            val onPokemonClick = remember<(Pokemon) -> Unit> {
                { pokemon ->
                    navController.navigate(Screen.Detail.createRoute("${pokemon.name}"))
                    navController.navigate(Screen.Detail.createRoute("${pokemon.name}"))
                }
            }

            HomeScreen(
                viewModel = viewModel,
                onClickDetail = onPokemonClick
            )
        }

        composable(
            route = Screen.Detail.route,
            arguments = listOf(navArgument("pokemonName") { type = NavType.StringType })
        ) { backStackEntry ->

            // Extract the name from the backStackEntry arguments
            val pokemonName = backStackEntry.arguments?.getString("pokemonName")

            // Get the ViewModel
            val viewModel: DetailViewModel = koinViewModel()

            // Pass the name to the DetailScreen
            if (pokemonName != null) {
                DetailScreen(
                    pokemonName = pokemonName,
                    viewModel = viewModel,
                    onBack = { navController.popBackStack() },
                )
            } else {
                // Handle the case where the name is missing
                Text("Error: Pokemon name not provided.")
            }

        }


        composable(Screen.Profile.route) {
            val viewModel: ProfileViewModel = koinViewModel()

            ProfileScreen(
                viewModel = viewModel,
                onLogout = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Profile.route) { inclusive = true }
                    }
                }
            )
        }
    }
}
