package com.example.zaptos.splash_navigation


sealed class SplashScreens(val route: String) {
    object Splash : SplashScreens("splash_screen")
    object Home : SplashScreens("home_screen")
    object Login : SplashScreens("login_screen")
}