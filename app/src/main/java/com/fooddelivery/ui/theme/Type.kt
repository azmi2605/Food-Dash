package com.fooddelivery.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

// Using system default (you can add a Google Font via downloadable fonts)
val AppTypography = Typography(
    displayLarge = TextStyle(
        fontFamily  = FontFamily.Default,
        fontWeight  = FontWeight.Bold,
        fontSize    = 32.sp,
        color       = TextPrimary
    ),
    headlineMedium = TextStyle(
        fontFamily  = FontFamily.Default,
        fontWeight  = FontWeight.SemiBold,
        fontSize    = 22.sp,
        color       = TextPrimary
    ),
    titleLarge = TextStyle(
        fontFamily  = FontFamily.Default,
        fontWeight  = FontWeight.Bold,
        fontSize    = 18.sp,
        color       = TextPrimary
    ),
    bodyLarge = TextStyle(
        fontFamily  = FontFamily.Default,
        fontWeight  = FontWeight.Normal,
        fontSize    = 16.sp,
        color       = TextPrimary
    ),
    bodyMedium = TextStyle(
        fontFamily  = FontFamily.Default,
        fontWeight  = FontWeight.Normal,
        fontSize    = 14.sp,
        color       = TextSecondary
    ),
    labelSmall = TextStyle(
        fontFamily  = FontFamily.Default,
        fontWeight  = FontWeight.Medium,
        fontSize    = 12.sp,
        color       = TextHint
    )
)
