package com.example.zaptos.search_and_description

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.rounded.ArrowBackIos
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.example.zaptos.MasterViewModel
import com.example.zaptos.models.CartItem
import com.example.zaptos.ui.theme.BGBlue
import com.example.zaptos.ui.theme.DPColor

@Composable
fun SearchScreen(navController: NavHostController, context: Context, vm: MasterViewModel) {

    Scaffold(
        topBar = {
            Box(
                contentAlignment = Alignment.CenterStart,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .background(
                        BGBlue
                    ),
            ) {
                Row {
                    Icon(
                        imageVector = Icons.Rounded.ArrowBackIos,
                        contentDescription = "Back Button",
                        modifier = Modifier
                            .clickable {
                                navController.navigateUp()
                            }
                            .padding(start = 10.dp),
                        tint = DPColor
                    )

                    Text(
                        text = "Search",
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(start = 15.dp, bottom = 5.dp),
                        color = DPColor,
                        fontSize = 20.sp,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {

            val searchVm = SearchViewModel()
            val query = searchVm.searchTextState
            var switchVar by remember { mutableStateOf(false) }
            SearchAppBar(
                text = query,
                onTextChange = { changedText ->
                    query.value = changedText
                },
                onSearchClicked = { my_query ->
                    Toast.makeText(context, "Search Clicked:$my_query", Toast.LENGTH_LONG).show()

                    searchVm.updateSearchRes(my_query, vm)

                    Toast.makeText(context, searchVm.searchRes?.value.toString(), Toast.LENGTH_LONG)
                        .show()
                }
            )

            if (searchVm.searchRes?.value?.size!! > 0) {
                switchVar = true
            }

            if (switchVar) {
                LazyColumn {
                    items(searchVm.searchRes.value.size) {
                        searchVm.searchRes.value.forEach { item ->
                            SearchRowItem(
                                title = item.title,
                                imageurl = item.imageurl,
                                price = item.price
                            )
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun SearchAppBar(
    text: MutableState<String>,
    onTextChange: (String) -> Unit,
    onSearchClicked: (String) -> Unit,
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        elevation = AppBarDefaults.TopAppBarElevation,
        color = MaterialTheme.colors.primary,
        shape = RectangleShape
    ) {
        TextField(modifier = Modifier
            .fillMaxWidth(),
            value = text.value,
            onValueChange = {
                onTextChange(it)
            },
            placeholder = {
                Text(
                    modifier = Modifier
                        .alpha(ContentAlpha.medium),
                    text = "Search",
                    color = Color.White
                )
            },
            textStyle = TextStyle(
                fontSize = 16.sp
            ),
            singleLine = true,
            trailingIcon = {
                IconButton(
                    onClick = {
                        onSearchClicked(text.value)
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search Icon",
                        tint = DPColor
                    )
                }
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(
                onSearch = {
                    onSearchClicked(text.value)
                }
            ),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = BGBlue,
                cursorColor = DPColor,
                textColor = DPColor
            ))
    }
}


@Composable
fun SearchRowItem(title: String, imageurl: String, price: String) {
    Card(
        elevation = 5.dp,
        backgroundColor = Color.White,
        modifier = Modifier
            .height((LocalConfiguration.current.screenHeightDp / 2.5).dp)
            .width((LocalConfiguration.current.screenWidthDp).dp)
            .padding(start = 10.dp, end = 10.dp)
            .shadow(
                15.dp,
                RoundedCornerShape(20.dp),
                ambientColor = DPColor,
                spotColor = DPColor
            ),
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
                        androidx.compose.material.Text(
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