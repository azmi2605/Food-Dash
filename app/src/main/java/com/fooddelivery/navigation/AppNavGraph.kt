package com.fooddelivery.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.fooddelivery.ui.screens.*
import com.fooddelivery.viewmodel.FoodViewModel

@Composable
fun AppNavGraph() {
    val navController = rememberNavController()
    val viewModel: FoodViewModel = viewModel()

    NavHost(
        navController    = navController,
        startDestination = Routes.SPLASH
    ) {
        composable(Routes.SPLASH) {
            SplashScreen(navController = navController)
        }

        composable(Routes.HOME) {
            HomeScreen(
                navController = navController,
                viewModel     = viewModel
            )
        }

        composable(
            route     = Routes.DETAIL,
            arguments = listOf(navArgument("foodId") { type = NavType.IntType })
        ) { backStackEntry ->
            val foodId = backStackEntry.arguments?.getInt("foodId") ?: return@composable
            DetailScreen(
                navController = navController,
                viewModel     = viewModel,
                foodId        = foodId
            )
        }

        composable(Routes.CART) {
            CartScreen(
                navController = navController,
                viewModel     = viewModel
            )
        }
    }
}
