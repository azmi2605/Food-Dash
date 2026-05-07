package com.fooddelivery.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Fastfood
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.draw.clip
import androidx.navigation.NavController
import com.fooddelivery.navigation.Routes
import com.fooddelivery.ui.theme.*
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController) {
    // Animate scale on entry
    val scale = remember { Animatable(0.5f) }

    LaunchedEffect(Unit) {
        scale.animateTo(
            targetValue = 1f,
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioMediumBouncy,
                stiffness    = Spring.StiffnessLow
            )
        )
        delay(1800)
        navController.navigate(Routes.HOME) {
            popUpTo(Routes.SPLASH) { inclusive = true }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(listOf(DarkBg, DarkSurface))
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.scale(scale.value)
        ) {
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(24.dp))
                    .background(Brush.verticalGradient(listOf(OrangeRed, OrangeRed.copy(alpha = 0.7f)))),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Fastfood,
                    contentDescription = "Logo",
                    tint = White,
                    modifier = Modifier.size(56.dp)
                )
            }
            Spacer(Modifier.height(12.dp))
            Text(
                text       = "FoodDash",
                fontSize   = 38.sp,
                fontWeight = FontWeight.ExtraBold,
                color      = OrangeRed
            )
            Spacer(Modifier.height(6.dp))
            Text(
                text     = "Delicious, delivered fast",
                fontSize = 15.sp,
                color    = TextSecondary
            )
        }
    }
}
