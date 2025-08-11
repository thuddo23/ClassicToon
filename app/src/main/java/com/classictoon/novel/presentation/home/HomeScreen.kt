/*
 * Book's Story — free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * SPDX-License-Identifier: GPL-3.0-only
 */

package com.classictoon.novel.presentation.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.classictoon.novel.domain.navigator.Screen
import com.classictoon.novel.presentation.navigator.LocalNavigator
import com.classictoon.novel.presentation.server_book_detail.ServerBookDetailScreen
import com.classictoon.novel.R

object HomeScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        
        HomeScreenContent(
            onBookClick = { bookId ->
                navigator.push(ServerBookDetailScreen(bookId))
            }
        )
    }
}

@Composable
fun HomeScreenContent(
    onBookClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    var searchQuery by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("Fantasy") }
    
    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = Color.White
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = paddingValues.calculateTopPadding())
        ) {
            // Header Section
            item {
                HeaderSection()
            }
            
            // Featured Section
            item {
                FeaturedSection()
            }
            
            // Top Picks Section
            item {
                TopPicksSection()
            }
            
            // Category Section
            item {
                CategorySection(
                    selectedCategory = selectedCategory,
                    onCategorySelected = { selectedCategory = it }
                )
            }
            
            // Top Series by Category
            item {
                TopSeriesSection()
            }
            
            // Newest Arrivals
            item {
                NewestArrivalsSection()
            }
            
            // Footer
            item {
                FooterSection()
            }
        }
    }
}

@Composable
private fun HeaderSection() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Level Button
        Button(
            onClick = { },
            modifier = Modifier.height(40.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.White
            ),
            border = BorderStroke(1.dp, Color(0xFFC084FC))
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_level),
                contentDescription = "Level",
                modifier = Modifier.size(18.dp),
                tint = Color.Black
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Level 12",
                color = Color.Black,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium
            )
        }
        
        // Profile Button
        Box(
            modifier = Modifier
                .size(38.dp)
                .clip(CircleShape)
                .background(
                    Brush.horizontalGradient(
                        colors = listOf(Color(0xFFFACC15), Color(0xFFFB923C))
                    )
                )
                .clickable { },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_profile),
                contentDescription = "Profile",
                modifier = Modifier.size(18.dp),
                tint = Color.White
            )
        }
    }
}

@Composable
private fun FeaturedSection() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Text(
            text = "What's everyone reading",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                FeaturedCard(
                    title = "Dragon's Tale",
                    tags = listOf("#Fantasy", "#Romance"),
                    isNewUpdate = true,
                    modifier = Modifier.width(287.dp)
                )
            }
            item {
                FeaturedCard(
                    title = "Dragon's Tale",
                    tags = listOf("#Adventure"),
                    isNewUpdate = false,
                    modifier = Modifier.width(260.dp)
                )
            }
            item {
                FeaturedCard(
                    title = "Dragon's Tale",
                    tags = listOf("#Fantasy"),
                    isNewUpdate = false,
                    modifier = Modifier.width(260.dp)
                )
            }
        }
    }
}

@Composable
private fun FeaturedCard(
    title: String,
    tags: List<String>,
    isNewUpdate: Boolean,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.height(339.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF7C3AED))
        ) {
            // Placeholder for image - you can replace with actual AsyncImage
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFF7C3AED))
            )
            
            // Content overlay
            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(16.dp)
            ) {
                Text(
                    text = title,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.White
                )
                
                if (isNewUpdate) {
                    Text(
                        text = "New Update",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF22FF00),
                        modifier = Modifier.padding(top = 4.dp)
                    )
                } else {
                    Text(
                        text = "2 new chapter",
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF22FF00),
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
                
                // Tags
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.padding(top = 8.dp)
                ) {
                    tags.forEach { tag ->
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(10.dp))
                                .background(Color(0x2AD9D9D9))
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            Text(
                                text = tag,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Medium,
                                color = Color(0xB3FFFFFF)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun TopPicksSection() {
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
        
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.height(564.dp)
        ) {
            items(9) { index ->
                TopPickCard(
                    title = when (index) {
                        0 -> "Dragon's Tale"
                        1 -> "Magic bla"
                        2 -> "Shadow Quest"
                        3 -> "The Fool"
                        4 -> "Explorei"
                        5 -> "Opal Queen"
                        6 -> "Opal Queen"
                        7 -> "Opal Queen"
                        else -> "Opal Queen"
                    },
                    modifier = Modifier.size(110.dp)
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
                .size(110.dp, 138.dp)
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

@Composable
private fun CategorySection(
    selectedCategory: String,
    onCategorySelected: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = "Top Series by Category",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            val categories = listOf("Fantasy", "Romance", "Action", "Sci-Fi", "Slice of Life")
            
            items(categories) { category ->
                val isSelected = selectedCategory == category
                Button(
                    onClick = { onCategorySelected(category) },
                    modifier = Modifier.height(36.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (isSelected) Color(0xFFC084FC) else Color.Transparent
                    ),
                    border = if (!isSelected) BorderStroke(1.dp, Color(0xFFC084FC)) else null
                ) {
                    Text(
                        text = category,
                        color = if (isSelected) Color.White else Color(0xFFC084FC),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}

@Composable
private fun TopSeriesSection() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Top Series by Category",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            
            Icon(
                imageVector = Icons.Default.ArrowForward,
                contentDescription = "View more",
                modifier = Modifier.size(21.dp)
            )
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            val series = listOf(
                "Kingdom Hearts" to "1",
                "Dragon Rider" to "2",
                "Wizard School" to "3"
            )
            
            items(series) { (title, rank) ->
                TopSeriesCard(title = title, rank = rank)
            }
        }
    }
}

@Composable
private fun TopSeriesCard(
    title: String,
    rank: String
) {
    Column(
        modifier = Modifier.width(120.dp)
    ) {
        Box(
            modifier = Modifier
                .size(117.dp, 144.dp)
                .clip(RoundedCornerShape(5.dp))
                .background(Color(0xFFE5E7EB))
        )
        
        Text(
            text = rank,
            fontSize = 45.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.padding(top = 8.dp)
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

@Composable
private fun NewestArrivalsSection() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Newest Arrivals",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            
            Icon(
                imageVector = Icons.Default.ArrowForward,
                contentDescription = "View more",
                modifier = Modifier.size(21.dp)
            )
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            NewestArrivalCard(
                title = "Kingdom Hearts",
                modifier = Modifier.weight(1f)
            )
            NewestArrivalCard(
                title = "Dragon Rider",
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun NewestArrivalCard(
    title: String,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(206.dp)
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

@Composable
private fun FooterSection() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        // Navigation buttons
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            FooterButton(
                icon = Icons.Default.Home,
                text = "Home",
                isSelected = true
            )
            FooterButton(
                icon = Icons.Default.Search,
                text = "Explore",
                isSelected = false
            )
            FooterButton(
                icon = Icons.Default.Person,
                text = "Mine",
                isSelected = false
            )
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Advertisement space
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(61.dp)
                .background(Color(0xFF374151))
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Advertisement Space",
                color = Color(0xFF9CA3AF),
                fontSize = 14.sp
            )
        }
    }
}

@Composable
private fun FooterButton(
    icon: ImageVector,
    text: String,
    isSelected: Boolean
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = icon,
            contentDescription = text,
            modifier = Modifier.size(20.dp),
            tint = if (isSelected) Color(0xFF680FBC) else Color(0xFF9CA3AF)
        )
        
        Text(
            text = text,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            color = if (isSelected) Color(0xFF680FBC) else Color(0xFF9CA3AF),
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}
