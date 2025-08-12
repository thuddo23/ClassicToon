package com.classictoon.novel.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TopPicksSection() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = "Top Picks",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        
        // Use regular Column and Row instead of LazyVerticalGrid to avoid infinite height constraints
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Row 1
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                TopPickCard(
                    title = "Dragon's Tale",
                    modifier = Modifier.weight(1f)
                )
                TopPickCard(
                    title = "Magic bla",
                    modifier = Modifier.weight(1f)
                )
                TopPickCard(
                    title = "Shadow Quest",
                    modifier = Modifier.weight(1f)
                )
            }
            
            // Row 2
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                TopPickCard(
                    title = "The Fool",
                    modifier = Modifier.weight(1f)
                )
                TopPickCard(
                    title = "Explorei",
                    modifier = Modifier.weight(1f)
                )
                TopPickCard(
                    title = "Opal Queen",
                    modifier = Modifier.weight(1f)
                )
            }
            
            // Row 3
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                TopPickCard(
                    title = "Opal Queen",
                    modifier = Modifier.weight(1f)
                )
                TopPickCard(
                    title = "Opal Queen",
                    modifier = Modifier.weight(1f)
                )
                TopPickCard(
                    title = "Opal Queen",
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
private fun TopPickCard(
    title: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(0.9f)
                .clip(RoundedCornerShape(5.dp))
                .background(Color(0xFFE5E7EB))
        )
        
        Text(
            text = title,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            color = Color.Black,
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}
