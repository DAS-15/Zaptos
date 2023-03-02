package com.example.zaptos.cart_screen

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.RemoveCircleOutline
import androidx.compose.material.icons.rounded.ArrowBackIos
import androidx.compose.material.icons.rounded.ShoppingCart
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.example.zaptos.MasterViewModel
import com.example.zaptos.PaymentActivity
import com.example.zaptos.ui.theme.BGBlue
import com.example.zaptos.ui.theme.DPColor
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


@Composable
fun CartScreen(navController: NavHostController, context: Context, vm: MasterViewModel) {

    val totalSum = remember {
        mutableStateOf(0)
    }

    val refreshList = remember {
        mutableStateOf(false)
    }

    val sharedPreference = context.getSharedPreferences(
        "PREFERENCE_NAME",
        Context.MODE_PRIVATE
    )

    val showCheckout = remember {
        mutableStateOf(true)
    }

    val editor = sharedPreference.edit()

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

//                    Text(
//                        text = "",
//                        fontWeight = FontWeight.Bold,
//                        modifier = Modifier.padding(start = 15.dp, bottom = 5.dp),
//                        color = DPColor,
//                        fontSize = 20.sp,
//                        textAlign = TextAlign.Center
//                    )
                }
            }
        }
    ) {

        var li: QuerySnapshot? by remember { mutableStateOf(null) }

        if (refreshList.value) {

            val db = Firebase.firestore
            val currentUser = Firebase.auth.currentUser

            db.collection("carts")
                .document("${currentUser?.uid.toString()}_cart")
                .collection("cart").get()
                .addOnSuccessListener { Obj ->
                    Log.i(
                        "FirestoreCartCartScreen",
                        "CartScreen DocumentSnapshot added with ID: $Obj"
                    )

                    li = Obj

//                    Log.i(
//                        "FirestoreCartCartScreen",
//                        "CartScreen DocumentSnapshot added with ID: $li"
//                    )
                }
                .addOnFailureListener { e ->
                    Log.i("FirestoreCart", "Error adding document", e)
                }

            refreshList.value = false
        }

        LaunchedEffect(key1 = 1) {
            val db = Firebase.firestore
            val currentUser = Firebase.auth.currentUser

            db.collection("carts")
                .document("${currentUser?.uid.toString()}_cart")
                .collection("cart").get()
                .addOnSuccessListener { Obj ->
                    Log.i(
                        "FirestoreCartCartScreen",
                        "CartScreen DocumentSnapshot added with ID: $Obj"
                    )

                    li = Obj

//                    Log.i(
//                        "FirestoreCartCartScreen",
//                        "CartScreen DocumentSnapshot added with ID: $li"
//                    )
                }
                .addOnFailureListener { e ->
                    Log.i("FirestoreCart", "Error adding document", e)
                }
        }


        showCheckout.value = !(li == null || li?.size() == 0)


        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f),
