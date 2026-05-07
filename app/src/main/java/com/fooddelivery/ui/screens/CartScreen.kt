package com.fooddelivery.ui.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.fooddelivery.ui.theme.*
import com.fooddelivery.viewmodel.FoodViewModel
import com.fooddelivery.viewmodel.PaymentMethod

enum class CartStep {
    REVIEW, PAYMENT_CONFIRMATION, SUCCESS
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen(
    navController: NavController,
    viewModel: FoodViewModel
) {
    val cartItems by viewModel.cartItems.collectAsStateWithLifecycle()
    val total     by viewModel.cartTotal.collectAsStateWithLifecycle()
    val userAddress by viewModel.userAddress.collectAsStateWithLifecycle()
    val selectedPayment by viewModel.paymentMethod.collectAsStateWithLifecycle()
    
    var currentStep by remember { mutableStateOf(CartStep.REVIEW) }
    var showAddressDialog by remember { mutableStateOf(false) }
    var tempAddress by remember { mutableStateOf(userAddress) }

    Scaffold(
        containerColor = DarkBg,
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(containerColor = DarkBg),
                navigationIcon = {
                    IconButton(onClick = { 
                        if (currentStep == CartStep.PAYMENT_CONFIRMATION) {
                            currentStep = CartStep.REVIEW
                        } else {
                            navController.popBackStack() 
                        }
                    }) {
                        Icon(
                            imageVector        = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint               = TextPrimary
                        )
                    }
                },
                title = {
                    Text(
                        text = when(currentStep) {
                            CartStep.REVIEW -> "My Cart 🛒"
                            CartStep.PAYMENT_CONFIRMATION -> "Confirm Payment 💳"
                            CartStep.SUCCESS -> "Success! 🎉"
                        }, 
                        color = TextPrimary, 
                        fontWeight = FontWeight.Bold
                    )
                }
            )
        },
        bottomBar = {
            if (cartItems.isNotEmpty() && currentStep != CartStep.SUCCESS) {
                Surface(color = DarkSurface, tonalElevation = 8.dp) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier              = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("Delivery Fee", color = TextSecondary, fontSize = 14.sp)
                            Text("Free 🎉", color = GreenSuccess, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                        }
                        Spacer(Modifier.height(6.dp))
                        Row(
                            modifier              = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("Total", color = TextPrimary, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                            Text(
                                "$${String.format("%.2f", total)}",
                                color      = OrangeRed,
                                fontSize   = 22.sp,
                                fontWeight = FontWeight.ExtraBold
                            )
                        }
                        Spacer(Modifier.height(12.dp))
                        Button(
                            onClick = {
                                if (currentStep == CartStep.REVIEW) {
                                    currentStep = CartStep.PAYMENT_CONFIRMATION
                                } else {
                                    viewModel.clearCart()
                                    currentStep = CartStep.SUCCESS
                                }
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(54.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = OrangeRed),
                            shape  = RoundedCornerShape(16.dp)
                        ) {
                            Text(
                                if (currentStep == CartStep.REVIEW) "Place Order 🚀" else "Confirm Order ✅",
                                fontSize = 17.sp,
                                fontWeight = FontWeight.Bold,
                                color = White
                            )
                        }
                    }
                }
            }
        }
    ) { padding ->
        when (currentStep) {
            CartStep.SUCCESS -> {
                Box(
                    modifier         = Modifier.fillMaxSize().padding(padding),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("✅", fontSize = 80.sp)
                        Spacer(Modifier.height(16.dp))
                        Text("Order Placed!", fontSize = 28.sp, fontWeight = FontWeight.ExtraBold, color = GreenSuccess)
                        Spacer(Modifier.height(8.dp))
                        Text(
                            "Your food is being prepared\nand will arrive soon!",
                            fontSize = 15.sp,
                            color    = TextSecondary,
                            textAlign = androidx.compose.ui.text.style.TextAlign.Center
                        )
                        Spacer(Modifier.height(24.dp))
                        Button(
                            onClick = { navController.popBackStack() },
                            colors  = ButtonDefaults.buttonColors(containerColor = OrangeRed),
                            shape   = RoundedCornerShape(14.dp)
                        ) {
                            Text("Back to Home", color = White, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
            CartStep.REVIEW -> {
                if (cartItems.isEmpty()) {
                    Box(
                        modifier         = Modifier.fillMaxSize().padding(padding),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("🛒", fontSize = 70.sp)
                            Spacer(Modifier.height(12.dp))
                            Text("Your cart is empty", fontSize = 22.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
                            Spacer(Modifier.height(6.dp))
                            Text("Add something delicious!", fontSize = 14.sp, color = TextSecondary)
                            Spacer(Modifier.height(24.dp))
                            Button(
                                onClick = { navController.popBackStack() },
                                colors  = ButtonDefaults.buttonColors(containerColor = OrangeRed),
                                shape   = RoundedCornerShape(14.dp)
                            ) {
                                Text("Browse Food", color = White, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                } else {
                    LazyColumn(
                        modifier       = Modifier.fillMaxSize().padding(padding),
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        item {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(18.dp))
                                    .background(DarkCard)
                                    .padding(16.dp)
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Icon(Icons.Default.LocationOn, null, tint = OrangeRed, modifier = Modifier.size(20.dp))
                                        Spacer(Modifier.width(8.dp))
                                        Text("Delivery Address", color = TextPrimary, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                                    }
                                    Text(
                                        "Edit",
                                        color = OrangeRed,
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier.clickable {
                                            tempAddress = userAddress
                                            showAddressDialog = true
                                        }
                                    )
                                }
                                Spacer(Modifier.height(8.dp))
                                Text(userAddress, color = TextSecondary, fontSize = 14.sp)
                            }
                        }

                        item {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(18.dp))
                                    .background(DarkCard)
                                    .padding(16.dp)
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(Icons.Default.CreditCard, null, tint = OrangeRed, modifier = Modifier.size(20.dp))
                                    Spacer(Modifier.width(8.dp))
                                    Text("Payment Method", color = TextPrimary, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                                }
                                Spacer(Modifier.height(12.dp))
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                                ) {
                                    PaymentOptionChip(
                                        name = "Online",
                                        selected = selectedPayment == PaymentMethod.ONLINE,
                                        onClick = { viewModel.updatePaymentMethod(PaymentMethod.ONLINE) },
                                        modifier = Modifier.weight(1f)
                                    )
                                    PaymentOptionChip(
                                        name = "Offline (COD)",
                                        selected = selectedPayment == PaymentMethod.OFFLINE,
                                        onClick = { viewModel.updatePaymentMethod(PaymentMethod.OFFLINE) },
                                        modifier = Modifier.weight(1f)
                                    )
                                }
                            }
                        }

                        item {
                            Text("Items in Cart", color = TextPrimary, fontWeight = FontWeight.Bold, fontSize = 16.sp, modifier = Modifier.padding(top = 8.dp))
                        }

                        items(cartItems, key = { it.foodItem.id }) { cartItem ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(18.dp))
                                    .background(DarkCard)
                                    .padding(14.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(
                                    modifier = Modifier.size(64.dp).clip(RoundedCornerShape(14.dp)).background(DarkSurface),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(cartItem.foodItem.emoji, fontSize = 34.sp)
                                }
                                Spacer(Modifier.width(14.dp))
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(cartItem.foodItem.name, fontSize = 15.sp, fontWeight = FontWeight.Bold, color = TextPrimary, maxLines = 1, overflow = TextOverflow.Ellipsis)
                                    Text("$${String.format("%.2f", cartItem.foodItem.price)} each", fontSize = 13.sp, color = TextSecondary)
                                }
                                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                    IconButton(onClick = { viewModel.removeFromCart(cartItem.foodItem) }, modifier = Modifier.size(32.dp).clip(CircleShape).background(DarkSurface)) {
                                        Icon(Icons.Default.Remove, null, tint = OrangeRed, modifier = Modifier.size(16.dp))
                                    }
                                    Text("${cartItem.quantity}", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
                                    IconButton(onClick = { viewModel.addToCart(cartItem.foodItem) }, modifier = Modifier.size(32.dp).clip(CircleShape).background(OrangeRed)) {
                                        Icon(Icons.Default.Add, null, tint = White, modifier = Modifier.size(16.dp))
                                    }
                                    Spacer(Modifier.width(4.dp))
                                    IconButton(onClick = { viewModel.deleteFromCart(cartItem.foodItem) }, modifier = Modifier.size(32.dp).clip(CircleShape).background(DarkSurface)) {
                                        Icon(Icons.Default.Delete, null, tint = TextHint, modifier = Modifier.size(16.dp))
                                    }
                                }
                            }
                        }
                    }
                }
            }
            CartStep.PAYMENT_CONFIRMATION -> {
                Box(
                    modifier = Modifier.fillMaxSize().padding(padding),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(24.dp)) {
                        Text("💳", fontSize = 80.sp)
                        Spacer(Modifier.height(16.dp))
                        Text("Confirm Payment", fontSize = 28.sp, fontWeight = FontWeight.ExtraBold, color = TextPrimary)
                        Spacer(Modifier.height(8.dp))
                        Text(
                            "You are paying via ${if (selectedPayment == PaymentMethod.ONLINE) "Online Payment" else "Cash on Delivery"}.",
                            fontSize = 15.sp, color = TextSecondary, textAlign = androidx.compose.ui.text.style.TextAlign.Center
                        )
                        Spacer(Modifier.height(24.dp))
                        Card(colors = CardDefaults.cardColors(containerColor = DarkCard), shape = RoundedCornerShape(16.dp), modifier = Modifier.fillMaxWidth()) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text("Delivery Address:", color = OrangeRed, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                                Text(userAddress, color = TextPrimary, fontSize = 14.sp)
                                Spacer(Modifier.height(12.dp))
                                Text("Total Amount:", color = OrangeRed, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                                Text("$${String.format("%.2f", total)}", color = TextPrimary, fontSize = 20.sp, fontWeight = FontWeight.ExtraBold)
                            }
                        }
                        Spacer(Modifier.height(32.dp))
                        TextButton(onClick = { currentStep = CartStep.REVIEW }) {
                            Text("Go back and edit", color = TextHint)
                        }
                    }
                }
            }
        }
    }

    if (showAddressDialog) {
        AlertDialog(
            onDismissRequest = { showAddressDialog = false },
            title = { Text("Update Address", color = TextPrimary) },
            text = {
                OutlinedTextField(
                    value = tempAddress,
                    onValueChange = { tempAddress = it },
                    label = { Text("Address") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = OrangeRed,
                        cursorColor = OrangeRed,
                        focusedLabelColor = OrangeRed,
                        unfocusedLabelColor = TextSecondary,
                        focusedTextColor = TextPrimary,
                        unfocusedTextColor = TextPrimary
                    )
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        viewModel.updateAddress(tempAddress)
                        showAddressDialog = false
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = OrangeRed)
                ) {
                    Text("Save", color = White)
                }
            },
            dismissButton = {
                TextButton(onClick = { showAddressDialog = false }) {
                    Text("Cancel", color = OrangeRed)
                }
            },
            containerColor = DarkSurface
        )
    }
}

@Composable
fun PaymentOptionChip(
    name: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(if (selected) OrangeRed else DarkSurface)
            .clickable(onClick = onClick)
            .padding(vertical = 12.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = name,
            color = if (selected) White else TextSecondary,
            fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal,
            fontSize = 14.sp
        )
    }
}
