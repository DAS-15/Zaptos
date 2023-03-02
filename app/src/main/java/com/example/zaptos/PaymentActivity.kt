package com.example.zaptos

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.zaptos.payment.PaymentNavGraph
import com.example.zaptos.ui.theme.BGBlue
import com.example.zaptos.ui.theme.ZaptosTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController

class PaymentActivity : ComponentActivity() {
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

                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val paymentNavController = rememberNavController()
                    PaymentNavGraph(paymentNavController = paymentNavController, context = applicationContext)
                }
            }
        }
    }
}
