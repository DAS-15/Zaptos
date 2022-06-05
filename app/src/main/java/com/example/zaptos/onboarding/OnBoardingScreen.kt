package com.example.zaptos.onboarding

import android.content.Context
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.zaptos.splash_navigation.SplashScreens
import com.example.zaptos.ui.theme.BGBlue
import com.example.zaptos.ui.theme.DPColor
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState


@OptIn(ExperimentalPagerApi::class)
@Composable
fun OnBoardingScreen(context: Context, navController: NavHostController) {
    val pagerState = rememberPagerState()
    Column(
        modifier = Modifier.background(BGBlue)
    ) {
        HorizontalPager(
            count = 3,
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) { page ->

            if (page == 0) {
                PageOneUi()
            } else if (page == 1) {
                PageTwoUi()
            } else if (page == 2) {
                PageThreeUi()
            }
        }
        HorizontalPagerIndicator(
            pagerState = pagerState, modifier = Modifier
                .align(CenterHorizontally)
                .padding(20.dp)
        )
        AnimatedVisibility(visible = pagerState.currentPage == 2) {
            Button(
                onClick = {
//                    val loginIntent = Intent(context, LoginActivity::class.java)
//                    context.startActivity(loginIntent)

                    navController.popBackStack()
                    navController.navigate(SplashScreens.Login.route)
                }, modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 0.dp), shape = RectangleShape,
                colors = ButtonDefaults.buttonColors(backgroundColor = DPColor)
            ) {
                Text(text = "Get Started", color = BGBlue)
            }
        }
    }
}

@Composable
fun PageThreeUi() {

    Column(
        modifier = Modifier
            .background(Color(0xFF1f2023))
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Made by", fontSize = 36.sp, fontWeight = FontWeight.Bold, color = Color.Gray)
        Image(
            painterResource(id = com.example.zaptos.R.drawable.dpimage),
            contentDescription = "",
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.5f)
        )
    }
}

@OptIn(ExperimentalFoundationApi::class, androidx.compose.material.ExperimentalMaterialApi::class)
@Composable
fun PageTwoUi() {

    Column(
        modifier = Modifier
            .background(Color(0xFF1f2023))
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = com.example.zaptos.R.drawable.sneakers),
            contentDescription = "Shoes Image",
            modifier = Modifier
                .width(
                    (LocalConfiguration.current.screenWidthDp / 2).dp
                )
                .height(
                    (LocalConfiguration.current.screenWidthDp / 2).dp
                )
        )

        Spacer(modifier = Modifier.padding(40.dp))
        Text("Elegance you can wear", fontSize = 28.sp, fontWeight = FontWeight.Bold, color = Color.White)

        Spacer(modifier = Modifier.padding(10.dp))
        Text("After all, every journey begins with the perfect pair!!!", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color.White)
    }
}

@Composable
fun PageOneUi() {
    Column(
        modifier = Modifier
            .background(Color(0xFF1f2023))
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            painterResource(id = com.example.zaptos.R.drawable.zaptos),
            contentDescription = "",
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.5f)
        )
        Spacer(modifier = Modifier.padding(10.dp))
        Text(
            text = "WELCOME TO ZAPTOS",
            fontSize = 20.sp,
            fontFamily = FontFamily.Serif,
            letterSpacing = 6.sp,
            color = Color.White
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewPageOne() {
    PageOneUi()
}

@Preview(showBackground = true)
@Composable
fun PreviewPageTwo() {
    PageTwoUi()
}

@Preview(showBackground = true)
@Composable
fun PreviewPageThree() {
    PageThreeUi()
}