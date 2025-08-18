package com.classictoon.novel.presentation.server_book_detail

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.classictoon.novel.domain.library.book.Book

@Composable
fun BookCoverSection(
    book: Book,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Cover image
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(book.coverImage)
                .crossfade(true)
                .build(),
            contentDescription = "Cover of ${book.title}",
            modifier = Modifier
                .width(120.dp)
                .height(180.dp),
            contentScale = ContentScale.Crop
        )
        
        // Basic info
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = book.title,
                style = MaterialTheme.typography.headlineSmall,
                textAlign = TextAlign.Start
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    Icons.Default.Person,
                    contentDescription = null,
                    modifier = Modifier.size(16.dp),
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = book.author.asString(),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            DetailRow("Author", book.author.asString())
            DetailRow("Categories", book.category.joinToString(", ") { it.name })
            DetailRow("Progress", "${(book.progress * 100).toInt()}%")
        }
    }
}

@Composable
private fun DetailRow(
    label: String,
    value: String?,
    modifier: Modifier = Modifier
) {
    if (value != null) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
        ) {
            Text(
                text = "$label:",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.width(100.dp)
            )
            Text(
                text = value,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}
