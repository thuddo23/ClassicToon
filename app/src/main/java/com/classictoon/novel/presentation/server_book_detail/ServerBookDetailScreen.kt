/*
 * Book's Story — free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * SPDX-License-Identifier: GPL-3.0-only
 */

package com.classictoon.novel.presentation.server_book_detail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.classictoon.novel.domain.library.book.Book
import com.classictoon.novel.domain.navigator.Screen
import com.classictoon.novel.presentation.navigator.LocalNavigator

class ServerBookDetailScreen(private val bookId: Int) : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        
        ServerBookDetailScreenContent(
            bookId = bookId,
            onBackClick = { navigator.pop() },
            onReadBook = { bookId ->
//                navigator.push(ServerBookReaderScreen(bookId))
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ServerBookDetailScreenContent(
    bookId: Int,
    onBackClick: () -> Unit,
    onReadBook: (String) -> Unit,
    viewModel: ServerBookDetailViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    
    LaunchedEffect(bookId) {
        viewModel.loadBook(bookId)
    }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Book Details") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        when {
            uiState.isLoading -> {
                LoadingContent(modifier = Modifier.padding(paddingValues))
            }
            uiState.error != null -> {
                ErrorContent(
                    error = uiState.error!!,
                    onRetry = { viewModel.loadBook(bookId) },
                    onBackClick = onBackClick,
                    modifier = Modifier.padding(paddingValues)
                )
            }
            uiState.book != null -> {
                BookDetailContent(
                    book = uiState.book!!,
                    onReadBook = onReadBook,
                    onDownloadBook = { bookId -> viewModel.downloadBook(bookId) },
                    isDownloading = uiState.isDownloading,
                    modifier = Modifier.padding(paddingValues)
                )
                
                // Show download success message
                if (uiState.downloadSuccess) {
                    LaunchedEffect(Unit) {
                        // Clear success state after 3 seconds
                        kotlinx.coroutines.delay(3000)
                        viewModel.clearError()
                    }
                    
                    Snackbar(
                        modifier = Modifier.padding(16.dp),
                        action = {
                            TextButton(onClick = { viewModel.clearError() }) {
                                Text("Dismiss")
                            }
                        }
                    ) {
                        Text("Book downloaded successfully!")
                    }
                }
            }
        }
    }
}

@Composable
private fun BookDetailContent(
    book: Book,
    onReadBook: (String) -> Unit,
    onDownloadBook: (Int) -> Unit,
    isDownloading: Boolean = false,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        BookCoverSection(book = book)
        
        Spacer(modifier = Modifier.height(24.dp))
        
        BookActionButtons(
            onReadBook = { onReadBook(book.id.toString()) },
            onDownloadBook = { onDownloadBook(book.id) },
            isDownloading = isDownloading
        )
        
        Spacer(modifier = Modifier.height(24.dp))
        
        BookDescriptionSection(description = book.description)
        
        Spacer(modifier = Modifier.height(24.dp))
        
        BookDetailsSection(book = book)
    }
}

@Composable
private fun LoadingContent(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}
