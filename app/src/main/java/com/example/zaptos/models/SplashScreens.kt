package com.example.zaptos.models


sealed class SplashScreens(val route: String) {
    object Splash : SplashScreens("splash_screen")
    object Home : SplashScreens("home_screen")
    object Login : SplashScreens("login_screen")
    object Register : SplashScreens("register_screen")
}