//                    .padding(it),
            ) {
                if (li == null || li?.size() == 0) {
                    item {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(top = (LocalConfiguration.current.screenHeightDp / 4).dp),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.ShoppingCart,
                                contentDescription = "Empty Cart",
                                modifier = Modifier
                                    .height((LocalConfiguration.current.screenHeightDp / 3).dp)
                                    .width(
                                        (LocalConfiguration.current.screenWidthDp / 2).dp
                                    )
                            )
                            Spacer(modifier = Modifier.padding(10.dp))
                            Text(text = "EMPTY CART!!!", fontSize = 24.sp)
                        }

                    }
                } else {

                    item {
//                    val savedCartList = rememberSaveable{
//                        mutableStateOf(mutableListOf<CartItem>())
//                    }
//
//                    savedCartList.value.addAll(CartList.cartList)

//                    val sharedPreferences =
//                        context.getSharedPreferences("PRIVATE_PREFERENCES", Context.MODE_PRIVATE)
//
//                    val li = getList(sharedPreferences, "mycartlist") as MutableList<CartItem>?
//                    li?.addAll(CartList.cartList)
//                    setList("mycartlist", li, sharedPreferences)

//                    li?.forEach { cartItem ->
//                        MyCartItem(
//                            cartItem.title,
//                            cartItem.imageurl,
//                            cartItem.price,
//                            navController,
//                            cartItem.index,
//                            cartItem.type,
//                            cartItem.quantity
//                        )
//                    }


                        if (li != null) {
                            val itemList = li?.documents
                            itemList?.sortBy { i -> i["title"].toString() }

//                            if(refreshTotalSum.value) {
//                                totalSum.value = 0
//                            }

                            itemList?.forEach { item ->

//                        if(prevItem?.get("title")?.toString() == currentItem?.get("title").toString() && prevItem?.get("size").toString() == currentItem?.get("size").toString())

                                LaunchedEffect(key1 = 2) {
                                    totalSum.value += item["price"].toString().toInt()
                                }
                                MyCartItem(
                                    title = item["title"].toString(),
                                    imageurl = item["imageurl"].toString(),
                                    price = item["price"].toString(),
                                    size = item["size"].toString(),
                                    quantity = item["quantity"] as Long,
                                    item_id = item.id,
                                    refreshList = refreshList,
                                    sharedPreference = sharedPreference
                                )
                            }
                        }
                        LaunchedEffect(key1 = 3) {
                            editor.putInt("totalsum", totalSum.value)
                            editor.commit()
                        }
                    }
                }
            }

            if (showCheckout.value) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp)
                        .background(BGBlue),
                    shape = RoundedCornerShape(topEnd = 20.dp, topStart = 20.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxSize()
                    ) {
                        Text(
                            text = "Total amount: Rs.${sharedPreference.getInt("totalsum", 0)}",
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp,
                            modifier = Modifier
                                .padding(start = 15.dp, end = 5.dp)
                        )
                        Spacer(modifier = Modifier.fillMaxWidth(0.2f))
                        Button(onClick = {
                            context.startActivity(Intent(context, PaymentActivity::class.java))
                        }) {
                            Text(text = "Checkout")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun MyCartItem(
    title: String,
    imageurl: String,
    price: String,
    size: String,
    quantity: Long,
    item_id: String,
    refreshList: MutableState<Boolean>,
    sharedPreference: SharedPreferences,
) {
    val my_quantity by remember {
        mutableStateOf(quantity)
    }

    Card(
        elevation = 5.dp,
        backgroundColor = Color.White,
        modifier = Modifier
            .height((LocalConfiguration.current.screenHeightDp / 4).dp)
            .width((LocalConfiguration.current.screenWidthDp).dp)
            .padding(start = 10.dp, end = 10.dp, top = 10.dp, bottom = 10.dp)
            .shadow(
                15.dp,
                RoundedCornerShape(20.dp),
                ambientColor = DPColor,
                spotColor = DPColor
            )
            .clickable {
//                RowItemData.index = index.toInt()
//                RowItemData.type = type
//                navController.navigate(MainScreens.Description.route)
            },
        shape = RoundedCornerShape(20.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.TopStart) {

            Row(
                modifier = Modifier
                    .fillMaxSize(),
            ) {
                val painter = rememberImagePainter(data = imageurl)

                Image(
                    painter = painter,
                    contentDescription = "$title item",
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth(0.5f)
                )

                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth(0.8f)
                        .padding(start = 20.dp),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Column {
                        Text(
                            text = title,
                            color = BGBlue,
                            fontSize = 16.sp,
                            textAlign = TextAlign.Start,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.padding(5.dp))
                        Text(
                            text = "Price: Rs.$price",
                            color = BGBlue,
                            fontSize = 16.sp,
                            textAlign = TextAlign.Start,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.padding(5.dp))
                        Text(
                            text = "Size: $size",
                            color = BGBlue,
                            fontSize = 16.sp,
                            textAlign = TextAlign.Start,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.padding(5.dp))
                        Text(
                            text = "Quantity: $my_quantity",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
//                        modifier = Modifier.padding(start = 15.dp, end = 15.dp),
                            color = Color.Black
                        )
//                    Row(modifier = Modifier.padding(5.dp)) {
//                        Icon(imageVector = Icons.Filled.Remove,
//                            contentDescription = "MinusIcon",
//                            modifier = Modifier
//                                .clickable { if (quantity >= 2) my_quantity-- }
//                                .padding(start = 15.dp, end = 15.dp)
//                                .height(20.dp)
//                                .width(20.dp),
//                            tint = Color.Black
//                        )
//                        Text(
//                            text = my_quantity.toString(),
//                            fontSize = 20.sp,
//                            modifier = Modifier
//                                .padding(start = 5.dp, end = 5.dp),
//                            color = Color.Black
//                        )
//                        Icon(imageVector = Icons.Filled.Add,
//                            contentDescription = "PlusIcon",
//                            modifier = Modifier
//                                .clickable { my_quantity++ }
//                                .padding(start = 15.dp, end = 15.dp)
//                                .height(25.dp)
//                                .width(25.dp),
//                            tint = Color.Black
//                        )
//                    }
                    }
                }
            }

            val openAlertDialog = remember {
                mutableStateOf(false)
            }

            Icon(
                imageVector = Icons.Filled.RemoveCircleOutline,
                contentDescription = "Remove Icon",
                modifier = Modifier
                    .padding(10.dp)
                    .clickable {
                        openAlertDialog.value = true
                    },
                tint = Color.Red,
            )

            if (openAlertDialog.value) {
                MyAlertDialog(
                    openDialog = openAlertDialog,
                    item_id = item_id,
                    refreshList = refreshList,
                    price = price,
                    sharedPreference = sharedPreference
                )
            }
        }
    }
}


@Composable
fun MyAlertDialog(
    openDialog: MutableState<Boolean>,
    item_id: String,
    refreshList: MutableState<Boolean>,
    price: String,
    sharedPreference: SharedPreferences
) {
    AlertDialog(onDismissRequest = {
        openDialog.value = false
    },
        title = {
            Text(text = "Are you sure???")
        },
        confirmButton = {
            Text(text = "Delete", color = Color.Red, modifier = Modifier
                .padding(10.dp)
                .clickable {
                    val db = Firebase.firestore
                    val currentUser = Firebase.auth.currentUser

                    db
                        .collection("carts")
                        .document("${currentUser?.uid.toString()}_cart")
                        .collection("cart")
                        .document(item_id)
                        .delete()
                        .addOnSuccessListener {
                            Log.d("CartScreen", "Item deleted Successfully")
                        }
                        .addOnFailureListener {
                            Log.d("CartScreen", "Failed to delete item")
                        }
                    openDialog.value = false
                    refreshList.value = true
                    val editor = sharedPreference.edit()
                    val total_sum = sharedPreference.getInt("totalsum", 0)
                    editor.putInt("totalsum", total_sum.minus(price.toInt()))
                    editor.commit()
                })
        },
        dismissButton = {
            Text(text = "Cancel", color = Color.Gray, modifier = Modifier
                .padding(10.dp)
                .clickable {
                    openDialog.value = false
                })
        })
}