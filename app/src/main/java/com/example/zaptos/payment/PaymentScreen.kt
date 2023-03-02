package com.example.zaptos.payment

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBackIos
import androidx.compose.material.icons.rounded.Language
import androidx.compose.material.icons.rounded.Payment
import androidx.compose.material.icons.rounded.QrCode
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.docode.Repository.MainRepository
import com.example.zaptos.MasterViewModel
import com.example.zaptos.models.PaymentScreens
import com.example.zaptos.ui.theme.BGBlue
import com.example.zaptos.ui.theme.DPColor
import com.example.zaptos.ui.theme.ZaptosTheme
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Job

@Composable
fun PaymentScreen(paymentNavController: NavHostController, context: Context) {
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
                                paymentNavController.navigateUp()
                            }
                            .padding(start = 10.dp),
                        tint = DPColor
                    )

                    Text(
                        text = "Payment",
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(start = 15.dp, bottom = 5.dp),
                        color = DPColor,
                        fontSize = 20.sp,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    ) { appBarPadding ->

        val uriHandler = LocalUriHandler.current

        var vm: MasterViewModel? = null

        LaunchedEffect(key1 = 1) {
            val job = Job()
            vm = MasterViewModel(job)
            val currentUser = Firebase.auth.currentUser
            vm!!.getPaymentLink(
                context,
                MainRepository(),
                job,
                currentUser?.uid.toString()
            )
        }

        val sharedPreference = context.getSharedPreferences(
            "PREFERENCE_NAME",
            Context.MODE_PRIVATE
        )

        val totalDiscount = remember {
            mutableStateOf(50)
        }

        val totalDeliveryCharges = remember {
            mutableStateOf(10)
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(appBarPadding)
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 10.dp, end = 10.dp)
            ) {
                item {
//                    Text(
//                        text = "Payment",
//                        fontWeight = FontWeight.Bold,
//                        fontSize = 20.sp,
//                        color = DPColor
//                    )
                    Spacer(modifier = Modifier.padding(8.dp))
                    Text(
                        text = "Payment Options",
                        fontSize = 16.sp,
                        color = DPColor.copy(alpha = 0.8f),
                        modifier = Modifier.padding(top = 10.dp)
                    )
                    Divider(
                        color = DPColor, modifier = Modifier
//                        .padding(top = 5.dp, bottom = 5.dp)
                    )
                    Card(
                        modifier = Modifier
                            .padding(10.dp)
                            .clickable {
                                paymentNavController.navigate(PaymentScreens.COD.route)
                            }
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(60.dp)
                                .padding(18.dp)
                        ) {
                            Text(
                                text = "Cash On Delivery",
                                modifier = Modifier.weight(1f),
                                color = DPColor
                            )
                            Text(text = "₹", color = DPColor, fontSize = 24.sp)
                        }
                    }

                    Card(
                        modifier = Modifier
                            .padding(10.dp)
                            .clickable {
                                val myurl = vm?.paymentLink?.value.toString()
                                println(MasterViewModel.myurl.toString())
                                if (myurl != "https://www.google.com") {
                                    uriHandler.openUri(MasterViewModel.myurl.toString())
                                } else {
                                    Toast
                                        .makeText(
                                            context,
                                            "Unable to process card payment\nPlease wait or try again!!!",
                                            Toast.LENGTH_LONG
                                        )
                                        .show()
                                }
                            }
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(60.dp)
                                .padding(18.dp)
                        ) {
                            Text(
                                text = "Credit/Debit Card",
                                modifier = Modifier.weight(1f),
                                color = DPColor
                            )
                            Icon(
                                imageVector = Icons.Rounded.Payment,
                                contentDescription = "Credit Debit Icon",
                                tint = DPColor
                            )
                        }
                    }

                    Card(
                        modifier = Modifier
                            .padding(10.dp)
                            .clickable {

                            }
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(60.dp)
                                .padding(18.dp)
                        ) {
                            Text(
                                text = "UPI",
                                modifier = Modifier.weight(1f),
                                color = DPColor
                            )
                            Icon(
                                imageVector = Icons.Rounded.QrCode,
                                contentDescription = "Money Icon",
                                tint = DPColor
                            )
                        }
                    }

                    Card(
                        modifier = Modifier
                            .padding(10.dp)
                            .clickable {

                            }
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(60.dp)
                                .padding(18.dp)
                        ) {
                            Text(
                                text = "Net Banking",
                                modifier = Modifier.weight(1f),
                                color = DPColor
                            )
                            Icon(
                                imageVector = Icons.Rounded.Language,
                                contentDescription = "Money Icon",
                                tint = DPColor
                            )
                        }
                    }
                }

                item {
                    Spacer(modifier = Modifier.padding(15.dp))
                    Column {
                        Row {
                            Text(
                                text = "Price Details",
                                color = DPColor,
                                fontSize = 16.sp,
                                modifier = Modifier.weight(1f)
                            )
                        }
                        Spacer(modifier = Modifier.padding(5.dp))
                        Row(modifier = Modifier.padding(10.dp)) {
                            Text(
                                text = "    TOTAL PRICE",
                                color = DPColor,
                                fontSize = 14.sp,
                                modifier = Modifier.weight(1f)
                            )
                            Text(
                                text = "Rs.${sharedPreference.getInt("totalsum", 0)}",
                                color = DPColor,
                                fontSize = 14.sp,
                                modifier = Modifier.padding(start = 10.dp, end = 10.dp)
                            )
                        }
                        Row(modifier = Modifier.padding(10.dp)) {
                            Text(
                                text = "    DISCOUNT",
                                color = DPColor,
                                fontSize = 14.sp,
                                modifier = Modifier.weight(1f)
                            )
                            Text(
                                text = "-Rs.${totalDiscount.value}",
                                color = DPColor,
                                fontSize = 14.sp,
                                modifier = Modifier.padding(start = 10.dp, end = 10.dp)
                            )
                        }
                        Row(modifier = Modifier.padding(10.dp)) {
                            Text(
                                text = "    DELIVERY CHARGES",
                                color = DPColor,
                                fontSize = 14.sp,
                                modifier = Modifier.weight(1f)
                            )
                            Text(
                                text = "Rs.${totalDeliveryCharges.value}",
                                color = DPColor,
                                fontSize = 14.sp,
                                modifier = Modifier.padding(start = 10.dp, end = 10.dp)
                            )
                        }
                        Spacer(modifier = Modifier.padding(5.dp))
                        Divider(color = DPColor)
                        Spacer(modifier = Modifier.padding(5.dp))
                        Row(modifier = Modifier.padding(10.dp)) {
                            Text(
                                text = "   TOTAL AMOUNT",
                                color = DPColor,
                                fontSize = 16.sp,
                                modifier = Modifier.weight(1f),
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "Rs.${
                                    sharedPreference.getInt(
                                        "totalsum",
                                        0
                                    ) - totalDiscount.value + totalDeliveryCharges.value
                                }",
                                color = DPColor,
                                fontSize = 16.sp,
                                modifier = Modifier.padding(start = 10.dp, end = 10.dp),
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun showPaymentScreen() {
    val context = LocalContext.current
    PaymentScreen(paymentNavController = rememberNavController(), context = context)
}


@Preview(showBackground = true)
@Composable
fun showDarkPaymentScreen() {
    ZaptosTheme(darkTheme = true) {
        val context = LocalContext.current
        PaymentScreen(paymentNavController = rememberNavController(), context = context)
    }
}