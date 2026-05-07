package com.fooddelivery.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fooddelivery.data.model.Category
import com.fooddelivery.ui.theme.*

@Composable
fun CategoryChip(
    category: Category,
    selected: Boolean,
    onClick: () -> Unit
) {
    val bgColor by animateColorAsState(
        targetValue = if (selected) OrangeRed else DarkCard,
        label       = "chipBg"
    )
    val textColor by animateColorAsState(
        targetValue = if (selected) White else TextSecondary,
        label       = "chipText"
    )

    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(50))
            .background(bgColor)
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = category.emoji, fontSize = 18.sp)
        Spacer(Modifier.width(6.dp))
        Text(
            text       = category.name,
            color      = textColor,
            fontSize   = 14.sp,
            fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal
        )
    }
}
