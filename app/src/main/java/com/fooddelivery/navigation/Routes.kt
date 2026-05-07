package com.fooddelivery.navigation

object Routes {
    const val SPLASH  = "splash"
    const val HOME    = "home"
    const val DETAIL  = "detail/{foodId}"
    const val CART    = "cart"

    fun detailRoute(foodId: Int) = "detail/$foodId"
}
