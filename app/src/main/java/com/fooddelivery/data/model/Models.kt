package com.fooddelivery.data.model

data class Category(
    val id: Int,
    val name: String,
    val emoji: String
)

data class FoodItem(
    val id: Int,
    val name: String,
    val description: String,
    val price: Double,
    val rating: Float,
    val reviewCount: Int,
    val categoryId: Int,
    val emoji: String,          // used as placeholder image
    val deliveryTime: String,   // e.g. "20-30 min"
    val isPopular: Boolean = false
)

data class CartItem(
    val foodItem: FoodItem,
    var quantity: Int
)
