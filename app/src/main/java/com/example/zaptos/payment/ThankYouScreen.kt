package com.example.zaptos.payment

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material.icons.rounded.Verified
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.zaptos.MainScreenActivity
import com.example.zaptos.ui.theme.DPColor

@Composable
fun ThankYouScreen(paymentNavController: NavHostController, context: Context) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = Icons.Rounded.CheckCircle,
                contentDescription = "Thank You",
                tint = Color.Green,
                modifier = Modifier
                    .height(250.dp)
                    .width(250.dp)
            )
            Text(
                text = "Thank You!!!",
                color = DPColor,
                fontWeight = FontWeight.Bold,
                fontSize = 36.sp,
            )
            Spacer(modifier = Modifier.padding(10.dp))
            Text(
                text = "Go back to Home Screen",
                modifier = Modifier
                    .clickable {
                        context.startActivity(Intent(context, MainScreenActivity::class.java))
                        val sharedPreference = context.getSharedPreferences(
                            "PREFERENCE_NAME",
                            Context.MODE_PRIVATE
                        )
                        sharedPreference.edit().putBoolean("backFromThankYou", true).apply()
                    },
                color = DPColor,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
            )
        }
    }
}

@Preview
@Composable
fun ThankYouPrev() {
    ThankYouScreen(paymentNavController = rememberNavController(), context = LocalContext.current)
}