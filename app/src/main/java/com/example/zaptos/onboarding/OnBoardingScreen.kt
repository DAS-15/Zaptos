package com.example.zaptos.onboarding

import android.content.Context
import android.content.Intent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState

val selectedSites = mutableListOf<String>()


@OptIn(ExperimentalPagerApi::class)
@Composable
fun OnBoardingScreen(context: Context) {
    val pagerState = rememberPagerState()
    Column() {
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
                PageTwoUi(context)
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
                }, modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 0.dp), shape = RectangleShape
            ) {
                Text(text = "Get Started")
            }
        }
    }
}

@Composable
fun PageThreeUi() {
    Column(
        modifier = Modifier.background(Color(0xFF1f2023)).fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painterResource(id = com.example.zaptos.R.drawable.zaptos), contentDescription = "", modifier = Modifier
                .fillMaxSize()
        )
    }
}

@OptIn(ExperimentalFoundationApi::class, androidx.compose.material.ExperimentalMaterialApi::class)
@Composable
fun PageTwoUi(context: Context) {

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Platforms",
            fontSize = 40.sp,
            fontWeight = FontWeight.Bold,
        )

        Spacer(modifier = Modifier.padding(10.dp))

        val data = listOf<String>(
            "Code\nForces",
            "TopCoder",
            "AtCoder",
            "CodeChef",
            "CS Academy",
            "Hacker\nRank",
            "Hacker\nEarth",
            "KickStart",
            "LeetCode"
        )

        val data_images = listOf(
            painterResource(id = com.example.zaptos.R.drawable.zaptos),
            painterResource(id = com.example.zaptos.R.drawable.zaptos),
            painterResource(id = com.example.zaptos.R.drawable.zaptos),
            painterResource(id = com.example.zaptos.R.drawable.zaptos),
            painterResource(id = com.example.zaptos.R.drawable.zaptos),
            painterResource(id = com.example.zaptos.R.drawable.zaptos),
            painterResource(id = com.example.zaptos.R.drawable.zaptos),
            painterResource(id = com.example.zaptos.R.drawable.zaptos),
            painterResource(id = com.example.zaptos.R.drawable.zaptos),
        )

        LazyVerticalGrid(
            cells = GridCells.Fixed(2),
            contentPadding = PaddingValues(6.dp),
            verticalArrangement = Arrangement.Center,
            horizontalArrangement = Arrangement.Center
        ) {
            items(data.size) { item ->
//                val selectedCard = rememberSaveable() {
//                    mutableStateOf(false)
//                }
//                val selectedCardBorder =
//                    if (selectedCard.value) {
//                        BorderStroke(2.dp, Color.Green)
//                    } else {
//                        BorderStroke(0.dp, Color.Transparent)
//                    }


                Card(
                    modifier = Modifier
                        .padding(20.dp)
                        .height(120.dp)
                        .width(120.dp),
//                        .border(selectedCardBorder, shape = RoundedCornerShape(10.dp))
//                        .clickable {
//                            selectedCard.value = !selectedCard.value
//                            if (selectedCard.value) {
//                                selectedSites.add(data[item])
//                            } else {
//                                selectedSites.remove(data[item])
//                            }
//                        },
                    backgroundColor = Color.LightGray,
                    shape = RoundedCornerShape(10.dp),
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .padding(15.dp)
                    ) {
                        Image(
                            data_images[item], contentDescription = "", modifier = Modifier
//                            .padding(20.dp)
                                .height(120.dp)
                                .width(120.dp)
                        )
                        Text(
                            text = data[item],
                            fontSize = 24.sp,
                            textAlign = TextAlign.Center,
//                        modifier = Modifier.padding(24.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun PageOneUi() {
    Column(
        modifier = Modifier.background(Color(0xFF1f2023)).fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            painterResource(id = com.example.zaptos.R.drawable.zaptos), contentDescription = "", modifier = Modifier
                .fillMaxSize()
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewPageOne() {
    PageOneUi()
}

//@Preview(showBackground = true)
//@Composable
//fun PreviewPageTwo() {
//    PageTwoUi(context)
//}

@Preview(showBackground = true)
@Composable
fun PreviewPageThree() {
    PageThreeUi()
}