package com.fooddelivery.data.repository

import com.fooddelivery.data.model.Category
import com.fooddelivery.data.model.FoodItem

object MockData {

    val categories = listOf(
        Category(1, "Burgers",  "🍔"),
        Category(2, "Pizza",    "🍕"),
        Category(3, "Sushi",    "🍱"),
        Category(4, "Tacos",    "🌮"),
        Category(5, "Desserts", "🍰"),
        Category(6, "Drinks",   "🥤")
    )

    val foodItems = listOf(
        FoodItem(
            id = 1, name = "Classic Smash Burger", categoryId = 1,
            description = "Double smash patty with cheddar, caramelised onions, pickles & our secret smoky sauce on a brioche bun.",
            price = 12.99, rating = 4.8f, reviewCount = 320, emoji = "🍔",
            deliveryTime = "20-30 min", isPopular = true
        ),
        FoodItem(
            id = 2, name = "BBQ Bacon Burger", categoryId = 1,
            description = "Juicy beef patty loaded with crispy bacon, BBQ sauce, jalapeños and coleslaw.",
            price = 14.49, rating = 4.6f, reviewCount = 215, emoji = "🥩",
            deliveryTime = "25-35 min"
        ),
        FoodItem(
            id = 3, name = "Margherita Pizza", categoryId = 2,
            description = "San Marzano tomato, fresh buffalo mozzarella, basil and extra-virgin olive oil on a hand-stretched sourdough base.",
            price = 11.99, rating = 4.7f, reviewCount = 540, emoji = "🍕",
            deliveryTime = "30-40 min", isPopular = true
        ),
        FoodItem(
            id = 4, name = "Pepperoni Supreme", categoryId = 2,
            description = "Loaded with spicy pepperoni cups, mozzarella, roasted red peppers and honey drizzle.",
            price = 13.99, rating = 4.9f, reviewCount = 780, emoji = "🍕",
            deliveryTime = "30-40 min", isPopular = true
        ),
        FoodItem(
            id = 5, name = "Salmon Sushi Box", categoryId = 3,
            description = "8-piece premium salmon nigiri & hosomaki, served with pickled ginger, wasabi and soy sauce.",
            price = 16.99, rating = 4.8f, reviewCount = 190, emoji = "🍱",
            deliveryTime = "40-50 min"
        ),
        FoodItem(
            id = 6, name = "Dragon Roll", categoryId = 3,
            description = "Crispy shrimp tempura inside, topped with avocado, tobiko and spicy mayo.",
            price = 15.49, rating = 4.7f, reviewCount = 145, emoji = "🍣",
            deliveryTime = "40-50 min"
        ),
        FoodItem(
            id = 7, name = "Street Tacos (3pc)", categoryId = 4,
            description = "Corn tortillas filled with carne asada, fresh pico, avocado crema and lime.",
            price = 10.99, rating = 4.5f, reviewCount = 300, emoji = "🌮",
            deliveryTime = "15-25 min", isPopular = true
        ),
        FoodItem(
            id = 8, name = "Churro Bites", categoryId = 5,
            description = "Golden crispy churros tossed in cinnamon sugar, served with warm chocolate dipping sauce.",
            price = 6.99, rating = 4.6f, reviewCount = 420, emoji = "🍩",
            deliveryTime = "10-20 min"
        ),
        FoodItem(
            id = 9, name = "NY Cheesecake Slice", categoryId = 5,
            description = "Dense, creamy New York-style cheesecake on a graham cracker crust with seasonal berry compote.",
            price = 7.49, rating = 4.9f, reviewCount = 650, emoji = "🍰",
            deliveryTime = "10-20 min", isPopular = true
        ),
        FoodItem(
            id = 10, name = "Mango Lassi", categoryId = 6,
            description = "Thick & creamy Alphonso mango blended with chilled yogurt and a pinch of cardamom.",
            price = 4.99, rating = 4.7f, reviewCount = 230, emoji = "🥤",
            deliveryTime = "10-15 min"
        ),
        FoodItem(
            id = 11, name = "Oreo Milkshake", categoryId = 6,
            description = "Thick vanilla milkshake blended with crushed Oreos, topped with whipped cream and cookie crumble.",
            price = 5.99, rating = 4.8f, reviewCount = 310, emoji = "🍦",
            deliveryTime = "10-15 min"
        ),
        FoodItem(
            id = 12, name = "Veggie Delight Burger", categoryId = 1,
            description = "Crispy chickpea patty with hummus, rocket, sun-dried tomatoes and tzatziki on a whole-wheat bun.",
            price = 11.49, rating = 4.4f, reviewCount = 175, emoji = "🥗",
            deliveryTime = "20-30 min"
        )
    )

    fun getById(id: Int): FoodItem? = foodItems.find { it.id == id }

    fun getByCategory(categoryId: Int): List<FoodItem> =
        foodItems.filter { it.categoryId == categoryId }

    fun getPopular(): List<FoodItem> = foodItems.filter { it.isPopular }
}
