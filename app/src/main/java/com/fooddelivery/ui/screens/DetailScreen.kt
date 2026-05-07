package com.fooddelivery.ui.screens

import androidx.compose.animation.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
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
import com.fooddelivery.navigation.Routes
import com.fooddelivery.ui.theme.*
import com.fooddelivery.viewmodel.FoodViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    navController: NavController,
    viewModel: FoodViewModel,
    foodId: Int
) {
    val food     = viewModel.getItemById(foodId) ?: return
    val cartCount by viewModel.cartCount.collectAsStateWithLifecycle()
    val qty       by remember { derivedStateOf { viewModel.quantityInCart(food) } }

    Scaffold(
        containerColor = DarkBg,
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(containerColor = DarkBg),
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector        = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint               = TextPrimary
                        )
                    }
                },
                title = { Text("Details", color = TextPrimary, fontWeight = FontWeight.Bold) },
                actions = {
                    Box {
                        IconButton(onClick = { navController.navigate(Routes.CART) }) {
                            Icon(
                                imageVector        = Icons.Default.ShoppingCart,
                                contentDescription = "Cart",
                                tint               = TextPrimary
                            )
                        }
                        if (cartCount > 0) {
                            Box(
                                modifier = Modifier
                                    .size(16.dp)
                                    .clip(CircleShape)
                                    .background(OrangeRed)
                                    .align(Alignment.TopEnd)
                                    .offset(x = (-4).dp, y = 4.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text("$cartCount", fontSize = 9.sp, color = White, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
            )
        },
        bottomBar = {
            Surface(color = DarkSurface, tonalElevation = 8.dp) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment     = Alignment.CenterVertically
                ) {
                    // Quantity control
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        IconButton(
                            onClick  = { viewModel.removeFromCart(food) },
                            enabled  = qty > 0,
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                                .background(if (qty > 0) OrangeRed else DarkCard)
                        ) {
                            Icon(Icons.Default.Remove, null, tint = White)
                        }
                        Text(
                            text       = "$qty",
                            fontSize   = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color      = TextPrimary
                        )
                        IconButton(
                            onClick  = { viewModel.addToCart(food) },
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                                .background(OrangeRed)
                        ) {
                            Icon(Icons.Default.Add, null, tint = White)
                        }
                    }

                    // Add to cart button
                    Button(
                        onClick = { viewModel.addToCart(food) },
                        colors  = ButtonDefaults.buttonColors(containerColor = OrangeRed),
                        shape   = RoundedCornerShape(14.dp),
                        modifier = Modifier.height(50.dp)
                    ) {
                        Text("Add to Cart", fontWeight = FontWeight.Bold, fontSize = 15.sp, color = White)
                    }
                }
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(padding)
        ) {
            // Big emoji hero
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(260.dp)
                    .background(Brush.verticalGradient(listOf(DarkSurface, DarkBg))),
                contentAlignment = Alignment.Center
            ) {
                Text(text = food.emoji, fontSize = 120.sp)
                if (food.isPopular) {
                    Box(
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(16.dp)
                            .clip(RoundedCornerShape(50))
                            .background(OrangeRed)
                            .padding(horizontal = 14.dp, vertical = 6.dp)
                    ) {
                        Text("⚡ Popular", fontSize = 12.sp, color = White, fontWeight = FontWeight.Bold)
                    }
                }
            }

            Spacer(Modifier.height(8.dp))

            Column(modifier = Modifier.padding(horizontal = 20.dp)) {
                Text(food.name, fontSize = 26.sp, fontWeight = FontWeight.ExtraBold, color = TextPrimary)
                Spacer(Modifier.height(6.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    Text("⭐ ${food.rating}", fontSize = 14.sp, color = YellowStar)
                    Text("(${food.reviewCount} reviews)", fontSize = 14.sp, color = TextHint)
                    Text("🕐 ${food.deliveryTime}", fontSize = 14.sp, color = TextHint)
                }
                Spacer(Modifier.height(16.dp))
                Text("Description", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
                Spacer(Modifier.height(6.dp))
                Text(food.description, fontSize = 14.sp, color = TextSecondary, lineHeight = 22.sp)
                Spacer(Modifier.height(20.dp))
                Row(
                    modifier              = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment     = Alignment.CenterVertically
                ) {
                    Text("Price", fontSize = 16.sp, color = TextSecondary)
                    Text(
                        "$${String.format("%.2f", food.price)}",
                        fontSize   = 28.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color      = OrangeRed
                    )
                }
            }
        }
    }
}
