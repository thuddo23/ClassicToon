package com.classictoon.novel.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.classictoon.novel.domain.library.book.Book

@Composable
fun NewestArrivalsSection(
    newestBooks: List<Book>,
    onBookClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
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
        
        if (newestBooks.isNotEmpty()) {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                val books = newestBooks.take(10)
                itemsIndexed(books) { index, book ->
                    if (index == 0) Spacer(modifier = Modifier.width(16.dp))
                    NewestArrivalCard(
                        book = book,
                        onClick = { onBookClick(book.id) }
                    )
                    if (index == books.lastIndex) Spacer(modifier = Modifier.width(16.dp))
                }
            }
        } else {
            // Placeholder when no books are available
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(5) {
                    NewestArrivalCardPlaceholder()
                }
            }
        }
    }
}

@Composable
private fun NewestArrivalCard(
    book: Book,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val width = LocalConfiguration.current.screenWidthDp * 0.3
    Column(
        modifier = modifier
            .width(width.dp)
            .clickable { onClick() }
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(book.coverImage)
                .crossfade(true)
                .build(),
            contentDescription = "Cover of ${book.title}",
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(0.9f)
                .clip(RoundedCornerShape(8.dp)),
            contentScale = ContentScale.Crop,
            error = ColorPainter(Color(0xFFE5E7EB))
        )
        
        Spacer(modifier = Modifier.height(8.dp))
        
        Text(
            text = book.title,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            color = Color.Black,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )

        book.author.getAsString()?.let {
            Text(
                text = it,
                fontSize = 12.sp,
                color = Color.Gray,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
private fun NewestArrivalCardPlaceholder(
    modifier: Modifier = Modifier
) {
    val width = LocalConfiguration.current.screenWidthDp * 0.3
    Column(
        modifier = modifier
            .width(width.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(0.9f)
                .clip(RoundedCornerShape(8.dp))
                .background(Color(0xFFE5E7EB))
        )
        
        Spacer(modifier = Modifier.height(8.dp))
        
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(16.dp)
                .background(Color(0xFFE5E7EB), RoundedCornerShape(4.dp))
        )
        
        Spacer(modifier = Modifier.height(4.dp))
        
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(12.dp)
                .background(Color(0xFFE5E7EB), RoundedCornerShape(4.dp))
        )
    }
}
