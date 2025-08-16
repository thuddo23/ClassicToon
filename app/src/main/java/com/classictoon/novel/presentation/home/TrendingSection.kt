package com.classictoon.novel.presentation.home

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.fontscaling.MathUtils.lerp
import androidx.compose.ui.unit.sp
import com.classictoon.novel.domain.library.book.Book
import kotlin.math.absoluteValue

@Composable
fun TrendingSection(
    trendingBooks: List<Book>,
    onBookClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val featuredBooks = if (trendingBooks.isNotEmpty()) {
        trendingBooks.map { book ->
            FeaturedBook(
                id = book.id,
                title = book.title,
                tags = listOf("#${book.category}", "#Popular"),
                isNewUpdate = true
            )
        }
    } else {
        listOf(
            FeaturedBook(
                id = 1,
                title = "Dragon's Tale",
                tags = listOf("#Fantasy", "#Romance"),
                isNewUpdate = true
            ),
            FeaturedBook(
                id = 2,
                title = "Shadow Quest",
                tags = listOf("#Adventure", "#Mystery"),
                isNewUpdate = false
            ),
            FeaturedBook(
                id = 3,
                title = "Magic Academy",
                tags = listOf("#Fantasy", "#School"),
                isNewUpdate = true
            ),
            FeaturedBook(
                id = 4,
                title = "The Fool's Journey",
                tags = listOf("#Adventure", "#Comedy"),
                isNewUpdate = false
            ),
            FeaturedBook(
                id = 5,
                title = "Opal Queen",
                tags = listOf("#Romance", "#Fantasy"),
                isNewUpdate = false
            )
        )
    }
    
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
        modifier = modifier
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
                page = page,
                onClick = { onBookClick(infiniteBooks[page].id) }
            )
        }
    }
}

data class FeaturedBook(
    val id: Int,
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
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .padding(0.dp)
            .clickable { onClick() }
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
                                .clip(androidx.compose.foundation.shape.RoundedCornerShape(10.dp))
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
