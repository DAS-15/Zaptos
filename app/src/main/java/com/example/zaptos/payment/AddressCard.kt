package com.example.zaptos.payment

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.zaptos.models.PaymentScreens
import com.example.zaptos.ui.theme.BGBlue
import com.example.zaptos.ui.theme.DPColor
import com.google.firebase.firestore.DocumentSnapshot

@Composable
fun AddressCard(item: DocumentSnapshot, paymentNavController: NavHostController) {
    Card(
        backgroundColor = BGBlue,
        shape = RoundedCornerShape(20.dp),
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
//            .fillMaxHeight(0.4f)
    ) {
        Column(
            modifier = Modifier
                .padding(20.dp)
        ) {
            Text(text = "Address:", fontWeight = FontWeight.Bold, color = DPColor)
            Text(text = "${item["name"].toString()},", color = DPColor)
            Text(
                text = "${item["flatandbuilding"].toString()} ${item["localityandtown"].toString()} ${item["city"].toString()} ${item["state"].toString()}-${item["pincode"].toString()}.",
                color = DPColor
            )
            Text(text = item["mobileno"].toString(), color = DPColor)
            Card(
                shape = CircleShape, backgroundColor = DPColor, modifier = Modifier
                    .padding(start = (LocalConfiguration.current.screenWidthDp / 1.4).dp)
                    .height(30.dp)
                    .width(60.dp)
            ) {
                Icon(
                    imageVector = Icons.Rounded.ArrowForward,
                    contentDescription = "Use this Address",
                    tint = BGBlue,
                    modifier = Modifier.clickable {
                        paymentNavController.navigate(PaymentScreens.Payment.route)
                    }
                )
            }
        }
    }
}