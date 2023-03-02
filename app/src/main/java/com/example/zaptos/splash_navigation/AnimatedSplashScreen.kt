package com.example.zaptos.splash_navigation

import android.content.Context
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import com.example.zaptos.R
import com.example.zaptos.models.SplashScreens
import kotlinx.coroutines.delay

@Composable
fun AnimatedSplashScreen(navController: NavHostController, context: Context) {
    var startAnimation by remember {
        mutableStateOf(false)
    }

//    val firstTime = rememberSaveable {
//        mutableStateOf(true)
//    }

    val sharedPreference =  context.getSharedPreferences("PREFERENCE_NAME",Context.MODE_PRIVATE)
    var editor = sharedPreference.edit()


    val alphaAnim = animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f,
        animationSpec = tween(
            durationMillis = 3000
        )
    )

    LaunchedEffect(key1 = true) {
        startAnimation = true
        delay(4000)
        if(sharedPreference.getString("firsttimestart", "null") == "null"){
            navController.popBackStack()
            navController.navigate(SplashScreens.Home.route)
        } else {
            navController.popBackStack()
            navController.navigate(SplashScreens.Register.route)
        }
    }
    Splash(alpha = alphaAnim.value)
}

@Composable
fun Splash(alpha: Float) {
    Box(
        modifier = Modifier
            .background(Color(0xFF1f2023))
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.zaptos),
            "Image",
            modifier = Modifier
                .fillMaxSize()
                .alpha(alpha = alpha),
        )
    }
}

@Composable
@Preview
fun SplashScreenPreview() {
    Splash(alpha = 1f)
}

@Composable
@Preview(uiMode = UI_MODE_NIGHT_YES)
fun SplashScreenDarkPreview() {
    Splash(alpha = 1f)
}