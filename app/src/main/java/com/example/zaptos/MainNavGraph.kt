package com.example.zaptos

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.zaptos.cart_screen.CartScreen
import com.example.zaptos.models.MainScreens
import com.example.zaptos.search_and_description.DescriptionScreen
import com.example.zaptos.search_and_description.SearchScreen

@Composable
fun MainNavGraph(
    context: Context,
    navController: NavHostController,
    vm: MasterViewModel,
) {
    NavHost(
        navController = navController,
        startDestination = MainScreens.Main.route
    ) {
        composable(route = MainScreens.Main.route) {
            MainApp(navController = navController, context = context, vm = vm)
        }
        composable(route = MainScreens.Search.route) {
            SearchScreen(navController = navController, context = context, vm = vm)
        }
        composable(route = MainScreens.Description.route) {
            DescriptionScreen(navController = navController, context = context, vm = vm)
        }
        composable(route = MainScreens.Cart.route) {
            CartScreen(navController = navController, context = context, vm = vm)
        }
    }
}