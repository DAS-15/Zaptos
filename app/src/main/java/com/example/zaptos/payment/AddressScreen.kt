package com.example.zaptos.payment

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.zaptos.models.PaymentScreens
import com.example.zaptos.ui.theme.BGBlue
import com.example.zaptos.ui.theme.DPColor
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddressScreen(paymentNavController: NavHostController, context: android.content.Context) {


    // Contact info variables
    var nameTF by remember {
        mutableStateOf("")
    }
    var mobileNoTF by remember {
        mutableStateOf("")
    }

    // Address variables
    var addressPinCodeTF by remember {
        mutableStateOf("")
    }

    var addressFlatAndBuildingTF by remember {
        mutableStateOf("")
    }

    var addressLocalityAndTownTF by remember {
        mutableStateOf("")
    }

    var addressCityTF by remember {
        mutableStateOf("")
    }

    var addressStateTF by remember {
        mutableStateOf("")
    }

    var isError by remember {
        mutableStateOf(false)
    }

    var li: QuerySnapshot? by remember {
        mutableStateOf(null)
    }

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
//                    Icon(
//                        imageVector = Icons.Rounded.ArrowBackIos,
//                        contentDescription = "Back Button",
//                        modifier = Modifier
//                            .clickable {
//
//                            }
//                            .padding(start = 10.dp),
//                        tint = DPColor
//                    )

                    Text(
                        text = "Shipping Details",
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
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {

                item {
                    LaunchedEffect(key1 = 1) {
                        val db = Firebase.firestore
                        val currentUser = Firebase.auth.currentUser

                        db.collection("carts")
                            .document("${currentUser?.uid.toString()}_cart")
                            .collection("address").get()
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

                    if (li != null) {
                        Spacer(modifier = Modifier.padding(6.dp))
                        Text(
                            text = "Saved Addresses",
                            fontWeight = FontWeight.Bold,
                            color = DPColor,
                            fontSize = 20.sp,
                            modifier = Modifier.padding(10.dp)
                        )

                        Spacer(modifier = Modifier.padding(5.dp))
                        Divider(
                            color = DPColor,
                            modifier = Modifier.padding(start = 10.dp, end = 10.dp)
                        )
                        Spacer(modifier = Modifier.padding(5.dp))

                        li?.documents?.forEach { item ->
                            AddressCard(item, paymentNavController)
                        }

                        Spacer(modifier = Modifier.padding(5.dp))
                        Divider(
                            color = DPColor,
                            modifier = Modifier.padding(start = 10.dp, end = 10.dp)
                        )
                        Spacer(modifier = Modifier.padding(5.dp))
                    }
                }

                item {
                    Card(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(10.dp), shape = RoundedCornerShape(30.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .background(BGBlue)
                                .padding(30.dp)
                        ) {
//                            Text(text = "SHIPPING DETAILS", fontSize = 30.sp, color = DPColor)
                            if (isError) {
                                Row {
                                    Icon(
                                        imageVector = Icons.Filled.Info,
                                        contentDescription = "Error Icon",
                                        tint = Color.Red
                                    )
                                    Spacer(modifier = Modifier.padding(4.dp))
                                    Text(
                                        text = "All fields are mandatory!!!",
                                        color = Color.Red,
                                        fontSize = 16.sp
                                    )
                                }
                                Spacer(modifier = Modifier.padding(10.dp))
                            }

                            // Contact Details
                            Text(
                                text = "CONTACT DETAILS",
                                fontSize = 20.sp,
                                color = DPColor,
                                fontWeight = FontWeight.Bold
                            )
                            Divider(color = BGBlue)
                            Spacer(modifier = Modifier.padding(8.dp))

                            // Name
                            OutlinedTextField(
                                value = nameTF,
                                onValueChange = {
                                    nameTF = it
                                },
                                label = {
                                    Text(text = "Name", fontSize = 16.sp)
                                },
                                singleLine = true
                            )
                            Spacer(modifier = Modifier.padding(5.dp))

                            // Mobile No.
                            OutlinedTextField(
                                value = mobileNoTF,
                                onValueChange = {
                                    mobileNoTF = it
                                },
                                label = {
                                    Text(text = "Mobile No.", fontSize = 16.sp)
                                },
                                singleLine = true
                            )
                            Spacer(modifier = Modifier.padding(15.dp))

                            // Address
                            Text(
                                text = "ADDRESS",
                                fontSize = 20.sp,
                                color = DPColor,
                                fontWeight = FontWeight.Bold
                            )
                            Divider(color = BGBlue)
                            Spacer(modifier = Modifier.padding(8.dp))

                            // Pincode
                            OutlinedTextField(
                                value = addressPinCodeTF,
                                onValueChange = {
                                    addressPinCodeTF = it
                                },
                                label = {
                                    Text(text = "Pincode", fontSize = 16.sp)
                                },
                                singleLine = true
                            )
                            Spacer(modifier = Modifier.padding(5.dp))


                            // Flat and Building
                            OutlinedTextField(
                                value = addressFlatAndBuildingTF,
                                onValueChange = {
                                    addressFlatAndBuildingTF = it
                                },
                                label = {
                                    Text(text = "Flat and Building", fontSize = 16.sp)
                                },
                                singleLine = true
                            )
                            Spacer(modifier = Modifier.padding(5.dp))

                            // Locality and Town
                            OutlinedTextField(
                                value = addressLocalityAndTownTF,
                                onValueChange = {
                                    addressLocalityAndTownTF = it
                                },
                                label = {
                                    Text(text = "Locality and Town", fontSize = 16.sp)
                                },
                                singleLine = true
                            )
                            Spacer(modifier = Modifier.padding(5.dp))

                            // City
                            OutlinedTextField(
                                value = addressCityTF,
                                onValueChange = {
                                    addressCityTF = it
                                },
                                label = {
                                    Text(text = "City", fontSize = 16.sp)
                                },
                                singleLine = true
                            )
                            Spacer(modifier = Modifier.padding(5.dp))

                            // State
                            OutlinedTextField(
                                value = addressStateTF,
                                onValueChange = {
                                    addressStateTF = it
                                },
                                label = {
                                    Text(text = "State", fontSize = 16.sp)
                                },
                                singleLine = true
                            )

                            Spacer(modifier = Modifier.padding(10.dp))

                            Button(
                                onClick = {
                                    if (nameTF == "" || mobileNoTF == "" || addressPinCodeTF == "" || addressFlatAndBuildingTF == "" || addressLocalityAndTownTF == "" || addressCityTF == "" || addressStateTF == "") {
                                        isError = true
                                    } else {
                                        val db = Firebase.firestore
                                        val currentUser = Firebase.auth.currentUser

                                        db.collection("carts")
                                            .document("${currentUser?.uid.toString()}_cart")
                                            .collection("address")
                                            .add(
                                                hashMapOf(
                                                    "name" to nameTF,
                                                    "mobileno" to mobileNoTF,
                                                    "pincode" to addressPinCodeTF,
                                                    "flatandbuilding" to addressFlatAndBuildingTF,
                                                    "localityandtown" to addressLocalityAndTownTF,
                                                    "city" to addressCityTF,
                                                    "state" to addressStateTF,
                                                )
                                            )
                                            .addOnSuccessListener { Obj ->
                                                Log.i(
                                                    "FirestoreCartCartScreen",
                                                    "AddressScreen DocumentSnapshot added with ID: $Obj"
                                                )

                                                paymentNavController.navigate(PaymentScreens.Payment.route)

                                            }
                                            .addOnFailureListener { e ->
                                                Log.i("FirestoreCart", "Error adding document", e)
                                            }
                                    }

                                },
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(text = "Proceed to pay", fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
            }
        }
    }
}