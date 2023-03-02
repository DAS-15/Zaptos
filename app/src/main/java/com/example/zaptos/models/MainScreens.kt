package com.example.zaptos.models

sealed class MainScreens(val route: String) {
    object Main : SplashScreens("main_screen")
    object Search : SplashScreens("search_screen")
    object Description : SplashScreens("description_screen")
    object Cart : SplashScreens("cart_screen")
}