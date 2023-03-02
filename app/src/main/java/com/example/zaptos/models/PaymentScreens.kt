package com.example.zaptos.models

sealed class PaymentScreens(val route: String) {
    object Address : PaymentScreens("address_screen")
    object Payment : PaymentScreens("payment_screen")
    object COD : PaymentScreens("cod_screen")
    object ThankYou : PaymentScreens("thankyou_screen")
}