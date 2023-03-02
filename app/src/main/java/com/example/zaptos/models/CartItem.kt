package com.example.zaptos.models

import kotlinx.serialization.Serializable

@Serializable
data class CartItem(
    val title: String,
    val price: String,
    val imageurl: String,
    val quantity: Int,
    val index: Int,
    val type: String,
    val size: String
)
