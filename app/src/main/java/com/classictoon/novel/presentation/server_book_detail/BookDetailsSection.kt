package com.classictoon.novel.presentation.server_book_detail

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.classictoon.novel.domain.library.book.Book

@Composable
fun BookDetailsSection(
    book: Book,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = "Book Details",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        
        // Future: Add more book details when available
        // DetailRow("Publisher", book.publisher)
        // DetailRow("Language", book.language)
        // DetailRow("ISBN", book.isbn)
        // DetailRow("Page Count", book.pageCount?.toString())
        // DetailRow("Publish Date", book.publishDate)
        
        // For now, show basic info
        DetailRow("Title", book.title)
        DetailRow("Author", book.author.asString())
        DetailRow("Categories", book.category.joinToString(", ") { it.name })
        DetailRow("Progress", "${(book.progress * 100).toInt()}%")
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
