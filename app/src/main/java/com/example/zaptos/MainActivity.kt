package com.example.zaptos

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.docode.Repository.MainRepository
import com.example.zaptos.models.SplashScreens
import com.example.zaptos.splash_navigation.SetupNavGraph
import com.example.zaptos.ui.theme.BGBlue
import com.example.zaptos.ui.theme.ZaptosTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Job

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ZaptosTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colors.background
                    ) {

                        val systemUiController = rememberSystemUiController()
                        val useDarkIcons = MaterialTheme.colors.isLight

                        systemUiController.setSystemBarsColor(
                            color = BGBlue,
                            darkIcons = useDarkIcons
                        )

                        val sharedPreference = getSharedPreferences(
                            "PREFERENCE_NAME",
                            Context.MODE_PRIVATE
                        )

                        val auth = Firebase.auth
                        val currentUser = auth.currentUser

                        if (currentUser != null) {
                            val mainScreenIntent = Intent(this, MainScreenActivity::class.java)
                            startActivity(mainScreenIntent)
                        } else {
                            val navController = rememberNavController()
                            SetupNavGraph(navController = navController, this)

                            if (sharedPreference.getString("loggedout", "null") == "true") {
                                navController.popBackStack()
                                navController.navigate(SplashScreens.Login.route)
                            }
                        }

//                        if(sharedPreference.getString("firsttimestart", "null") == "null") {
//                            val navController = rememberNavController()
//                            SetupNavGraph(navController = navController, this)
//                        } else {
////                            val mainIntent = Intent(this, MainScreenActivity::class.java)
////                            startActivity(mainIntent)
//
//                            val navController = rememberNavController()
//                            SetupNavGraph(navController = navController, this)
//                            navController.popBackStack()
//                            navController.navigate(SplashScreens.Login.route)
//                        }
                    }
                }
            }
        }
    }
}
