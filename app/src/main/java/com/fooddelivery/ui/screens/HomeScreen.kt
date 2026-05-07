package com.fooddelivery.ui.screens

import androidx.compose.animation.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Fastfood
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.fooddelivery.data.model.Category
import com.fooddelivery.navigation.Routes
import com.fooddelivery.ui.components.CategoryChip
import com.fooddelivery.ui.components.FoodCard
import com.fooddelivery.ui.theme.*
import com.fooddelivery.viewmodel.FoodViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: FoodViewModel
) {
    val selectedCategory   by viewModel.selectedCategoryId.collectAsStateWithLifecycle()
    val filteredItems      by viewModel.filteredItems.collectAsStateWithLifecycle()
    val cartCount          by viewModel.cartCount.collectAsStateWithLifecycle()
    val userAddress        by viewModel.userAddress.collectAsStateWithLifecycle()

    val logoBrush = remember { Brush.verticalGradient(listOf(OrangeRed, OrangeRed.copy(alpha = 0.7f))) }
    val bannerBrush = remember { Brush.horizontalGradient(listOf(OrangeRed, OrangeLight)) }
    val allCategory = remember { Category(0, "All", "🍽️") }

    Scaffold(
        containerColor = DarkBg,
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(containerColor = DarkBg),
                navigationIcon = {
                    Box(
                        modifier = Modifier
                            .padding(start = 16.dp, end = 8.dp)
                            .size(42.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(logoBrush),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Fastfood,
                            contentDescription = "Logo",
                            tint = White,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                },
                title = {
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.LocationOn,
                                contentDescription = null,
                                tint = OrangeRed,
                                modifier = Modifier.size(14.dp)
                            )
                            Spacer(Modifier.width(4.dp))
                            Text(
                                text = userAddress,
                                fontSize = 12.sp,
                                color = OrangeRed,
                                fontWeight = FontWeight.Medium
                            )
                        }
                        Text(
                            "What are you craving?",
                            fontSize   = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color      = TextPrimary
                        )
                    }
                },
                actions = {
                    Box {
                        IconButton(
                            onClick = { navController.navigate(Routes.CART) }
                        ) {
                            Icon(
                                imageVector = Icons.Default.ShoppingCart,
                                contentDescription = "Cart",
                                tint  = TextPrimary,
                                modifier = Modifier.size(26.dp)
                            )
                        }
                        if (cartCount > 0) {
                            Box(
                                modifier = Modifier
                                    .size(18.dp)
                                    .clip(CircleShape)
                                    .background(OrangeRed)
                                    .align(Alignment.TopEnd)
                                    .offset(x = (-4).dp, y = 4.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text     = "$cartCount",
                                    fontSize = 10.sp,
                                    color    = White,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier            = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentPadding      = PaddingValues(bottom = 24.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // ── Hero Banner ──────────────────────────────────────────────────────
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .clip(RoundedCornerShape(24.dp))
                        .background(bannerBrush)
                        .padding(24.dp)
                ) {
                    Column {
                        Text(
                            "Free Delivery",
                            fontSize   = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color      = White.copy(alpha = 0.85f)
                        )
                        Text(
                            "On your first\norder today! 🎉",
                            fontSize   = 22.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color      = White
                        )
                        Spacer(Modifier.height(12.dp))
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(50))
                                .background(White.copy(alpha = 0.25f))
                                .padding(horizontal = 18.dp, vertical = 8.dp)
                        ) {
                            Text("Order Now →", color = White, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                        }
                    }
                    Text(
                        "🛵",
                        fontSize = 70.sp,
                        modifier = Modifier.align(Alignment.CenterEnd)
                    )
                }
            }

            // ── Categories ────────────────────────────────────────────────────────
            item {
                Text(
                    "Categories",
                    fontSize   = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color      = TextPrimary,
                    modifier   = Modifier.padding(start = 16.dp, top = 8.dp)
                )
            }
            item {
                LazyRow(
                    contentPadding      = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    item {
                        CategoryChip(
                            category = allCategory,
                            selected = selectedCategory == null,
                            onClick  = { viewModel.selectCategory(null) }
                        )
                    }
                    items(viewModel.categories) { cat ->
                        CategoryChip(
                            category = cat,
                            selected = selectedCategory == cat.id,
                            onClick  = { viewModel.selectCategory(cat.id) }
                        )
                    }
                }
            }

            // ── Section title ─────────────────────────────────────────────────────
            item {
                Text(
                    if (selectedCategory == null) "🔥 Popular Items" else "Menu",
                    fontSize   = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color      = TextPrimary,
                    modifier   = Modifier.padding(start = 16.dp, top = 4.dp)
                )
            }

            // ── Food list ─────────────────────────────────────────────────────────
            items(filteredItems, key = { it.id }) { food ->
                FoodCard(
                    foodItem = food,
                    onClick  = { navController.navigate(Routes.detailRoute(food.id)) },
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }
        }
    }
}
