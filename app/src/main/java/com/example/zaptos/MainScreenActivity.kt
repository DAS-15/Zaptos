package com.example.zaptos

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.rounded.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.zaptos.ui.theme.BGBlue
import com.example.zaptos.ui.theme.DPColor
import com.example.zaptos.ui.theme.ZaptosTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController

class MainScreenActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ZaptosTheme {

                val systemUiController = rememberSystemUiController()
                val useDarkIcons = MaterialTheme.colors.isLight

                systemUiController.setSystemBarsColor(
                    color = BGBlue,
                    darkIcons = useDarkIcons
                )

                NavigationDrawer()
                MainApp()

            }
        }
    }
}


@Composable
fun MainApp() {
    var navigateClick by remember { mutableStateOf(false) }
    val offSetAnim by animateDpAsState(targetValue = if (navigateClick) 253.dp else 0.dp)
    val scaleAnim by animateFloatAsState(targetValue = if (navigateClick) 0.6f else 1.0f)

    val myScrollState = rememberScrollState()
    val scrollColorState by remember {
        if (myScrollState.value > 0) {
            mutableStateOf(Color.Transparent.copy(alpha = 0.5f))
        } else {
            mutableStateOf(BGBlue)
        }
    }


    // A surface container using the 'background' color from the theme
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {
        Scaffold(
            topBar = {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.08f)
                        .background(BGBlue),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Menu,
                        contentDescription = "NavDrawer",
                        modifier = Modifier
                            .padding(start = 10.dp, end = 5.dp)
                            .clickable {
                                navigateClick = !navigateClick
                            }
                            .height((LocalConfiguration.current.screenHeightDp / 15).dp)
                            .width((LocalConfiguration.current.screenWidthDp / 12).dp),
                        tint = DPColor
                    )

                    Text(
                        text = "Home",
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 15.dp, top = 5.dp, bottom = 5.dp),
                        textAlign = TextAlign.Start,
                        color = DPColor
                    )

                    Icon(
                        imageVector = Icons.Rounded.Search,
                        contentDescription = "Search Icon",
                        modifier = Modifier
                            .padding(start = 5.dp, end = 15.dp)
                            .clickable {

                            }
                            .height((LocalConfiguration.current.screenHeightDp / 15).dp)
                            .width((LocalConfiguration.current.screenWidthDp / 12).dp),
                        tint = DPColor
                    )

                    Icon(
                        imageVector = Icons.Rounded.ShoppingCart,
                        contentDescription = "Cart Icon",
                        modifier = Modifier
                            .padding(start = 5.dp, end = 10.dp)
                            .clickable {

                            }
                            .height((LocalConfiguration.current.screenHeightDp / 15).dp)
                            .width((LocalConfiguration.current.screenWidthDp / 12).dp),
                        tint = DPColor
                    )
                }
            },
            backgroundColor = BGBlue
        ) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .scale(scaleAnim)
                    .offset(x = offSetAnim)
                    .clip(
                        if (navigateClick) RoundedCornerShape(30.dp) else RoundedCornerShape(
                            0.dp
                        )
                    )
                    .background(BGBlue)
                    .verticalScroll(myScrollState)
            ) {
                Text(
                    text = "Nike",
                    color = DPColor,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = 10.dp, start = 15.dp, bottom = 5.dp)
                )
                Spacer(modifier = Modifier.padding(5.dp))
                LazyRow {
                    items(5) {
                        RowItem("Nike")
                    }
                }

                Text(
                    text = "Adidas",
                    color = DPColor,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = 30.dp, start = 15.dp, bottom = 5.dp)
                )
                Spacer(modifier = Modifier.padding(5.dp))
                LazyRow {
                    items(5) {
                        RowItem("Adidas")
                    }
                }

                Text(
                    text = "Puma",
                    color = DPColor,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = 30.dp, start = 15.dp, bottom = 5.dp)
                )
                Spacer(modifier = Modifier.padding(5.dp))
                LazyRow {
                    items(5) {
                        RowItem("Puma")
                    }
                }
            }
        }
    }
}


@Composable
fun RowItem(s: String) {
    Card(
        elevation = 5.dp,
        backgroundColor = DPColor,
        modifier = Modifier
            .height(350.dp)
            .width((LocalConfiguration.current.screenWidthDp / 1.5).dp)
            .padding(start = 10.dp, end = 10.dp),
        shape = RoundedCornerShape(20.dp)
    ) {
//        Box(
//            modifier = Modifier.fillMaxSize(),
//            contentAlignment = Alignment.BottomCenter
//        ){
//            Image(
//                imageVector = Icons.Rounded.Error,
//                contentDescription = "$s item",
//                modifier = Modifier.fillMaxSize().height(160.dp)
//            )
//
//            Box() {
//                Card(
//                    backgroundColor = Color.White
//                ){
//                    Text(
//                        text = "$s item",
//                        color = Color.Black,
//                        fontSize = 16.sp
//                    )
//                }
//            }
//        }
    }
}


@Preview()
@Composable
fun NavigationDrawer() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BGBlue)
    ) {
        NavigationItem(
            resId = Icons.Rounded.Person,
            text = "Profile",
            topPadding = 145.dp
        ) {}
        NavigationItem(
            resId = Icons.Rounded.ShoppingCart,
            text = "Cart"
        ) {}
        NavigationItem(
            resId = Icons.Rounded.ShoppingBag,
            text = "Orders"
        ) {}
        NavigationItem(
            resId = Icons.Rounded.HelpOutline,
            text = "FAQs"
        ) {}
        NavigationItem(
            resId = Icons.Rounded.Info,
            text = "About Us"
        ) {}

        Row(
            modifier = Modifier
                .padding(start = 50.dp, bottom = 87.dp)
                .fillMaxHeight(),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.Bottom
        ) {
            Text(
                text = "Sign Out",
                color = DPColor,
                fontSize = 17.sp
            )

            Image(
                imageVector = Icons.Filled.ArrowForward,
                contentDescription = "Logout",
                colorFilter = ColorFilter.tint(DPColor)
            )
        }
    }
}


@Composable
fun NavigationItem(
    resId: ImageVector,
    text: String,
    topPadding: Dp = 20.dp,
    destination: String = "",
    itemClicked: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 38.dp, top = topPadding)
            .clickable { itemClicked(destination) }
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Icon(
                imageVector = resId,
                contentDescription = "Item Image",
                modifier = Modifier.size(30.dp),
                tint = DPColor
            )
            Text(
                text = text,
                color = DPColor,
                fontSize = 16.sp
            )
        }

        Box(
            modifier = Modifier
                .padding(start = 35.dp, top = 26.dp, bottom = 16.dp)
                .size(120.dp, 0.5.dp)
                .background(Color.Gray)
        )
    }
}
