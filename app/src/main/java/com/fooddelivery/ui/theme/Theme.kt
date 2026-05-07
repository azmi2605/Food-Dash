package com.fooddelivery.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary         = OrangeRed,
    onPrimary       = White,
    secondary       = OrangeLight,
    onSecondary     = White,
    background      = DarkBg,
    onBackground    = TextPrimary,
    surface         = DarkSurface,
    onSurface       = TextPrimary,
    surfaceVariant  = DarkCard,
    onSurfaceVariant= TextSecondary,
    error           = Color(0xFFCF6679)
)

@Composable
fun FoodDeliveryTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = DarkColorScheme,
        typography  = AppTypography,
        content     = content
    )
}
