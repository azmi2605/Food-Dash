package com.fooddelivery.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fooddelivery.data.model.FoodItem
import com.fooddelivery.ui.theme.*

@Composable
fun FoodCard(
    foodItem: FoodItem,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .shadow(6.dp, RoundedCornerShape(20.dp))
            .clip(RoundedCornerShape(20.dp))
            .background(DarkCard)
            .clickable(onClick = onClick)
            .padding(16.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            // Emoji as image placeholder
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(DarkSurface),
                contentAlignment = Alignment.Center
            ) {
                Text(text = foodItem.emoji, fontSize = 42.sp)
            }

            Spacer(Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                if (foodItem.isPopular) {
                    Text(
                        text       = "⚡ Popular",
                        fontSize   = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color      = OrangeRed
                    )
                    Spacer(Modifier.height(2.dp))
                }
                Text(
                    text       = foodItem.name,
                    fontSize   = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color      = TextPrimary,
                    maxLines   = 1,
                    overflow   = TextOverflow.Ellipsis
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    text     = foodItem.description,
                    fontSize = 12.sp,
                    color    = TextSecondary,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(Modifier.height(8.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("⭐ ${foodItem.rating}", fontSize = 12.sp, color = YellowStar)
                    Text(
                        " (${foodItem.reviewCount})",
                        fontSize = 12.sp,
                        color    = TextHint
                    )
                    Spacer(Modifier.weight(1f))
                    Text(
                        text       = "$${String.format("%.2f", foodItem.price)}",
                        fontSize   = 16.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color      = OrangeRed
                    )
                }
                Spacer(Modifier.height(4.dp))
                Text(
                    text     = "🕐 ${foodItem.deliveryTime}",
                    fontSize = 11.sp,
                    color    = TextHint
                )
            }
        }
    }
}
