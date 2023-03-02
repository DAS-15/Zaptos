package com.example.zaptos.payment

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBackIos
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.zaptos.models.PaymentScreens
import com.example.zaptos.ui.theme.BGBlue
import com.example.zaptos.ui.theme.DPColor

@Composable
fun CODScreen(paymentNavController: NavHostController, context: Context) {
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
                        text = "Cash On Delivery",
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
                .padding(start = 10.dp, end = 10.dp),
            contentAlignment = Alignment.BottomCenter,
        ) {

            // Final Bill
            Box(modifier = Modifier.fillMaxSize()) {
                Column {
                    Spacer(modifier = Modifier.padding(10.dp))
                    Row {
                        Text(
                            text = "Price Details",
                            color = DPColor,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
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

            // COD Button
            Button(
                onClick = {
                    paymentNavController.navigate(PaymentScreens.ThankYou.route)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 5.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = DPColor)
            ) {
                Text(text = "Pay via Cash", color = BGBlue, fontWeight = FontWeight.Bold)
            }
        }
    }
}


@Preview
@Composable
fun CODPrev() {
    CODScreen(paymentNavController = rememberNavController(), context = LocalContext.current)
}