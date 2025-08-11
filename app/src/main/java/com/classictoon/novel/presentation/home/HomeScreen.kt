/*
 * Book's Story — free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * SPDX-License-Identifier: GPL-3.0-only
 */

package com.classictoon.novel.presentation.home

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.fontscaling.MathUtils.lerp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.classictoon.novel.R
import com.classictoon.novel.domain.navigator.Screen
import com.classictoon.novel.presentation.navigator.LocalNavigator
import com.classictoon.novel.presentation.server_book_detail.ServerBookDetailScreen
import kotlin.math.absoluteValue

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
    val featuredBooks = listOf(
        FeaturedBook(
            title = "Dragon's Tale",
            tags = listOf("#Fantasy", "#Romance"),
            isNewUpdate = true
        ),
        FeaturedBook(
            title = "Shadow Quest",
            tags = listOf("#Adventure", "#Mystery"),
            isNewUpdate = false
        ),
        FeaturedBook(
            title = "Magic Academy",
            tags = listOf("#Fantasy", "#School"),
            isNewUpdate = true
        ),
        FeaturedBook(
            title = "The Fool's Journey",
            tags = listOf("#Adventure", "#Comedy"),
            isNewUpdate = false
        ),
        FeaturedBook(
            title = "Opal Queen",
            tags = listOf("#Romance", "#Fantasy"),
            isNewUpdate = false
        )
    )
    
    // Create infinite scroll by repeating the list
    val infiniteBooks = remember(featuredBooks) {
        List(1000) { index ->
            featuredBooks[index % featuredBooks.size]
        }
    }
    
    val pagerState = rememberPagerState(
        pageCount = { infiniteBooks.size },
        initialPage = 500 // Start in the middle for infinite scroll effect
    )
    
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(
            text = "What's everyone reading",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier
                .padding(bottom = 16.dp)
                .padding(horizontal = 16.dp)
        )
        
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .height(320.dp),
            contentPadding = PaddingValues(horizontal = 80.dp) // Show parts of adjacent cards
        ) { page ->
            FeaturedCard(
                book = infiniteBooks[page],
                pagerState = pagerState,
                page = page
            )
        }
    }
}

data class FeaturedBook(
    val title: String,
    val tags: List<String>,
    val isNewUpdate: Boolean
)

@SuppressLint("RestrictedApi")
@Composable
private fun FeaturedCard(
    book: FeaturedBook,
    pagerState: PagerState,
    page: Int,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .padding(0.dp)
            .graphicsLayer {
                val pageOffset = (
                    (pagerState.currentPage - page) + pagerState
                        .currentPageOffsetFraction
                ).absoluteValue

                // Scale from 0.85 (adjacent) to 1.0 (center)
                val scale = lerp(
                    start = 0.85f,
                    stop = 1f,
                    amount = 1f - pageOffset.coerceIn(0f, 1f)
                )
                scaleX = scale
                scaleY = scale

                // Alpha from 0.5f (adjacent) to 1.0f (center)
                val alpha = lerp(
                    start = 0.5f,
                    stop = 1f,
                    amount = 1f - pageOffset.coerceIn(0f, 1f)
                )
                this.alpha = alpha
            }
            .fillMaxWidth()
            .fillMaxHeight(),
        shape = RoundedCornerShape(16.dp),
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
                    text = book.title,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.White
                )
                
                if (book.isNewUpdate) {
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
                    book.tags.forEach { tag ->
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

