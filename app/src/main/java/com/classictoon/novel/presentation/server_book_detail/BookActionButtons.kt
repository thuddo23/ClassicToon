package com.classictoon.novel.presentation.server_book_detail

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Book
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun BookActionButtons(
    onReadBook: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // Read button
        Button(
            onClick = onReadBook,
            modifier = Modifier.weight(1f)
        ) {
            Icon(Icons.Default.Book, contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp))
            Text("Read Book")
        }
    }
}
