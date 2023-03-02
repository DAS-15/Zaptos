package com.example.zaptos.search_and_description

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.rounded.ArrowBackIos
import androidx.compose.material.icons.rounded.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.example.zaptos.MasterViewModel
import com.example.zaptos.RowItemData
import com.example.zaptos.models.CartItem
import com.example.zaptos.models.ItemModel
import com.example.zaptos.ui.theme.BGBlue
import com.example.zaptos.ui.theme.DPColor
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

@Composable
fun DescriptionScreen(navController: NavHostController, context: Context, vm: MasterViewModel) {
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


        val itemObj: ItemModel?

        if (RowItemData.type == "Nike") {
            itemObj = vm.nikeList?.value?.get(RowItemData.index)
        } else if (RowItemData.type == "Adidas") {
            itemObj = vm.adidasList?.value?.get(RowItemData.index)
        } else {
            itemObj = vm.pumaList?.value?.get(RowItemData.index)
        }

        Toast.makeText(context, itemObj.toString(), Toast.LENGTH_LONG).show()

        var quantity by remember {
            mutableStateOf(1)
        }

        LazyColumn(
            modifier = Modifier
                .padding(it)
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            BGBlue,
                            Color.Black
                        )
                    )
                )
        ) {
            item {
                val painter = rememberImagePainter(data = itemObj?.imageurl.toString())
                Image(
                    painter = painter,
                    contentDescription = "ShoeImage",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(LocalConfiguration.current.screenWidthDp.dp)
                )
                Spacer(modifier = Modifier.padding(5.dp))
                Text(
                    text = itemObj?.title.toString(),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(15.dp)
                )
                Spacer(modifier = Modifier.padding(0.dp))
                Text(
                    text = "Price: ${itemObj?.price.toString()}",
                    fontSize = 20.sp,
                    modifier = Modifier.padding(15.dp)
                )
                Spacer(modifier = Modifier.padding(5.dp))

                val sizeList = listOf<Int>(6, 7, 8, 9, 10, 11, 12)
                val expanded = rememberSaveable {
                    mutableStateOf(false)
                }
                val currentValue = rememberSaveable {
                    mutableStateOf(
                        sizeList[0]
                    )
                }

                Row {
                    Text(
                        text = "Size:",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(start = 15.dp, end = 15.dp)
                    )
                    Row(
                        modifier = Modifier.clickable {
                            expanded.value = !expanded.value
                        }
                    ) {
                        Text(text = currentValue.value.toString())
                        Icon(imageVector = Icons.Rounded.ArrowDropDown, contentDescription = "")
                        DropdownMenu(expanded = expanded.value, onDismissRequest = {
                            expanded.value = false
                        }) {
                            sizeList.forEach { size ->
                                DropdownMenuItem(onClick = {
                                    currentValue.value = size
                                    expanded.value = false
                                }
                                ) {
                                    Text(text = size.toString())
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.padding(10.dp))

                Text(
                    text = "Quantity:",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = 15.dp, end = 15.dp)
                )
                Row(modifier = Modifier.padding(5.dp)) {
                    Icon(imageVector = Icons.Filled.Remove,
                        contentDescription = "MinusIcon",
                        modifier = Modifier
                            .clickable { if (quantity >= 2) quantity-- }
                            .padding(start = 15.dp, end = 15.dp)
                            .height(25.dp)
                            .width(25.dp),
                        tint = Color.White
                    )
                    Text(
                        text = quantity.toString(),
                        fontSize = 20.sp,
                        modifier = Modifier
                            .padding(start = 5.dp, end = 5.dp)
                    )
                    Icon(imageVector = Icons.Filled.Add,
                        contentDescription = "PlusIcon",
                        modifier = Modifier
                            .clickable { quantity++ }
                            .padding(start = 15.dp, end = 15.dp)
                            .height(25.dp)
                            .width(25.dp),
                        tint = Color.White
                    )
                }
                Card(
                    modifier = Modifier
                        .padding(
                            top = 10.dp,
                            start = (LocalConfiguration.current.screenWidthDp / 16).dp
                        )
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically, modifier = Modifier
                            .padding(
                                5.dp
                            )
                    ) {
                        Text(
                            text = "Total sum:",
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp,
                            modifier = Modifier
                                .padding(start = 5.dp, end = 5.dp)
                        )

                        val totalSum = (itemObj?.price?.substring(
                            1,
                            itemObj.price.length
                        )
                            ?.replace(",", "")
                            ?.toInt()
                            ?.times(quantity)).toString()

                        Text(
                            text = "Rs.$totalSum",
                            fontSize = 20.sp,
                            modifier = Modifier
                                .padding(start = 5.dp, end = 25.dp)
                        )
                        Button(onClick = {
//                            CartList.cartList.add(
//                                CartItem(
//                                    title = itemObj?.title.toString(),
//                                    price = totalSum,
//                                    imageurl = itemObj?.imageurl.toString(),
//                                    quantity = quantity,
//                                    index = RowItemData.index,
//                                    type = RowItemData.type
//                                )
//                            )

                            val db = Firebase.firestore
                            val currentUser = Firebase.auth.currentUser
//                            val firstCartItem = hashMapOf(
//                                "status" to "up",
//                                "cart" to arrayListOf(
//                                    hashMapOf(
//                                        "title" to itemObj?.title.toString(),
//                                        "price" to totalSum,
//                                        "imageurl" to itemObj?.imageurl.toString(),
//                                        "quantity" to quantity,
//                                        "index" to RowItemData.index,
//                                        "type" to RowItemData.type
//                                    )
//                                )
//                            )

//                            val cartItem = hashMapOf(
//                                "title" to itemObj?.title.toString(),
//                                "price" to totalSum,
//                                "imageurl" to itemObj?.imageurl.toString(),
//                                "quantity" to quantity,
//                                "index" to RowItemData.index,
//                                "type" to RowItemData.type
//                            )
                            val cartItem = CartItem(
                                title = itemObj?.title.toString(),
                                price = totalSum,
                                imageurl = itemObj?.imageurl.toString(),
                                size = currentValue.value.toString(),
                                quantity = quantity,
                                index = RowItemData.index,
                                type = RowItemData.type
                            )

//                            val li = db.collection("carts")
//                                .document("${currentUser?.uid.toString()}_cart")
//                                .collection("cart").get().result

//                            for (item in li) {
//                                if (item["title"] == cartItem.title && item["size"] == cartItem.size) {
//                                    item["quantity"]++
//                                } else {
                            db.collection("carts")
                                .document("${currentUser?.uid.toString()}_cart")
                                .collection("cart")
                                .add(cartItem)
                                .addOnSuccessListener { Obj ->
                                    Log.i(
                                        "FirestoreCart",
                                        "MainDocumentSnapshot added with ID: $Obj"
                                    )
                                }
                                .addOnFailureListener { e ->
                                    Log.i("FirestoreCart", "Error adding document", e)
                                }
//                                }
//                            }

//                            if (Obj.result["status"] == "down") {
//                                db.collection("carts")
//                                    .document("${currentUser?.uid.toString()}_cart")
//                                    .set(firstCartItem)
//                                    .addOnSuccessListener { documentReference ->
//                                        Log.i(
//                                            "FirestoreCart",
//                                            "DocumentSnapshot added with ID: ${documentReference}"
//                                        )
//                                    }
//                                    .addOnFailureListener { e ->
//                                        Log.i("FirestoreCart", "Error adding document", e)
//                                    }
//                            } else {
//                                db.collection("carts")
//                                    .document("${currentUser?.uid.toString()}_cart")
//                                    .set(
//                                        { "cart" to arrayListOf(cartItem) },
//                                        SetOptions.merge()
//                                    )
//                                    .addOnSuccessListener { documentReference ->
//                                        Log.i(
//                                            "FirestoreCart",
//                                            "DocumentSnapshot added with ID: ${documentReference}"
//                                        )
//                                    }
//                                    .addOnFailureListener { e ->
//                                        Log.i("FirestoreCart", "Error adding document", e)
//                                    }
//                            }

                        }) {
                            Text(text = "Add to Cart")
                        }
                    }
                }
            }
        }
    }
}