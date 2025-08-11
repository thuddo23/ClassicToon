/*
 * Book's Story — free and open-source Material You eBook reader.
 * Copyright (C) 2024-2025 Acclorite
 * SPDX-License-Identifier: GPL-3.0-only
 */

package com.classictoon.novel.presentation.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.classictoon.novel.domain.library.book.Book
import com.classictoon.novel.domain.library.category.Category
import com.classictoon.novel.domain.navigator.Screen
import com.classictoon.novel.presentation.navigator.LocalNavigator
import com.classictoon.novel.presentation.server_book_detail.ServerBookDetailScreen
import com.classictoon.novel.R
import androidx.compose.ui.res.stringResource

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
    val gridState = rememberLazyGridState()
    var searchQuery by remember { mutableStateOf("") }
    
    /*LaunchedEffect(gridState) {
        snapshotFlow { gridState.layoutInfo.visibleItemsInfo }
            .collect { visibleItems ->
                if (visibleItems.isNotEmpty() && 
                    visibleItems.last().index >= uiState.books.size - 5) {
                    viewModel.loadMoreBooks()
                }
            }
    }*/
    
    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.surface
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = paddingValues.calculateTopPadding())
        ) {
            // Search bar
            SearchBar(
                query = searchQuery,
                onQueryChange = { 
                    searchQuery = it
                    viewModel.searchBooks(it)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )
            
            // Content
            when {
                uiState.isLoading && uiState.books.isEmpty() -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
                uiState.error != null -> {
                    ErrorContent(
                        error = uiState.error!!,
                        onRetry = { viewModel.loadBooks() },
                        onDismiss = { viewModel.clearError() }
                    )
                }
                else -> {
                    LazyVerticalGrid(
                        columns = GridCells.Adaptive(minSize = 160.dp),
                        contentPadding = PaddingValues(16.dp),
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        state = gridState,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        itemsIndexed(uiState.books, key = { _, book -> book.id }) { _, book ->
                            ServerBookCard(
                                book = book,
                                onClick = { onBookClick(book.id) }
                            )
                        }
                        
                        if (uiState.isLoading) {
                            item {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    CircularProgressIndicator()
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun SearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = query,
        onValueChange = onQueryChange,
        placeholder = { Text("Search books...") },
        leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
        modifier = modifier,
        singleLine = true
    )
}

@Composable
private fun ServerBookCard(
    book: Book,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column {
            // Cover image
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(book.coverImage)
                    .crossfade(true)
                    .build(),
                contentDescription = "Cover of ${book.title}",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentScale = ContentScale.Crop
            )
            
            // Book info
            Column(
                modifier = Modifier.padding(12.dp)
            ) {
                Text(
                    text = book.title,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                
                Spacer(modifier = Modifier.height(4.dp))
                
                Text(
                    text = book.author.asString(),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                
                book.category?.let { category ->
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = when (category) {
                            Category.FANTASY -> stringResource(id = R.string.reading_tab)
                            Category.ROMANCE -> stringResource(id = R.string.already_read_tab)
                            Category.ACTION -> stringResource(id = R.string.planning_tab)
                            Category.THRILLER -> stringResource(id = R.string.dropped_tab)
                            Category.COMEDY -> stringResource(id = R.string.comedy_tab)
                            Category.DRAMA -> stringResource(id = R.string.drama_tab)
                            Category.MYSTERY -> stringResource(id = R.string.mystery_tab)
                            Category.SCIENCE_FICTION -> stringResource(id = R.string.science_fiction_tab)
                            Category.OTHER -> stringResource(id = R.string.other_tab)
                        },
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
                
                // Note: BookEntity doesn't have rating, so we'll skip this for now
                // If you want to add rating, you'd need to add it to BookEntity
            }
        }
    }
}

@Composable
private fun ErrorContent(
    error: String,
    onRetry: () -> Unit,
    onDismiss: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Error",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.error
        )
        
        Spacer(modifier = Modifier.height(8.dp))
        
        Text(
            text = error,
            style = MaterialTheme.typography.bodyMedium,
            textAlign = androidx.compose.ui.text.style.TextAlign.Center
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(onClick = onRetry) {
                Text("Retry")
            }
            
            OutlinedButton(onClick = onDismiss) {
                Text("Dismiss")
            }
        }
    }
}
