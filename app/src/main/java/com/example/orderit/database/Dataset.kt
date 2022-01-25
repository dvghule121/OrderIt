package com.example.orderit.database

import com.example.orderit.R

class Dataset {
    val Specials = listOf<ProductItem>(
        ProductItem("Special maharashtrian misal pav", 70, 4, R.drawable.misal),
        ProductItem("Special maharashtrian kanda Poha", 50, 4, R.drawable.poha),
        ProductItem("Special Cold coffee", 99, 4, R.drawable.cold_coffee),
        ProductItem("Special punjabi aloo ke parothe", 70, 4, R.drawable.aloo_paratha)
    )

    val bestSellers = listOf<ProductItem>(
        ProductItem("Dominoes Extra large mushroom pizza", 249, 4, R.drawable.pizza),
        ProductItem("Coffee", 99, 4, R.drawable.coffe),
        ProductItem("Burger", 79, 5, R.drawable.burger),
        ProductItem("Vada pav ", 79, 5, R.drawable.vada_pav),
        ProductItem("Ginger chai", 79, 5, R.drawable.chai)
    )

    val all_products = listOf<ProductItem>(
        ProductItem("Dominoes Extra large mushroom pizza", 199, 4, R.drawable.pizza),
        ProductItem("Ginger chai", 15, 4, R.drawable.chai),
        ProductItem("Special maharashtrian kanda Poha", 50, 4, R.drawable.poha),
        ProductItem("Vada pav", 15, 4, R.drawable.vada_pav),
        ProductItem("Idli sambhar", 50, 4, R.drawable.idli),
        ProductItem("Vada sambhar", 50, 4, R.drawable.vada_sambar),
        ProductItem("Special punjabi aloo ke parothe", 70, 4, R.drawable.aloo_paratha),
        ProductItem("french fries", 50, 4, R.drawable.french_fries),
        ProductItem("Coffee", 50, 4, R.drawable.coffe),
        ProductItem("Special maharashtrian misal pav", 70, 4, R.drawable.misal),
        ProductItem("Burger", 79, 5, R.drawable.burger),
        ProductItem("Special Cold coffee", 99, 5, R.drawable.cold_coffee),
        ProductItem("Cheese sandwich", 120, 3, R.drawable.sandwich),
        ProductItem("Tomato soup", 60, 5, R.drawable.tomato_soup),
    )
}