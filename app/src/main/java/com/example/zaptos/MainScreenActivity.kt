package com.example.zaptos

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberImagePainter
import com.example.docode.Repository.MainRepository
import com.example.zaptos.models.CartItem
import com.example.zaptos.models.MainScreens
import com.example.zaptos.ui.theme.BGBlue
import com.example.zaptos.ui.theme.DPColor
import com.example.zaptos.ui.theme.ZaptosTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Job


class CartList {
    companion object {
        val cartList = mutableListOf<CartItem>()
    }
}

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

                val job = Job()
                val vm = MasterViewModel(job)
                vm.getNikeData(this, MainRepository(), job)
                vm.getAdidasData(this, MainRepository(), job)
                vm.getPumaData(this, MainRepository(), job)

                val navController = rememberNavController()
//                    val sharedPreference = getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE)
                MainNavGraph(navController = navController, context = this, vm = vm)
            }
        }
    }
}


@Composable
fun MainApp(
    context: Context,
    navController: NavHostController,
    vm: MasterViewModel
) {
    var navigateClick = remember { mutableStateOf(false) }
    val offSetAnim by animateDpAsState(targetValue = if (navigateClick.value) 253.dp else 0.dp)
    val scaleAnim by animateFloatAsState(targetValue = if (navigateClick.value) 0.6f else 1.0f)

    val myScrollState = rememberScrollState()
//    val scrollColorState by remember {
//        if (myScrollState.value > 0) {
//            mutableStateOf(
//                Brush.linearGradient(
//                    colors = listOf(
//                        DPColor,
//                        Color.White
//                    )
//                )
//            )
//        } else {
//            mutableStateOf(Brush.linearGradient(
//                colors = listOf(
//                    BGBlue,
//                    BGBlue
//                )
//            ))
//        }
//    }


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
                                navigateClick.value = !navigateClick.value
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
                                navController.navigate(MainScreens.Search.route)
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
                                navController.navigate(MainScreens.Cart.route)
                            }
                            .height((LocalConfiguration.current.screenHeightDp / 15).dp)
                            .width((LocalConfiguration.current.screenWidthDp / 12).dp),
                        tint = DPColor
                    )
                }
            },
            backgroundColor = BGBlue
        ) {
            NavigationDrawer(context, navController)
            MainScreen(
                navigateClick,
                offSetAnim,
                scaleAnim,
                myScrollState,
                vm,
                navController,
                context
            )
        }
    }
}


@Composable
fun MainScreen(
    navigateClick: MutableState<Boolean>,
    offSetAnim: Dp,
    scaleAnim: Float,
    myScrollState: ScrollState,
    vm: MasterViewModel,
    navController: NavHostController,
    context: Context,
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .scale(scaleAnim)
            .offset(x = offSetAnim)
            .clip(
                if (navigateClick.value) RoundedCornerShape(30.dp) else RoundedCornerShape(
                    0.dp
                )
            )
            .background(BGBlue)
            .verticalScroll(myScrollState)
//            .clickable {
//                if (navigateClick.value) {
//                    navigateClick.value = false
//                }
//            }
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
            if (vm.nikeList?.value?.isNotEmpty() == true) {
                items(10) { i ->
                    RowItem(
                        vm.nikeList.value[i].title.toString(),
                        vm.nikeList.value[i].imageurl.toString(),
                        vm.nikeList.value[i].price.toString(),
                        navController,
                        context,
                        vm,
                        i,
                        "Nike"
                    )
                }
            } else {
                item {
                    Box(
                        modifier = Modifier
                            .height(360.dp)
                            .fillMaxWidth()
                            .padding((LocalConfiguration.current.screenWidthDp / 2.2).dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
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
            if (vm.adidasList?.value?.isNotEmpty() == true) {
                items(10) { i ->
                    RowItem(
                        vm.adidasList.value[i].title.toString(),
                        vm.adidasList.value[i].imageurl.toString(),
                        vm.adidasList.value[i].price.toString(),
                        navController,
                        context,
                        vm,
                        i,
                        "Adidas"
                    )
                }
            } else {
                item {
                    Box(
                        modifier = Modifier
                            .height(360.dp)
                            .fillMaxWidth()
                            .padding((LocalConfiguration.current.screenWidthDp / 2.2).dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
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
            if (vm.pumaList?.value?.isNotEmpty() == true) {
                items(10) { i ->
                    RowItem(
                        vm.pumaList.value[i].title.toString(),
                        vm.pumaList.value[i].imageurl.toString(),
                        vm.pumaList.value[i].price.toString(),
                        navController,
                        context,
                        vm,
                        i,
                        "Puma"
                    )
                }
            } else {
                item {
                    Box(
                        modifier = Modifier
                            .height(360.dp)
                            .fillMaxWidth()
                            .padding((LocalConfiguration.current.screenWidthDp / 2.2).dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
            }
        }
        Spacer(modifier = Modifier.padding(25.dp))
    }

}

class RowItemData {

    companion object {
        var index = 0
        var type = "Nike"
        var quantity = 1
    }
}


@Composable
fun RowItem(
    title: String,
    imageurl: String,
    price: String,
    navController: NavHostController,
    context: Context,
    vm: MasterViewModel,
    index: Int,
    type: String,
) {

    Card(
        elevation = 5.dp,
        backgroundColor = Color.White,
        modifier = Modifier
            .height((LocalConfiguration.current.screenHeightDp / 2.5).dp)
            .width((LocalConfiguration.current.screenWidthDp / 1.6).dp)
            .padding(start = 10.dp, end = 10.dp)
            .shadow(
                15.dp,
                RoundedCornerShape(20.dp),
                ambientColor = DPColor,
                spotColor = DPColor
            )
            .clickable {
                RowItemData.index = index
                RowItemData.type = type
                navController.navigate(MainScreens.Description.route)
            },
        shape = RoundedCornerShape(20.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.BottomCenter
        ) {
            val painter = rememberImagePainter(data = imageurl)

            Image(
                painter = painter,
                contentDescription = "$title item",
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 110.dp),
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
            ) {
                Card(
                    modifier = Modifier.fillMaxSize(),
                    backgroundColor = DPColor,
                    elevation = 5.dp
                ) {
                    Column {
                        Text(
                            text = "$title \n\n $price",
                            color = BGBlue,
                            fontSize = 16.sp,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(top = 20.dp),
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}


@Composable
fun NavigationDrawer(context: Context, navController: NavHostController) {

    val auth = Firebase.auth
    val currentUser = auth.currentUser

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        BGBlue,
                        Color.Black
                    )
                )
            )
    ) {
        NavigationItem(
            resId = Icons.Rounded.Person,
            text = "Profile",
            topPadding = 145.dp
        ) {}
        NavigationItem(
            resId = Icons.Rounded.ShoppingCart,
            text = "Cart"
        ) {
            navController.navigate(MainScreens.Cart.route)
        }
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
                .fillMaxHeight()
                .clickable {
                    auth.signOut()
                    val signoutintent = Intent(context, MainActivity::class.java)
                    val sharedPreference = context.getSharedPreferences(
                        "PREFERENCE_NAME",
                        Context.MODE_PRIVATE
                    )
                    val editor = sharedPreference.edit()
                    editor.putString("loggedout", "true")
                    editor.commit()
                    context.startActivity(signoutintent)
                },
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
