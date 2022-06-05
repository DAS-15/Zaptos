package com.example.zaptos.splash_navigation

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.zaptos.login_screen.LoginScreen
import com.example.zaptos.onboarding.OnBoardingScreen

@Composable
fun SetupNavGraph(
    navController: NavHostController,
    context: Context,
) {
    NavHost(
        navController = navController,
        startDestination = SplashScreens.Splash.route
    ) {
        composable(route = SplashScreens.Splash.route) {
            AnimatedSplashScreen(navController = navController)
        }
        composable(route = SplashScreens.Home.route) {
            OnBoardingScreen(navController = navController, context = context)
        }
        composable(route = SplashScreens.Login.route) {
            LoginScreen(context = context)
        }
    }
}