package com.example.zaptos.payment

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.zaptos.models.PaymentScreens

@Composable
fun PaymentNavGraph(
    paymentNavController: NavHostController,
    context: android.content.Context
) {
    NavHost(
        navController = paymentNavController,
        startDestination = PaymentScreens.Address.route
    ) {
        composable(PaymentScreens.Address.route) {
            AddressScreen(paymentNavController, context)
        }
        composable(PaymentScreens.Payment.route) {
            PaymentScreen(paymentNavController, context)
        }
        composable(PaymentScreens.COD.route) {
            CODScreen(paymentNavController, context)
        }
        composable(PaymentScreens.ThankYou.route) {
            ThankYouScreen(paymentNavController, context)
        }
    }
}