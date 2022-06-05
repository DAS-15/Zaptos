package com.example.zaptos.splash_navigation

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.zaptos.onboarding.OnBoardingScreen

@Composable
fun SetupNavGraph(
    navController: NavHostController,
    context: Context,
    firstTime: MutableState<Boolean>
) {
    NavHost(
        navController = navController,
        startDestination = SplashScreens.Splash.route
    ) {
        composable(route = SplashScreens.Splash.route) {
            AnimatedSplashScreen(navController = navController)
        }
        composable(route = SplashScreens.Home.route) {
            OnBoardingScreen(context = context)
        }
    }
}